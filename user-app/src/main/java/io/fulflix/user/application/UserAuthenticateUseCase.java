package io.fulflix.user.application;

import io.fulflix.user.api.authenticate.dto.CreatePrincipalRequest;
import io.fulflix.user.api.authenticate.dto.UserCredentialResponse;
import io.fulflix.user.exception.UserErrorCode;
import io.fulflix.user.exception.UserException;
import io.fulflix.user.repo.UserRepo;
import io.fulflix.user.repo.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthenticateUseCase {

    private final EntityManager entityManager;
    private final UserRepo userRepo;

    @Transactional
    public Long createUser(CreatePrincipalRequest request) {
        User transientUser = request.toEntity();
        User savedUser = saveUser(transientUser);

        return savedUser.getId();
    }

    private User saveUser(User transientUser) {
        entityManager.persist(transientUser);
        entityManager.flush();
        transientUser.applyUserCreated(transientUser.getId());
        return transientUser;
    }

    public UserCredentialResponse loadUserCredentialByUsername(String username) {
        return userRepo.findByUsername(username)
            .map(UserCredentialResponse::from)
            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST, username));
    }

}
