package net.seibertmedia.rocket.combinedidtest.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByUsernameLike(String usernameLike);

	@Query("SELECT u from User u WHERE LOWER(u.username) = TRIM(BOTH from LOWER(?1))")
	Optional<User> findByUsernameTrimmed(String username);
}
