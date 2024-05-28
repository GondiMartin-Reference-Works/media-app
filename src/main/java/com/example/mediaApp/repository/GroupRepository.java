package com.example.mediaApp.repository;

import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    List<GroupEntity> findGroupEntitiesByAdminUser_Id(Long adminUserId);

    List<GroupEntity> findGroupEntitiesByParticipantUsersContaining(AppUserEntity participant);
}
