package com.aluracursos.playhunt;

import com.aluracursos.playhunt.main.Main;
import com.aluracursos.playhunt.repository.GameRepository;
import com.aluracursos.playhunt.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlayhuntApplication implements CommandLineRunner {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlatformRepository platformRepository;

	public static void main(String[] args) {
		SpringApplication.run(PlayhuntApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(gameRepository, platformRepository);
		main.menu();
	}
}
