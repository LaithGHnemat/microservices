package com.evolution.orderservice.controller;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/6/2024
 */

import com.evolution.orderservice.dto.OrderLineItemsDto;
import com.evolution.orderservice.dto.OrderRequest;
import com.evolution.orderservice.service.IOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

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
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    public String checkSingelItem(@RequestBody OrderLineItemsDto orderLineItemsDto) {
    // todo this just an ex
        return orderService.checkSingelItem(orderLineItemsDto);


    }

    // note: fallbackMethod will be exc when CircuitBreaker decides
      //- >> we can not return the value of placeOrder
    public String fallbackMethod(OrderLineItemsDto orderLineItemsDto, Throwable throwable) {
        log.info("Cannot check item availability. Executing fallback logic.");
        return "Inventory Service is currently unavailable. Please try again later.";
    }

}