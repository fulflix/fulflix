package io.fulflix.user.application;


import io.fulflix.user.repo.UserRepo;
import io.fulflix.user.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMyPageService {

    private final UserRetrieveService userRetrieveService;
    private final UserRepo userRepo;

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRetrieveService.findById(id);
        userRepo.delete(user);
    }

}
