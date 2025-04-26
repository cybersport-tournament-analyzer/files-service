package com.gpoint.files_service.service.avatar;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarService {

    String objectKey = "avatar";

    void saveAvatar(UUID id, MultipartFile file);

    Resource getAvatar(UUID id);

    void deleteAvatar(UUID id);
}
