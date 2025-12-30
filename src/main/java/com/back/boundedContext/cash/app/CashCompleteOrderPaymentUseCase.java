package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.exception.EventPublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void completeOrderPayment(OrderDto order, long pgPaymentAmount) {
        Wallet customerWallet = cashSupport.findWalletByHolderId(order.getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        if (pgPaymentAmount > 0) {
            customerWallet.credit(
                    pgPaymentAmount,
                    CashLog.EventType.충전__PG결제_토스페이먼츠,
                    "Order",
                    order.getId()
            );
        }
        boolean canPay = customerWallet.getBalance() >= order.getSalePrice();

        if (canPay) {
            //고객 지갑에서 차감 (결제?)
            customerWallet.debit(
                    order.getSalePrice(),
                    CashLog.EventType.사용__주문결제,
                    "Order",
                    order.getId()
            );
            // 홀딩에 구매한 금액 추가(홀딩)
            holdingWallet.credit(
                    order.getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    "Order",
                    order.getId()
            );
            // 성공 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentSucceededEvent(
                            order,
                            pgPaymentAmount
                    )
            );
        } else {
            // 실패 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(order.getId()),
                            order,
                            pgPaymentAmount,
                            pgPaymentAmount - customerWallet.getBalance()
                    )
            );
        }

    }
}
