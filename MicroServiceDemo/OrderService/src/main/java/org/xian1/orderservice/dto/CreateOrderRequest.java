package org.xian1.orderservice.dto;

import java.util.List;

public class CreateOrderRequest {
    private String username;
    private String password;
    private List<OrderLineRequest> items;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderLineRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderLineRequest> items) {
        this.items = items;
    }
}
