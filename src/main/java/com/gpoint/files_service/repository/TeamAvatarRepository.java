package com.gpoint.files_service.repository;

import com.gpoint.files_service.entity.team.TeamAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamAvatarRepository extends JpaRepository<TeamAvatar, UUID> {

    TeamAvatar findByTeamId(UUID teamId);

    void deleteByKey(String key);

    boolean existsByTeamId(UUID teamId);
}
