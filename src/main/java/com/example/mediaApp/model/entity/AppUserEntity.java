package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @ToString.Include
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @ToString.Include
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Lob
    @Column(name = "profile_picture", length = 1000000)
    private byte[] profilePicture;

    // Unidirectional
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<AddressEntity> addresses;

    // Bidirectional
    // Storing friend-requests only from other users
    @OneToMany(
            mappedBy = "receiverUser",
            orphanRemoval = true)
    private List<FriendRequestEntity> friendRequests;

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<FriendConnectionEntity> friendConnections;

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<StoryEntity> stories;

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<PostEntity> posts;

    // Bidirectional
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "participantUsers")
    private List<GroupEntity> joinedGroups;
}