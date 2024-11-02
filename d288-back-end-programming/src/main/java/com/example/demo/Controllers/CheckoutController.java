package com.example.demo.Controllers;

import com.example.demo.Services.*;
import com.example.demo.Services.PurchaseData;
import com.example.demo.Services.PurchaseResponseData;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponseData placeOrder(@RequestBody PurchaseData purchaseData) {
        PurchaseResponseData purchaseResponseData = checkoutService.placeOrder(purchaseData);
        return purchaseResponseData;
    }
}
