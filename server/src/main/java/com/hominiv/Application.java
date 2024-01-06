package com.hominiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @SpringBootApplication - the main entry point of our service. This is where Spring
 * 							starts its initialization of all our different components
 * 							and services, preparing them for use.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/**
	 * I'm not even sure these are necessary for our use case, but if so they're
	 * letting spring know where to find repositories and database entities.
	 */
	@EnableJpaRepositories(basePackages = "com.hominiv.resources.repositories")
	@EntityScan(basePackages = "com.hominiv.resources.dataobjects")
	public class DataConfiguration {}
}
