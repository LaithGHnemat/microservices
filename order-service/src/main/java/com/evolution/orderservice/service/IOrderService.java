package com.evolution.orderservice.service;

import com.evolution.orderservice.dto.OrderLineItemsDto;
import com.evolution.orderservice.dto.OrderRequest;

public interface IOrderService {
    void placeOrder(OrderRequest orderRequest);

    String checkSingelItem(OrderLineItemsDto orderLineItemsDto);
}
