package com.bookstore.notification;

import com.bookstore.common.BaseEntity;
import com.bookstore.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification<T> extends BaseEntity {
    private String message;
    private T data;
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @PrePersist
    void init(){
        this.read = false;
    }

}
