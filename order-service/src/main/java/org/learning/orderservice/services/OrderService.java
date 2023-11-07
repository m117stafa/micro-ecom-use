package org.learning.orderservice.services;


import lombok.extern.slf4j.Slf4j;
import org.learning.orderservice.dto.InventoryResponse;
import org.learning.orderservice.dto.OrderLineItemsDto;
import org.learning.orderservice.dto.OrderRequest;
import org.learning.orderservice.dto.OrderResponse;
import org.learning.orderservice.models.Order;
import org.learning.orderservice.models.OrderLineItems;
import org.learning.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsListDto()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        log.info(order.toString());
        log.info(order.getOrderLineItemsList().toString());
        List<String> productCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getProductCode)
                .toList();

        log.info(productCodes.toString());

        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8083/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("productCode",productCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponsesArray != null;
        boolean allProductInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        log.info(String.valueOf(allProductInStock));
        log.info(Arrays.toString(inventoryResponsesArray));
        if(allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
        

    }

    //TODO: create other endpoint depend on project and test them

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setProductCode(orderLineItemsDto.getProductCode());
        return orderLineItems;
    }


    }


