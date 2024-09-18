package io.fulflix.product.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.fulflix.product.repo.ProductQueryRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;

public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ProductQueryRepo memberQueryRepo() {
        return new ProductQueryRepo(jpaQueryFactory());
    }

}
