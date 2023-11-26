package ru.simankovd.springredditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.simankovd.springredditclone.config.OpenApiConfiguration;

@EnableAsync
@SpringBootApplication
@Import(OpenApiConfiguration.class)
public class  SpringRedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedditCloneApplication.class, args);
	}

}
