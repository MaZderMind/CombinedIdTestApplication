package net.seibertmedia.rocket.combinedidtest.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, UserAuthorityId> {

	List<UserAuthority> findByUserId(UUID userId);
}
