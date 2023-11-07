package org.learning.orderservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.learning.orderservice.dto.OrderRequest;
import org.learning.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("orderRequest ------------------");
        log.info(orderRequest.toString());
        orderService.placeOrder(orderRequest);
        return "Order is created";
    }

}
