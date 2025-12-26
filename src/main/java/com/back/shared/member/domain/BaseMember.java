package com.back.shared.member.domain;

import com.back.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@MappedSuperclass
@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor
public abstract class BaseMember extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activeScore;

    public BaseMember(String username, String password, String nickname,int activeScore) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.activeScore = activeScore;
    }
}
