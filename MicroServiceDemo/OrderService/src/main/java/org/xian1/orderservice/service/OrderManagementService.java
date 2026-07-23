package org.xian1.orderservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.xian1.orderservice.client.AuthClient;
import org.xian1.orderservice.client.ProductClient;
import org.xian1.orderservice.dto.*;
import org.xian1.orderservice.model.OrderItem;
import org.xian1.orderservice.model.OrderRecord;
import org.xian1.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderManagementService {

    private final OrderRepository orderRepository;
    private final AuthClient authClient;
    private final ProductClient productClient;

    public OrderManagementService(OrderRepository orderRepository, AuthClient authClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.authClient = authClient;
        this.productClient = productClient;
    }

    public InvoiceResponse createOrder(CreateOrderRequest request) {
        validateRequest(request);

        AuthValidationResponse authResponse = authClient.validate(
                new AuthValidationRequest(request.getUsername(), request.getPassword())
        );
        if (authResponse == null || !Boolean.TRUE.equals(authResponse.getValid())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        OrderRecord order = new OrderRecord();
        order.setInvoiceNumber(generateInvoiceNumber());
        order.setUsername(request.getUsername());
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineRequest line : request.getItems()) {
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
            }

            ProductDTO product = productClient.getProductById(line.getProductId());
            if (product == null || product.getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found: " + line.getProductId());
            }

            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(line.getQuantity()));
            total = total.add(subTotal);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setQuantity(line.getQuantity());
            item.setUnitPrice(unitPrice);
            item.setSubTotal(subTotal);
            order.getItems().add(item);
        }

        order.setTotalAmount(total);
        OrderRecord savedOrder = orderRepository.save(order);
        return toInvoice(savedOrder);
    }

    public InvoiceResponse findByInvoiceNumber(String invoiceNumber) {
        OrderRecord order = orderRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        return toInvoice(order);
    }

    public List<InvoiceResponse> findByUsername(String username) {
        return orderRepository.findByUsername(username)
                .stream()
                .map(this::toInvoice)
                .collect(Collectors.toList());
    }

    private void validateRequest(CreateOrderRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one order item is required");
        }
    }

    private String generateInvoiceNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "INV-" + datePart + "-" + randomPart;
    }

    private InvoiceResponse toInvoice(OrderRecord order) {
        InvoiceResponse response = new InvoiceResponse();
        response.setInvoiceNumber(order.getInvoiceNumber());
        response.setUsername(order.getUsername());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        response.setUserTotalInvoiced(orderRepository.sumTotalAmountByUsername(order.getUsername()));

        List<InvoiceItemResponse> items = order.getItems().stream().map(item -> {
            InvoiceItemResponse dto = new InvoiceItemResponse();
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice(item.getUnitPrice());
            dto.setSubTotal(item.getSubTotal());
            return dto;
        }).toList();

        response.setItems(items);
        return response;
    }
}
