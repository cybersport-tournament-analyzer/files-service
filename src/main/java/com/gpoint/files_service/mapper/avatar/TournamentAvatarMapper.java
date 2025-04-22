package com.gpoint.files_service.mapper.avatar;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.entity.tournament.TournamentAvatar;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentAvatarMapper {

    TournamentAvatar toEntity(File file, UUID tournamentId);
}