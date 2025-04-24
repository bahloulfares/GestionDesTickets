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
    
    Equipe equipe = EquipeFactory.equipeDTOToEquipe(equipeDTO);
    
    // Gérer les utilisateurs avant de sauvegarder l'équipe
    if (equipe.getUsers() != null && !equipe.getUsers().isEmpty()) {
        for (User user : equipe.getUsers()) {
            if (user.getUsername() != null) {
                User existingUser = userRepository.findById(user.getUsername()).orElse(null);
                
                if (existingUser != null && existingUser.getEquipe() != null 
                    && !existingUser.getEquipe().getIdEquipe().equals(equipeDTO.getIdEquipe())) {
                    // Permettre le changement d'équipe en détachant l'utilisateur de son ancienne équipe
                    log.debug("Changing user {} from team {} to team {}", 
                             existingUser.getUsername(), 
                             existingUser.getEquipe().getIdEquipe(), 
                             equipeDTO.getIdEquipe());
                    
                    existingUser.setEquipe(null);
                    userRepository.save(existingUser);
                }
                
                // Mettre à jour l'équipe de l'utilisateur
                user.setEquipe(equipe);
                userRepository.save(user); // Sauvegarder explicitement l'utilisateur avec sa nouvelle équipe
            }
        }
    }
    
    equipe = equipeRepository.save(equipe);
    return EquipeFactory.equipeToEquipeDTO(equipe);
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
    log.debug("Request to delete Equipe: {}",id);
    equipeRepository.deleteById(id);
  }
}

