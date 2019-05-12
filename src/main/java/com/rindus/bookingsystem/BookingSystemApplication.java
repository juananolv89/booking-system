package com.rindus.bookingsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class BookingSystemApplication {

    /**
     * Main class of the application.
     */
    public static void main(String[] args) {
	SpringApplication.run(BookingSystemApplication.class, args);
    }

    /** Component in charge of mapping entities to DTOs data objects */
    @Bean
    public ModelMapper modelMapper() {
	return new ModelMapper();
    }
}
