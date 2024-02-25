package com.example.mediaApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import static java.lang.StringTemplate.STR;

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

    private byte[] image;

    @ToString.Include
    @CreationTimestamp //https://www.javaguides.net/2022/02/spring-data-jpa-one-to-one-unidirectional-mapping.html
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUserEntity user;
}
