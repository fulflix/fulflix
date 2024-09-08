package io.fulflix.common.app.base.persistence;

import io.fulflix.common.app.config.TestUserAuditorAwareConfig;
import io.fulflix.common.app.jpa.JpaConfig;
import io.fulflix.common.web.base.TestBase;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import java.util.function.Supplier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({JpaConfig.class, TestUserAuditorAwareConfig.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class JpaTestBase extends TestBase {

    @Resource
    protected EntityManager entityManager;

    protected <T> T executeWithPersistContextClear(Supplier<T> supplier) {
        try {
            return supplier.get();
        } finally {
            entityManager.clear();
        }
    }

    protected void executeWithFlush(Runnable runnable) {
        try {
            runnable.run();
        } finally {
            entityManager.flush();
        }
    }

    protected void executeWithFlushAndClear(Runnable runnable) {
        try {
            executeWithFlush(runnable);
        } finally {
            entityManager.clear();
        }
    }

}
