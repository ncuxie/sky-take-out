package com.sky.service;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetMealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetMealService {

    List<DishItemVO> getDishItemById(Long id);

    void startOrStop(Integer status, Long id);

    SetMealVO getByIdWithDish(Long id);

    void update(SetMealDTO setMealDTO);

    void deleteBatch(List<Long> ids);

    PageResult pageQuery(SetMealPageQueryDTO setmealPageQueryDTO);

    void saveWithDish(SetMealDTO setmealDTO);

    List<SetMeal> list(SetMeal setMeal);
}
