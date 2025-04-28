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
    
//    // Nouvelles méthodes de recherche
//    List<User> findByNomContainingIgnoreCase(String nom);
//    List<User> findByPrenomContainingIgnoreCase(String prenom);
//    List<User> findByRole(String role);
//    List<User> findByPoste_IdPoste(Integer idPoste);
//    List<User> findByActif(Boolean actif);
// Ajoutez cette méthode à votre interface UserRepository
    List<User> findByEquipeIdEquipe(Integer idEquipe);
}

