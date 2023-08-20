package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.SetMeal;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐浏览接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @GetMapping("/list")
    @ApiOperation("查询套餐 根据分类id")
    @Cacheable(cacheNames = "setMealCache", key = "#categoryId")
    public Result<List<SetMeal>> list(Long categoryId) {
        SetMeal setMeal = new SetMeal();
        setMeal.setCategoryId(categoryId);
        setMeal.setStatus(StatusConstant.ENABLE);
        List<SetMeal> list = setMealService.list(setMeal);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("查询包含的菜品列表 根据套餐id")
    // TODO redis 查询包含的菜品列表 根据套餐id
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setMealService.getDishItemById(id);
        return Result.success(list);
    }

}