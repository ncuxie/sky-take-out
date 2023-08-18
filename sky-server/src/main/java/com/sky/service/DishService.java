package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    List<DishVO> listWithFlavor(Dish dish);

    List<Dish> list(Long categoryId);

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    void status(Integer status, Long id);

    void update(DishDTO dishDTO);

    DishVO getById(Long id);
}
