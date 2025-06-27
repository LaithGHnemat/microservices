package com.evolution.orderservice.service;

import com.evolution.orderservice.dto.InventoryResponse;
import com.evolution.orderservice.dto.OrderLineItemsDto;
import com.evolution.orderservice.dto.OrderRequest;
import com.evolution.orderservice.model.Order;
import com.evolution.orderservice.model.OrderLineItems;
import com.evolution.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/6/2024
 */

@Service
public class OrderService implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        if (CollectionUtils.isEmpty(orderRequest.getOrderLineItemsDto())) {
            return;
        }

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
                        .stream()
                        .map(this::mapFromDto)
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);


        List<String> skuCodes = order
                .getOrderLineItems()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());



        InventoryResponse[] skuCodesResult = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (!CollectionUtils.isEmpty(Arrays.asList(skuCodesResult)))
            return;

        boolean isAllInStock = Arrays.stream(skuCodesResult).allMatch(InventoryResponse::isInStock);
        if (!Boolean.TRUE.equals(isAllInStock))
            throw new IllegalArgumentException("the product not in the inv try again dude ");
        orderRepository.save(order);
    }

    private OrderLineItems mapFromDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder().
                price (orderLineItemsDto.getPrice()).
                quantity (orderLineItemsDto.getQuantity ()).
                skuCode (orderLineItemsDto.getSkuCode())
                .build ();
    }
}
