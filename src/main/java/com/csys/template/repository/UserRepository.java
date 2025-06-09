package com.csys.template.repository;

import com.csys.template.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByUsername(String username);
    List<User> findByEquipeIdEquipe(Integer idEquipe);
}

