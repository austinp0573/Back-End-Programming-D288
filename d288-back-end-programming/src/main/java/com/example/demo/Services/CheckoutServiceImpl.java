package com.example.demo.Services;

import com.example.demo.Entities.*;
import com.example.demo.Dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter;
import lombok.Setter;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;

    private final CustomerRepository customerRepository;

    private final ExcursionRepository excursionRepository;

    private final VacationRepository vacationRepository;

    public CheckoutServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository,
                               CustomerRepository customerRepository, ExcursionRepository excursionRepository,
                               VacationRepository vacationRepository){

        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.excursionRepository = excursionRepository;
        this.vacationRepository = vacationRepository;
    }

    @Override
    @Transactional
    public PurchaseResponseData placeOrder(PurchaseData purchaseData) {

        if (purchaseData.getCartItems() == null || purchaseData.getCartItems().isEmpty()) {
            return new PurchaseResponseData("Cart Empty");
        }

        // Generate tracking number only if the cart is not empty
        String orderTrackingNumber = generateOrderTrackingNumber();
        purchaseData.getCart().setOrderTrackingNumber(orderTrackingNumber);
        purchaseData.getCart().setStatus(StatusType.ordered);

        purchaseData.getCart().setStatus(StatusType.ordered);

        Vacation vacation = purchaseData.getCartItems()
                .stream()
                .findFirst()
                .map(CartItem::getVacation)
                .orElseThrow(() -> new IllegalArgumentException("Vacation can't be null."));

        vacationRepository.save(vacation);

        Cart savedCart = cartRepository.save(purchaseData.getCart());

        Optional.ofNullable(vacation.getExcursions())
                .ifPresent(excursions -> excursions.forEach(excursion -> {
                    if (excursion.getVacation() == null) {
                        excursion.setVacation(vacation);
                    }
                    excursionRepository.save(excursion);
                }));
        purchaseData.getCartItems().forEach(cartItem -> {
            cartItem.setCart(savedCart);

            cartItemRepository.save(cartItem);
        });

        purchaseData.getCartItems().forEach(cartItem -> {
            Set<Excursion> excursionsForCartItem = cartItem.getExcursions();
            if (excursionsForCartItem != null) {
                excursionsForCartItem.forEach(excursion -> {
                    Excursion persistedExcursion = excursionRepository.findById(excursion.getId()).orElse(null);
                    if (persistedExcursion != null) {
                        persistedExcursion.getCartItems().add(cartItem);
                        excursionRepository.save(persistedExcursion);
                    }
                });
            }
        });

        Customer customer = purchaseData.getCustomer();
        customerRepository.save(customer);

        return new PurchaseResponseData(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {return UUID.randomUUID().toString();}

}
