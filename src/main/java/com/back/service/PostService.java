//package com.back.service;
//
//import com.back.entity.Member;
//import com.back.entity.Post;
//import com.back.global.exception.EventPublisher.EventPublisher;
//import com.back.repository.PostRepository;
//
//import java.util.Optional;
//
//public class PostService {
//    private final PostRepository postRepository;
//    private final EventPublisher eventPublisher;
//
//    public Optional<Post> findById() {
//
//    }
//
//    public Post write(Member author, String title, String content) {
//        Post post = postRepository.save(new Post(author, title, content));
//
//        eventPublisher.publish(
//                new PostCreatedEvnet()
//        );
////        author.increseActiveScore(3);
//        return post;
//    }
//}
//
