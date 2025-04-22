package com.gpoint.files_service.mapper.avatar;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.entity.team.TeamAvatar;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamAvatarMapper {

    TeamAvatar toEntity(File file, UUID teamId);
}
