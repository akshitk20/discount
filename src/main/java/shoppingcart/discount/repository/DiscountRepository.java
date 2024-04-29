package shoppingcart.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingcart.discount.model.Coupon;

public interface DiscountRepository extends JpaRepository<Coupon, Integer> {
}
