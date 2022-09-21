package com.sicredi.assembly.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sicredi.assembly.config.environment.TestDBService;

@Configuration
@Profile("tst")
public class TestConfig {

	@Autowired
	private TestDBService testDBService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if ("create".equals(strategy)) {
			testDBService.instantiateTestDatabase();
			return true;
		}
		
		return false;
	}

}
