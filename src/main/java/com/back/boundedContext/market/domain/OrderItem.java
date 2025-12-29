package com.back.boundedContext.market.domain;

import com.back.global.jpa.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ODER_ITEM")
@NoArgsConstructor
@Getter
public class OrderItem extends BaseIdAndTime {
    /**
     * 주문상품에 필요한 것
     * <p>
     * 주문
     * 상품
     * 상품이름
     * 상품가격
     * 상품 세일 가격
     * ?? 수수료
     */

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String productName;
    private long price;
    private long salePrice;

    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, String productName, long price, long salePrice) {
        this.order = order;
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
    }
}
