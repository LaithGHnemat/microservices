package com.evolution.orderservice.controller;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/6/2024
 */

import com.evolution.orderservice.dto.OrderLineItemsDto;
import com.evolution.orderservice.dto.OrderRequest;
import com.evolution.orderservice.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing Order");

        orderService.placeOrder(orderRequest);
        return "The order has been  placed Successfully :) ";
    }

    @PostMapping("/api-gateway-test")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderLineItemsDto orderLineItemsDto) {
        return orderLineItemsDto.toString();

    }

   /* public CompletableFuture< String > fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info ( "Cannot Place Order Executing Fallback logic" );
        return CompletableFuture.supplyAsync ( () -> "Oops! Something went wrong, please order after some time!" );
    }*/

}