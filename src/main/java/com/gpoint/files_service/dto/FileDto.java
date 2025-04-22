package com.gpoint.files_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private UUID id;
    private String key;
    private long size;
    private LocalDateTime createdAt;
    private String name;
    private String type;
}