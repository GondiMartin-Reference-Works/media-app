package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "Story")
@Table(name = "story")
@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class StoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Include
    private AppUserEntity user;

    @Lob
    @Column(name = "image", length = 1000000)
    private byte[] image;

    @CreationTimestamp //https://www.javaguides.net/2022/02/spring-data-jpa-one-to-one-unidirectional-mapping.html
    @ToString.Include
    private Date time;
}
