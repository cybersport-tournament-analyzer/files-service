package com.gpoint.files_service.controller;

import com.gpoint.files_service.annotation.image.Image;
import com.gpoint.files_service.dto.FileDto;
import com.gpoint.files_service.service.avatar.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/avatars/team/{teamId}")
@Validated
@Tag(name = "team avatars")
public class TeamAvatarController {

    private final AvatarService avatarService;

    public TeamAvatarController(@Qualifier("teamAvatarService") AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload team avatar")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto uploadTeamAvatar(@Image @RequestParam("file") MultipartFile file, @PathVariable String teamId) {

        return avatarService.saveAvatar(UUID.fromString(teamId), file);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get team avatar")
    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public Resource getSmallTeamAvatar(@PathVariable String teamId) {

        return avatarService.getAvatar(UUID.fromString(teamId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete team avatar")
    public void deleteTeamAvatar(@PathVariable String teamId) {

        avatarService.deleteAvatar(UUID.fromString(teamId));
    }


}
