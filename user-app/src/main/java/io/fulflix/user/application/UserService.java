package io.fulflix.user.application;

import io.fulflix.user.api.dto.UserCreateRequest;
import io.fulflix.user.repo.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final EntityManager entityManager;

    @Transactional
    public Long createUser(UserCreateRequest request) {
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

}
