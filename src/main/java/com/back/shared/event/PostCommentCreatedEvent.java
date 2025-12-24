package com.back.shared.event;


import com.back.shared.dto.PostCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCommentCreatedEvent {
    private final PostCommentDto postComment;
}
