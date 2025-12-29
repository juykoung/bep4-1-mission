package com.back.shared.cash.event;

import com.back.boundedContext.market.domain.Order;
import com.back.shared.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashOrderSucceededEvent {
    private final OrderDto order;
    private final long pgPaymentAmount;
}
