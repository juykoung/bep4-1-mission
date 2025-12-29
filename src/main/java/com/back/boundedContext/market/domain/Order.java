package com.back.boundedContext.market.domain;

import com.back.global.jpa.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "MARKET_ORDER")
@Getter
@NoArgsConstructor
public class Order extends BaseIdAndTime {
    /**
     * 주문에 필요한 것들
     *
     * 구매자
     * 상품 목록
     * 가격
     * 세일가격
     */
    @ManyToOne(fetch = LAZY)
    private MarketMember buyer;

    private long price;
    private long salePrice;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getBuyer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product) {
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );

        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();

    }
}
