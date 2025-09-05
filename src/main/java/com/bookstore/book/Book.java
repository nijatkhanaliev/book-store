package com.bookstore.book;

import com.bookstore.category.Category;
import com.bookstore.common.BaseEntity;
import com.bookstore.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {
    private String title;
    private String author;
    @Column(nullable = false, unique = true)
    private String isbn;
    private Integer pages;
    private Double price;
    private String language;
    private String description;
    private String coverImage;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
