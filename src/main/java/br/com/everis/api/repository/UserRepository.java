package br.com.everis.api.repository;

import br.com.everis.api.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    UserEntity findByEmail(String name);
}
