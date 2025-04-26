package com.gpoint.files_service.service.avatar.team;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.mapper.avatar.TeamAvatarMapper;
import com.gpoint.files_service.property.AmazonS3Properties;
import com.gpoint.files_service.repository.TeamAvatarRepository;
import com.gpoint.files_service.service.avatar.AbstractAvatarService;
import com.gpoint.files_service.service.s3.AmazonS3Service;
import com.gpoint.files_service.validator.avatar.AvatarValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@Service
public class TeamAvatarService extends AbstractAvatarService {

    private final TeamAvatarRepository teamAvatarRepository;
    private final TeamAvatarMapper teamAvatarMapper;
    private final AvatarValidator avatarValidator;

    public TeamAvatarService(AmazonS3Properties amazonS3Properties,
                             AmazonS3Service amazonS3Service,
                             TeamAvatarMapper teamAvatarMapper,
                             @Qualifier("teamAvatarValidator") AvatarValidator avatarValidator,
                             TeamAvatarRepository teamAvatarRepository) {
        super(amazonS3Properties, amazonS3Service);
        this.teamAvatarMapper = teamAvatarMapper;
        this.avatarValidator = avatarValidator;
        this.teamAvatarRepository = teamAvatarRepository;
    }

    @Override
    @Transactional
    public void saveAvatar(UUID id, MultipartFile file) {

        avatarValidator.validateAccess(id);

        if(teamAvatarRepository.findByTeamId(id) != null) deleteAvatar(id);

        String path = buildPath(String.valueOf(id));
        String key = amazonS3Service.uploadFile(path, compressPic(file), getBucketName("team-avatar")).join();
        File s3File = buildFile(file, key);

        teamAvatarRepository.save(teamAvatarMapper.toEntity(s3File, id));
        log.info("Saved avatar to tournament id: {}", id);
    }

    @Override
    public Resource getAvatar(UUID id) {
        if (teamAvatarRepository.findByTeamId(id) == null) return null;

        String key = teamAvatarRepository.findByTeamId(id).getKey();
        String path = buildPath(String.valueOf(id), key);

        return amazonS3Service.downloadFile(path, getBucketName("team-avatar")).join();
    }

    @Override
    public void deleteAvatar(UUID id) {
        avatarValidator.validateAccess(id);

        String key = teamAvatarRepository.findByTeamId(id).getKey();
        String path = buildPath(String.valueOf(id), key);
        amazonS3Service.deleteFile(path, getBucketName("team-avatar"));

        teamAvatarRepository.delete(teamAvatarRepository.findByTeamId(id));

        log.info("Successfully deleted avatar from team with team id={}", id);
    }


    @Override
    protected AmazonS3Properties.Expansion getExpansion() {
        return amazonS3Properties.getExpansion().get(objectKey);
    }

    @Override
    protected String getEntityKey() {
        return amazonS3Properties.getPaths().get("team");
    }

}