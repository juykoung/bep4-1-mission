package com.back.boundedContext.post.domain;

import com.back.global.jpa.BaseIdAndTime;
import com.back.global.jpa.BaseIdAndTimeManual;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "POST_MEMBER")
public class PostMember extends BaseIdAndTimeManual {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;
}
