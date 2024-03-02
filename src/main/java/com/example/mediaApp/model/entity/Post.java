package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String text;

    @Lob
    @Column(length = 1000000)
    private byte[] image;

    // Unidirectional
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Like> likes;

    // Unidirectional
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    @OneToMany(
            mappedBy = "id",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Report> reports;
}
