package com.gpoint.files_service.validator.avatar.team;


import com.gpoint.files_service.exception.ValidationException;
import com.gpoint.files_service.repository.TeamAvatarRepository;
import com.gpoint.files_service.validator.avatar.AvatarValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class TeamAvatarValidator implements AvatarValidator {

    private final TeamAvatarRepository teamAvatarRepository;

    @Override
    public void validateAccess(UUID teamId) {

//        if (!username.equals(currentUsername)) {
//            throw new ValidationException("User with username=" + username + " not creator");
//        }
    }

}