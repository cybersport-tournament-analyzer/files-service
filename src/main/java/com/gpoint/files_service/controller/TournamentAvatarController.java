package com.gpoint.files_service.controller;

import com.gpoint.files_service.annotation.image.Image;
import com.gpoint.files_service.service.avatar.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/avatars/tournament/{tournamentId}")
@Tag(name = "tournament avatars")
public class TournamentAvatarController {

    private final AvatarService avatarService;

    public TournamentAvatarController(@Qualifier("tournamentAvatarService") AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload tournament avatar")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadTournamentAvatar(@Image @RequestParam("file") MultipartFile file, @PathVariable String tournamentId) {

        avatarService.saveAvatar(UUID.fromString(tournamentId), file);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get tournament avatar")
    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public Resource getSmallTournamentAvatar(@PathVariable String tournamentId) {

        return avatarService.getAvatar(UUID.fromString(tournamentId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete tournament avatar")
    public void deleteTournamentAvatar(@PathVariable String tournamentId) {

        avatarService.deleteAvatar(UUID.fromString(tournamentId));
    }
}
