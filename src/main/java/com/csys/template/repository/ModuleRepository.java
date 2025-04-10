package com.csys.template.repository;

import com.csys.template.domain.Module;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Module entity.
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
  Collection<Module> findByActif(Boolean actif);
}

