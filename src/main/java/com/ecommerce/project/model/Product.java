package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    private String name;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrise;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public void calculateAndSetSpecialPrice() {
        this.specialPrise = this.price - (this.price * (this.discount * 0.01));
    }

}
