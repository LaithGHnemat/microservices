package com.evolution.orderservice.service;

import com.evolution.orderservice.dto.OrderRequest;

public interface IOrderService {
    void placeOrder(OrderRequest orderRequest);

}
