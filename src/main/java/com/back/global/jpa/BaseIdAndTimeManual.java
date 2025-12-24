package com.back.global.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
public abstract class BaseIdAndTimeManual extends BaseEntity{
        @Id
        private int id;
        private LocalDateTime createDate;
        private LocalDateTime modifyDate;

}
