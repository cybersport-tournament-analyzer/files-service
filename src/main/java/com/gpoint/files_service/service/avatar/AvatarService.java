package com.gpoint.files_service.service.avatar;

import com.gpoint.files_service.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarService {

    String objectKey = "avatar";

    FileDto saveAvatar(UUID id, MultipartFile file);

    Resource getAvatar(UUID id);

    void deleteAvatar(UUID id);
}
