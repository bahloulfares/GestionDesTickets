package com.csys.template.repository;

import com.csys.template.domain.Demande;
import com.csys.template.domain.enums.EtatDemande;
import com.csys.template.domain.enums.PrioriteDemande;
import java.lang.Integer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Demande entity.
 */
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer>, QuerydslPredicateExecutor<Demande> {
    
    // Méthodes essentielles pour les filtres de base
    List<Demande> findByEtat(EtatDemande etat);
    List<Demande> findByDateCreationBetween(LocalDate dateDebut, LocalDate dateFin);
    
    // Méthodes nécessaires pour les requêtes WhereClauseBuilder
    List<Demande> findByClientIdClient(Integer idClient);
    List<Demande> findByModuleIdModule(Integer idModule);
    List<Demande> findByEquipeIdEquipe(Integer idEquipe);
}

