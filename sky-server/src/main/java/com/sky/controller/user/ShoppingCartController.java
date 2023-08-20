package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "C段-购物车接口")
@RequestMapping("/user/shoppingCart")
@RestController(value = "userShoppingCartController")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加到购物车")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.addToShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCarts);
    }

    @PostMapping("/sub")
    @ApiOperation("删除商品")
    public Result<String> delete(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.deleteFromShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result<String> empty() {
        shoppingCartService.emptyShoppingCart();
        return Result.success();
    }
}
