package com.example.demo.Services;

import com.example.demo.Entities.Cart;
import com.example.demo.Entities.CartItem;
import com.example.demo.Entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PurchaseData {

    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
