package com.sky.controller.admin;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetMealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理
 */
@Slf4j
@Api(tags = "套餐接口")
@RestController
@RequestMapping("/admin/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        setMealService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询套餐 根据id")
    public Result<SetMealVO> getById(@PathVariable Long id) {
        SetMealVO setMealVO = setMealService.getByIdWithDish(id);
        return Result.success(setMealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result<String> update(@RequestBody SetMealDTO setMealDTO) {
        setMealService.update(setMealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result<String> delete(@RequestParam List<Long> ids){
        setMealService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetMealPageQueryDTO setMealPageQueryDTO) {
        PageResult pageResult = setMealService.pageQuery(setMealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> save(@RequestBody SetMealDTO setmealDTO) {
        setMealService.saveWithDish(setmealDTO);
        return Result.success();
    }
}
