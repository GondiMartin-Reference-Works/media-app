package com.example.mediaApp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class AppUserEntity implements UserDetails {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @ToString.Include
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @ToString.Include
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

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
    private List<AddressEntity> addresses = List.of();

    // Bidirectional
    // Storing friend-requests only from other users
    @OneToMany(
            mappedBy = "receiverUser",
            orphanRemoval = true)
    private List<FriendRequestEntity> friendRequests = List.of();

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<FriendConnectionEntity> friendConnections = List.of();

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<StoryEntity> stories = List.of();

    // Bidirectional
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true)
    private List<PostEntity> posts = List.of();

    // Bidirectional
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "participantUsers")
    private List<GroupEntity> joinedGroups = List.of();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }
}