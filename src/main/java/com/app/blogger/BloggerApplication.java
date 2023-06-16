package com.app.blogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BloggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggerApplication.class, args);
	}

}
