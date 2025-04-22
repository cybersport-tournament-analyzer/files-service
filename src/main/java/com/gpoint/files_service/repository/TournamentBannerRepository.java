package com.gpoint.files_service.repository;

import com.gpoint.files_service.entity.tournament.TournamentBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TournamentBannerRepository extends JpaRepository<TournamentBanner, UUID> {

    TournamentBanner findByTournamentId(UUID tournamentId);

    void deleteByKey(String key);

    boolean existsByTournamentId(UUID tournamentId);

}
