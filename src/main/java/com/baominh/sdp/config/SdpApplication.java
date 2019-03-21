package com.baominh.sdp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableTransactionManagement
@EntityScan(basePackages = "com.baominh.sdp.entity")
@EnableJpaRepositories(basePackages = "com.baominh.sdp.repositoty.jpa")
@ComponentScan(basePackages = { "com.baominh.sdp" })
public class SdpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdpApplication.class, args);
	}

}
