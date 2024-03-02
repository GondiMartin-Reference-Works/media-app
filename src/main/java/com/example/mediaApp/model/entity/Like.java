package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Like")
@Table(name = "_like")
@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Unidirectional
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}
