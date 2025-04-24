package com.csys.template.repository;

import com.csys.template.domain.Equipe;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipe entity.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
  Collection<Equipe> findByActif(Boolean actif);
}

