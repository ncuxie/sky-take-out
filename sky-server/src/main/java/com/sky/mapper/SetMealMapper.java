package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetMealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {

    List<SetMeal> list(SetMeal setMeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from set_meal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.set_meal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetMealId(Long setMealId);

    @Select("select * from setMeal where id = #{id}")
    SetMeal getById(Long id);

    @Delete("delete from setMeal where id = #{id}")
    void deleteById(Long setMealId);

    Page<SetMealVO> pageQuery(SetMealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(SetMeal setmeal);

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(SetMeal setmeal);
}
