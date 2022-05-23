package com.jpa.boilerplate.jpaboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EnableJpaRepositories(basePackages = { "com.jpa.boilerplate.jpaboilerplate.repository" })
// @EntityScan(basePackages = { "com.jpa.boilerplate.jpaboilerplate.entity" })
public class JpaboilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaboilerplateApplication.class, args);
	}

}
