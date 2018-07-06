package net.seibertmedia.rocket.combinedidtest;

import static java.lang.System.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.seibertmedia.rocket.combinedidtest.model.User;
import net.seibertmedia.rocket.combinedidtest.model.UserRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class CombinedIdTestApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(CombinedIdTestApplication.class);

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CombinedIdTestApplication.class, args);
	}

	@Override
	public void run(String... args) {
		User user = new User()
				.setUsername("my-username")
				.setHashedPassword("hashed-pw")
				.setEnabled(true)
				.addAuthority("foo")
				.addAuthority("bar");

		userRepository.save(user);

		User user2 = new User()
				.setUsername("other-username")
				.setHashedPassword("hashed-pw")
				.setEnabled(true)
				.addAuthority("moo")
				.addAuthority("zoo")
				.addAuthority("qoo");

		userRepository.save(user2);

		log.info("User-Count: {}", userRepository.count());

		exit(0);
	}
}
