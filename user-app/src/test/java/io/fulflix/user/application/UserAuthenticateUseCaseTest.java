package io.fulflix.user.application;

import static io.fulflix.fixture.UserFixtures.CREATE_PRINCIPAL_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.user.exception.UserErrorCode;
import io.fulflix.user.exception.UserException;
import io.fulflix.user.repo.UserRepo;
import io.fulflix.user.repo.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;

@AutoConfigureTestEntityManager
@ExtendWith(MockitoExtension.class)
@DisplayName("UseCase:UserAuthenticate")
class UserAuthenticateUseCaseTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserAuthenticateUseCase userAuthenticateUseCase;

    @Test
    @DisplayName("회원 생성")
    void createPrincipal() {
        // Given
        User given = CREATE_PRINCIPAL_REQUEST.toEntity();
        given(userRepo.existsByUsername(anyString())).willReturn(false);
        doNothing().when(entityManager).persist(given);

        // When
        Long actual = userAuthenticateUseCase.createUser(CREATE_PRINCIPAL_REQUEST);

        // Then
        assertThat(actual).isEqualTo(given.getId());
        verify(entityManager, times(1)).persist(given);
        verify(entityManager, times(1)).flush();
    }

    @Test
    @DisplayName("[예외] 회원 아이디 중복")
    void throwException_whenUsernameDuplicated() {
        // Given
        given(userRepo.existsByUsername(anyString())).willReturn(true);

        // When & Then
        assertThatExceptionOfType(UserException.class)
            .isThrownBy(() -> userAuthenticateUseCase.createUser(CREATE_PRINCIPAL_REQUEST))
            .extracting(UserException::getErrorCode)
            .isEqualTo(UserErrorCode.DUPLICATED_USERNAME);

        verify(userRepo, times(1)).existsByUsername(anyString());
    }

}
