package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity(name = "Group")
@Table(name = "_group")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image", length = 1000000)
    private byte[] image;

    // Unidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id")
    private AppUserEntity adminUser;

    // Bidirectional
    @ManyToMany
    @JoinTable(
            name = "group_participant",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_user_id"))
    private List<AppUserEntity> participantUsers;

    // Bidirectional
    @OneToMany(
            mappedBy = "group",
            orphanRemoval = true)
    private List<GroupRequestEntity> groupRequests;

    // Bidirectional
    @OneToMany(
            mappedBy = "group",
            orphanRemoval = true)
    private List<PostEntity> posts;
}
