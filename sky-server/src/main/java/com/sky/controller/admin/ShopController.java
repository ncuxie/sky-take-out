package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "店铺接口")
@RequestMapping("/admin/shop")
@RestController(value = "adminShopController")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("店铺状态设置")
    public Result<String> openOrClose(@PathVariable Integer status) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("status",status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("店铺状态获取")
    public Result<Integer> getShopStatus(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return Result.success((Integer) valueOperations.get("status"));
    }
}
