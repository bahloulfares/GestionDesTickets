package com.csys.template.service;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.factory.EquipeFactory;
import com.csys.template.repository.EquipeRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csys.template.repository.UserRepository;
import com.csys.template.domain.User;
import com.csys.template.web.rest.errors.IllegalBusinessLogiqueException;
import java.util.ArrayList;

/**
 * Service Implementation for managing Equipe.
 */
@Service
@Transactional
public class EquipeService {
  private final Logger log = LoggerFactory.getLogger(EquipeService.class);

  private final EquipeRepository equipeRepository;
  // En haut de la classe
  private final UserRepository userRepository;
  
  public EquipeService(EquipeRepository equipeRepository, UserRepository userRepository) {
      this.equipeRepository = equipeRepository;
      this.userRepository = userRepository;
  }

  /**
   * Save a equipeDTO.
   *
   * @param equipeDTO
   * @return the persisted entity
   */
  public EquipeDTO save(EquipeDTO equipeDTO) {
    log.debug("Request to save Equipe : {}", equipeDTO);
    Preconditions.checkArgument(equipeDTO.getIdEquipe() == null, "equipe.id.exists");
    
    // Sauvegarder d'abord l'équipe sans les utilisateurs
    Equipe equipe = EquipeFactory.equipeDTOToEquipe(equipeDTO);
    List<User> users = equipe.getUsers();
    equipe.setUsers(null);
    final Equipe savedEquipe = equipeRepository.save(equipe);
    
    // Maintenant que l'équipe a un ID, associer les utilisateurs et les sauvegarder
    if (users != null && !users.isEmpty()) {
        // Vérifier que chaque utilisateur n'est pas déjà affecté à une équipe
        for (User user : users) {
            // Si l'utilisateur existe déjà, vérifier s'il a déjà une équipe
            if (user.getUsername() != null) {
                User existingUser = userRepository.findById(user.getUsername()).orElse(null);
                if (existingUser != null && existingUser.getEquipe() != null) {
                    throw new IllegalBusinessLogiqueException("user.already.assigned.to.team");
                }
            }
            user.setEquipe(savedEquipe);
        }
        userRepository.saveAll(users);
        savedEquipe.setUsers(users);
    }
    
    return EquipeFactory.equipeToEquipeDTO(savedEquipe);
}

  /**
   * Update a equipeDTO.
   *
   * @param equipeDTO
   * @return the updated entity
   */
  public EquipeDTO update(EquipeDTO equipeDTO) {
    log.debug("Request to update Equipe: {}", equipeDTO);
    Equipe inBase = equipeRepository.findById(equipeDTO.getIdEquipe()).orElse(null);
    Preconditions.checkArgument(inBase != null, "equipe.NotFound");

    // Convertir EquipeDTO en Equipe pour obtenir les utilisateurs
    Equipe equipe = EquipeFactory.equipeDTOToEquipe(equipeDTO);
    
    // Liste des utilisateurs dans la nouvelle équipe
    List<User> newUsers = equipe.getUsers() != null ? equipe.getUsers() : new ArrayList<>();

    // Liste des utilisateurs actuellement affectés à cette équipe
    List<User> currentUsers = userRepository.findByEquipeIdEquipe(equipeDTO.getIdEquipe());

    // Supprimer ceux qui ne sont plus présents
    for (User oldUser : currentUsers) {
        boolean stillExists = newUsers.stream()
            .anyMatch(newUser -> newUser.getUsername().equals(oldUser.getUsername()));
        if (!stillExists) {
            log.debug("Détachement de l'utilisateur {}", oldUser.getUsername());
            oldUser.setEquipe(null);
            userRepository.save(oldUser);
        }
    }

    // Réaffecter les nouveaux utilisateurs
    for (User user : newUsers) {
        if (user.getUsername() != null) {
            User existingUser = userRepository.findById(user.getUsername()).orElse(null);
            if (existingUser != null) {
                existingUser.setEquipe(inBase);
                userRepository.save(existingUser);
            }
        }
    }

    // Mettre à jour les champs de l'équipe elle-même
    inBase.setDesignation(equipeDTO.getDesignation());
    inBase.setActif(equipeDTO.getActif());
    equipeRepository.save(inBase);

    return EquipeFactory.equipeToEquipeDTO(inBase);
}

  /**
   * Get one equipeDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public EquipeDTO findOne(Integer id) {
    log.debug("Request to get Equipe: {}",id);
    Equipe equipe= equipeRepository.findById(id).orElse(null);
    EquipeDTO dto = EquipeFactory.equipeToEquipeDTO(equipe);
    return dto;
  }

  /**
   * Get one equipe by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Equipe findEquipe(Integer id) {
    log.debug("Request to get Equipe: {}",id);
    Equipe equipe= equipeRepository.findById(id).orElse(null);
    return equipe;
  }

  /**
   * Get all the equipes.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<EquipeDTO> findAll() {
    log.debug("Request to get All Equipes");
    Collection<Equipe> result= equipeRepository.findAll();
    return EquipeFactory.equipeToEquipeDTOs(result);
  }

  /**
   * Delete equipe by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
      log.debug("Request to delete Equipe: {}", id);
      
      // Récupérer l'équipe avec ses utilisateurs
      Equipe equipe = equipeRepository.findById(id).orElse(null);
      if (equipe == null) {
          throw new IllegalBusinessLogiqueException("equipe.NotFound");
      }
      
      // Détacher tous les utilisateurs de l'équipe avant de la supprimer
      if (equipe.getUsers() != null && !equipe.getUsers().isEmpty()) {
          log.debug("Detaching {} users from team before deletion", equipe.getUsers().size());
          
          for (User user : equipe.getUsers()) {
              user.setEquipe(null);
          }
          userRepository.saveAll(equipe.getUsers());
      }
      
      // Maintenant supprimer l'équipe
      equipeRepository.deleteById(id);
  }
}

