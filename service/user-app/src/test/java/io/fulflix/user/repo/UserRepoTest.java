package io.fulflix.user.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.fulflix.core.app.base.persistence.JpaTestBase;
import io.fulflix.common.web.principal.Role;
import io.fulflix.user.repo.model.User;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Repo:JPA:User")
class UserRepoTest extends JpaTestBase {

    private final UserRepo userRepo;

    public UserRepoTest(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private final User user = User.of("username", "password", "홍길동", Role.MASTER_ADMIN);

    @Nested
    @DisplayName("User Entity를")
    class UserCRUDTest {

        @Test
        @DisplayName("저장")
        void save() {
            // When
            User actual = userRepo.save(user);

            // Then
            assertThat(actual.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("조회")
        void findById() {
            // Given
            User given = userRepo.save(user);

            // When
            User actual = userRepo.findById(given.getId()).orElseThrow();

            // Then
            assertThat(actual).isEqualTo(given);
        }

        @Test
        @DisplayName("수정")
        void updateById() {
            // Given
            User given = userRepo.save(user);
            final Long currentUser = 1L;

            // When
            executeWithFlush(() -> given.applyUserCreated(currentUser));

            // Then
            assertThat(given.getCreatedBy()).isEqualTo(currentUser);
        }

        @Test
        @DisplayName("삭제")
        void deleteById() {
            // Given
            User given = userRepo.save(user);

            // When
            executeWithFlush(given::delete);

            // Then
            assertThat(given.isDeleted()).isTrue();
            assertThat(given.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("삭제 후 조회 불가")
        void throwException_whenFindDeletedUser() {
            // Given
            User given = userRepo.save(user);
            executeWithFlushAndClear(given::delete);

            // When & Then
            assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> userRepo.findById(given.getId()).orElseThrow());
        }

    }

}
