package com.example.mediaApp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUserEntity user;

    // Bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @Column(name = "text", nullable = false)
    private String text;

    @Lob
    @Column(name = "image", length = 10000000)
    private byte[] image;

    // Unidirectional
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<LikeEntity> likes;

    // Unidirectional
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<CommentEntity> comments;

    // Bidirectional
    @OneToMany(
            mappedBy = "post",
            orphanRemoval = true)
    private List<ReportEntity> reports;
}
