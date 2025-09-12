package com.bookstore.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerApplicationCreatedEvent {
    private Long userId;
    private String userName;
}
