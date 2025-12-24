package com.back.boundedContext.post.domain;

import com.back.boundedContext.member.domain.Member;
import com.back.global.jpa.BaseIdAndTime;
import com.back.shared.dto.PostCommentDto;
import com.back.shared.event.PostCommentCreatedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    Member author;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    // jpa가 더티체크 -> 내부에 있는 변경이 있으면 쿼리를 날려줌
    @OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(Member author, String content) {
        PostComment postComment = new PostComment(this, author, content);
        comments.add(postComment);
//        author.increseActiveScore(1);
        publishEvent(new PostCommentCreatedEvent(new PostCommentDto(postComment)));
        return postComment;
    }

    public boolean hasComments() {
        return !comments.isEmpty();
    }
}
