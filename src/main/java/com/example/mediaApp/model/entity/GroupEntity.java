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

    private String name;

    private String description;

    @Lob
    @Column(length = 1000000)
    private byte[] image;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "admin_user_id")
    private AppUserEntity adminUser;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUserEntity> participantUsers;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GroupRequestEntity> groupRequests;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostEntity> posts;
}
