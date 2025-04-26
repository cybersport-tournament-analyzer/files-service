package com.gpoint.files_service.service.avatar.tournament;

import com.gpoint.files_service.entity.File;
import com.gpoint.files_service.entity.tournament.TournamentAvatar;
import com.gpoint.files_service.mapper.avatar.TournamentAvatarMapper;
import com.gpoint.files_service.property.AmazonS3Properties;
import com.gpoint.files_service.repository.TournamentAvatarRepository;
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
public class TournamentAvatarService extends AbstractAvatarService {

    private final TournamentAvatarRepository tournamentAvatarRepository;
    private final TournamentAvatarMapper tournamentAvatarMapper;
    private final AvatarValidator avatarValidator;

    public TournamentAvatarService(AmazonS3Properties amazonS3Properties,
                                   AmazonS3Service amazonS3Service,
                                   TournamentAvatarMapper tournamentAvatarMapper,
                                   @Qualifier("tournamentAvatarValidator") AvatarValidator avatarValidator,
                                   TournamentAvatarRepository tournamentAvatarRepository) {
        super(amazonS3Properties, amazonS3Service);
        this.tournamentAvatarMapper = tournamentAvatarMapper;
        this.avatarValidator = avatarValidator;
        this.tournamentAvatarRepository = tournamentAvatarRepository;
    }

    @Override
    @Transactional
    public void saveAvatar(UUID id, MultipartFile file) {

        avatarValidator.validateAccess(id);

        if(tournamentAvatarRepository.findByTournamentId(id) != null) deleteAvatar(id);

        String path = buildPath(String.valueOf(id));
        String key = amazonS3Service.uploadFile(path, compressPic(file), getBucketName("tournament-avatar")).join();

        File s3File = buildFile(file, key);
        tournamentAvatarRepository.save(tournamentAvatarMapper.toEntity(s3File, id));
        log.info("Saved avatar to tournament id: {}", id);
    }

    @Override
    public Resource getAvatar(UUID id) {
        if (tournamentAvatarRepository.findByTournamentId(id) == null) return null;

        String key = tournamentAvatarRepository.findByTournamentId(id).getKey();
        String path = buildPath(String.valueOf(id), key);

        return amazonS3Service.downloadFile(path, getBucketName("tournament-avatar")).join();
    }

    @Override
    public void deleteAvatar(UUID id) {
        avatarValidator.validateAccess(id);


        String key = tournamentAvatarRepository.findByTournamentId(id).getKey();
        String path = buildPath(String.valueOf(id), key);
        amazonS3Service.deleteFile(path, getBucketName("tournament-avatar"));

        tournamentAvatarRepository.delete(tournamentAvatarRepository.findByTournamentId(id));

        log.info("Successfully deleted avatar from team with tournament id={}", id);
    }


    @Override
    protected AmazonS3Properties.Expansion getExpansion() {
        return amazonS3Properties.getExpansion().get(objectKey);
    }

    @Override
    protected String getEntityKey() {
        return amazonS3Properties.getPaths().get("tournament");
    }

}
