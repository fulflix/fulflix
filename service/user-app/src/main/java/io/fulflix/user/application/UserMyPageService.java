package io.fulflix.user.application;

import io.fulflix.user.api.retrieve.dto.UserResponse;
import io.fulflix.user.exception.UserErrorCode;
import io.fulflix.user.exception.UserException;
import io.fulflix.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMyPageService {

    private final UserRepo userRepo;

    public UserResponse loadUserById(Long id) {
        return userRepo.findById(id).map(UserResponse::from)
            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST, id));
    }

}
