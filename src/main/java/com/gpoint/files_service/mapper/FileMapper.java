package com.gpoint.files_service.mapper;

import com.gpoint.files_service.dto.FileDto;
import com.gpoint.files_service.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    FileDto toDto(File file);
}
