package net.lanet.screensound;

import net.lanet.screensound.configs.ApplicationProperties;
import net.lanet.screensound.main.Main;
import net.lanet.screensound.repository.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner {

	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired
	private IArtistaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreensoundApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository, applicationProperties);
		main.viewMenu();
	}

}
