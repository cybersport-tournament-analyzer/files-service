package com.gpoint.files_service.validator.avatar.tournament;

import com.gpoint.files_service.exception.ValidationException;
import com.gpoint.files_service.repository.TournamentAvatarRepository;
import com.gpoint.files_service.validator.avatar.AvatarValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class TournamentAvatarValidator implements AvatarValidator {

    private final TournamentAvatarRepository teamAvatarRepository;

    @Override
    public void validateAccess(UUID teamId) {

//        if (!username.equals(currentUsername)) {
//            throw new ValidationException("User with username=" + username + " not creator");
//        }
    }

}
