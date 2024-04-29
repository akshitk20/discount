package shoppingcart.discount.model;

import jakarta.persistence.Entity;

@Entity
public record Coupon(int id, String productName, String description, int amount) {
}
