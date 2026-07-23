package org.xian1.orderservice.controller;

import org.springframework.web.bind.annotation.*;
import org.xian1.orderservice.dto.CreateOrderRequest;
import org.xian1.orderservice.dto.InvoiceResponse;
import org.xian1.orderservice.service.OrderManagementService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderManagementService orderService;

    public OrderController(OrderManagementService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public InvoiceResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public InvoiceResponse getInvoice(@PathVariable String invoiceNumber) {
        return orderService.findByInvoiceNumber(invoiceNumber);
    }

    @GetMapping("/user/{username}")
    public List<InvoiceResponse> getUserOrders(@PathVariable String username) {
        return orderService.findByUsername(username);
    }
}
