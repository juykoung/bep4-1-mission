package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.exception.EventPublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public CashMember syncMember(MemberDto member) {
        boolean isNew = !cashMemberRepository.existsById(member.getId());

        // 수정했을 땐 저장
        CashMember _member = cashMemberRepository.save(
                new CashMember(
                    member.getId(),
                    member.getCreateDate(),
                    member.getModifyDate(),
                    member.getUsername(),
                    "",
                    member.getNickname(),
                    member.getActivityScore()
                )
        );

        /** 수정아닌 생성일 땐 이벤트 발행
         * -> CashEventListener의 createWallet 관련 핸들러가 받아서 처리함
        */
        if (isNew) {
            eventPublisher.publish(
                    new CashMemberCreatedEvent(
                            new CashMemberDto(_member)
                    )
            );
        }

        return _member;
    }
}
