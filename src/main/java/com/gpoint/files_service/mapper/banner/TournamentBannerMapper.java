package com.gpoint.files_service.mapper.banner;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.entity.tournament.TournamentBanner;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentBannerMapper {

    TournamentBanner toEntity(File file, UUID tournamentId);
}