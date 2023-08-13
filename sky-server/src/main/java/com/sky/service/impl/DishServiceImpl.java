package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

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
        if (flavors != null) {
            flavors.forEach(flavor->flavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }

    }
}
