package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetMeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void addToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 判断商品是否已存在
        if (list != null && !list.isEmpty()) {
            // 存在，数量加1
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.update(cart);
        } else {
            Long dishId = shoppingCartDTO.getDishId();
            // 不存在，插入数据
            if (dishId != null) {
                // 插入 dish 菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                // 插入 setMeal 套餐
                SetMeal setMeal = setMealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setMeal.getName());
                shoppingCart.setImage(setMeal.getImage());
                shoppingCart.setAmount(setMeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.list(
                ShoppingCart.builder()
                        .userId(BaseContext.getCurrentId())
                        .build());
    }

    @Override
    public void deleteFromShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        ShoppingCart cart = list.get(0);
        List<Long> id = Collections.singletonList(cart.getId());
        // 商品数目大于1
        if (cart.getNumber() > 1) {
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.update(cart);
        } else {
            // 商品数目为1
            shoppingCartMapper.deleteById(id);
        }
    }

    @Override
    public void emptyShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
