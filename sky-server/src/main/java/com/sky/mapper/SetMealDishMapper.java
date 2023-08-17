package com.sky.mapper;

import com.sky.entity.SetMealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    @Select("select * from set_meal_dish where set_meal_id = #{setMealId}")
    List<SetMealDish> getBySetMealId(Long setMealId);

    @Delete("delete from set_meal_dish where set_meal_id = #{setMealId}")
    void deleteBySetMealId(Long setMealId);

    void insertBatch(List<SetMealDish> setMealDishes);

    List<Long> getSetMealIdsByDishIds(List<Long> ids);

}
