package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

    void addToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void deleteFromShoppingCart(ShoppingCartDTO shoppingCartDTO);

    void emptyShoppingCart();
}
