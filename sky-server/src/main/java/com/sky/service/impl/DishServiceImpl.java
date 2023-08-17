package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetMeal;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 插入 菜品 (1)
        dishMapper.insert(dish);

        // 获取 insert 生成的 id
        Long dishId = dish.getId();

        // 插入 口味 (n)
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO pageQuery) {
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(pageQuery);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);

            // 菜品是否在售
            if (dish.getStatus().equals(StatusConstant.ENABLE))
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);


        }
        // 菜品是否在套餐
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishIds(ids);
        if (!setMealIds.isEmpty())
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        // 删除菜品
        dishMapper.deleteByIds(ids);

        // 删除口味
        dishFlavorMapper.deleteByDishIds(ids);
    }

    @Override
    @Transactional
    public void status(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);

        // 如果是停售操作，还需要将包含当前菜品的套餐也停售
        if (status.equals(StatusConstant.DISABLE)) {
            List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishIds(Collections.singletonList(id));
            log.info("setMealIds:{}", setMealIds);
            if (setMealIds != null && setMealIds.size() > 0) {
                for (Long setMealId : setMealIds) {
                    SetMeal setmeal = SetMeal.builder()
                            .id(setMealId)
                            .status(StatusConstant.DISABLE)
                            .build();
                    setMealMapper.update(setmeal);
                }
            }
        }
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            dishFlavorMapper.deleteByDishIds(Collections.singletonList(dishDTO.getId()));
            flavors.forEach(flavor -> flavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dishMapper.getById(id), dishVO);
        return dishVO;
    }
}
