package io.fulflix.user.application;

import io.fulflix.user.api.retrieve.dto.UserResponse;
import io.fulflix.user.exception.UserErrorCode;
import io.fulflix.user.exception.UserException;
import io.fulflix.user.repo.UserRepo;
import io.fulflix.user.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRetrieveService {

    private final UserRepo userRepo;

    public UserResponse loadUserById(Long id) {
        return UserResponse.from(findById(id));
    }

    public Page<UserResponse> loadAllUsersByPageable(Pageable pageable) {
        return userRepo.findAll(pageable).map(UserResponse::from);
    }

    public User findById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST, id));
    }

}
