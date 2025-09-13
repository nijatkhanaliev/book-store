package com.bookstore.user;

import com.bookstore.book.Book;
import com.bookstore.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String imageUrl;
    private LocalDate birthOfDate;
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @PrePersist
    void init(){
        this.isActive = true;
    }

    @Transient
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

}
