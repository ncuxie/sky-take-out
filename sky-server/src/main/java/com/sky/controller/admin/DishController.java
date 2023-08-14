package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
@Slf4j
public class DishController {

    @Autowired
    private DishServiceImpl dishService;

    @GetMapping("/{id}")
    @ApiOperation("菜品查询")
    public Result<DishVO> getDishVO(@PathVariable Long id){
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("菜品修改")
    public Result<String> update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品状态")
    public Result<String> status(@PathVariable Integer status, Long id) {
        dishService.status(status, id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result<String> deleteBatch(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询");
        PageResult page = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(page);
    }

    @PostMapping
    @ApiOperation("菜品添加")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("菜品添加");
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

}
