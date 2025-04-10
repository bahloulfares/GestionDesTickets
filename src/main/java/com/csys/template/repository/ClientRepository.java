package com.csys.template.repository;

import com.csys.template.domain.Client;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}

