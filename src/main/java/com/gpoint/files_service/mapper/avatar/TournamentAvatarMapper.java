package com.gpoint.files_service.mapper.avatar;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.entity.tournament.TournamentAvatar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentAvatarMapper {

    @Mapping(source = "file.key", target = "key")
    TournamentAvatar toEntity(File file, UUID tournamentId);
}