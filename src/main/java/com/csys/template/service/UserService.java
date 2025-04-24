package com.csys.template.service;

import com.csys.template.domain.User;
import com.csys.template.dto.UserDTO;
import com.csys.template.factory.UserFactory;
import com.csys.template.repository.PosteRepository;
import com.csys.template.repository.UserRepository;
import com.google.common.base.Preconditions;
import java.lang.String;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Service Implementation for managing User.
 */
@Service
@Transactional
public class UserService {
  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final PosteRepository posteRepository;

  public UserService(UserRepository userRepository, PosteRepository posteRepository) {
    this.userRepository=userRepository;
    this.posteRepository=posteRepository;
  }

  /**
   * Save a userDTO.
   *
   * @param userDTO
   * @return the persisted entity
   */
  public UserDTO save(UserDTO userDTO) {
    log.debug("Request to save User: {}",userDTO);
    User user = UserFactory.userDTOToUser(userDTO);
    user = userRepository.save(user);
    UserDTO resultDTO = UserFactory.userToUserDTO(user);
    return resultDTO;
  }

  /**
   * Update a userDTO.
   *
   * @param userDTO
   * @return the updated entity
   */
  // Mise à jour de la méthode update pour gérer les nouveaux champs
  public UserDTO update(UserDTO userDTO) {
      log.debug("Request to update User: {}", userDTO);
      User inBase = userRepository.findById(userDTO.getUsername()).orElse(null);
      Preconditions.checkArgument(inBase != null, "user.NotFound");
      
      // Vérifier si le poste existe si un ID est fourni
      if (userDTO.getIdPoste() != null) {
          posteRepository.findById(userDTO.getIdPoste())
              .orElseThrow(() -> new IllegalArgumentException("Poste not found with id " + userDTO.getIdPoste()));
      }
      
      User user = UserFactory.userDTOToUser(userDTO);
      user = userRepository.save(user);
      UserDTO resultDTO = UserFactory.userToUserDTO(user);
      return resultDTO;
  }
  
  // Nouvelles méthodes pour rechercher par les nouveaux attributs
//  @Transactional(readOnly = true)
//  public Collection<UserDTO> findByNom(String nom) {
//      return UserFactory.userToUserDTOs(userRepository.findByNomContainingIgnoreCase(nom));
//  }
//  
//  @Transactional(readOnly = true)
//  public Collection<UserDTO> findByActif(Boolean actif) {
//      return UserFactory.userToUserDTOs(userRepository.findByActif(actif));
//  }

  /**
   * Get one userDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public UserDTO findOne(String id) {
    log.debug("Request to get User: {}",id);
    User user= userRepository.findById(id).orElse(null);
    UserDTO dto = UserFactory.userToUserDTO(user);
    return dto;
  }

  /**
   * Get one user by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public User findUser(String id) {
    log.debug("Request to get User: {}",id);
    User user= userRepository.findById(id).orElse(null);
    return user;
  }

  /**
   * Get all the users.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<UserDTO> findAll() {
    log.debug("Request to get All Users");
    Collection<User> result= userRepository.findAll();
    return UserFactory.userToUserDTOs(result);
  }

  /**
   * Delete user by id.
   *
   * @param id the id of the entity
   */
  public void delete(String id) {
    log.debug("Request to delete User: {}",id);
    userRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public UserDTO findUser() {
    User user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    return UserFactory.userToUserDTO(user);
  }
}