package com.example.mediaApp.repository;

import com.example.mediaApp.model.entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {
}
