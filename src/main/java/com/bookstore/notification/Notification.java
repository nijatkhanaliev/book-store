package com.bookstore.notification;

import com.bookstore.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {
    private String message;
    private boolean read;
    private Long recipientId;

    @PrePersist
    void init(){
        this.read = false;
    }

}
