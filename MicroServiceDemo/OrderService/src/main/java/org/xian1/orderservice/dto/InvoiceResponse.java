package org.xian1.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceResponse {
    private String invoiceNumber;
    private String username;
    private BigDecimal totalAmount;
    private BigDecimal userTotalInvoiced;
    private LocalDateTime createdAt;
    private List<InvoiceItemResponse> items;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getUserTotalInvoiced() {
        return userTotalInvoiced;
    }

    public void setUserTotalInvoiced(BigDecimal userTotalInvoiced) {
        this.userTotalInvoiced = userTotalInvoiced;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<InvoiceItemResponse> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemResponse> items) {
        this.items = items;
    }
}
