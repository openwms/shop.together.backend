package io.interface21.shop2gether;

import org.ameba.annotation.EnableAspects;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
@EnableJpaAuditing
@EnableTransactionManagement
@EnableAspects
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    public
    @Bean
    BeanMapper serviceMapper() {
        return new DozerMapperImpl("META-INF/dozer/bean-mappings.xml");
    }
}
