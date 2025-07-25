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
//import com.csys.template.service.PasswordService;
import com.csys.template.web.rest.errors.IllegalBusinessLogiqueException;
import com.csys.template.web.rest.errors.MyResourceNotFoundException;
import java.util.Optional;

/**
 * Service Implementation for managing User.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PosteRepository posteRepository;
    //private PasswordService passwordService;

    public UserService(UserRepository userRepository, PosteRepository posteRepository) {
        this.userRepository = userRepository;
        this.posteRepository = posteRepository;
        //this.passwordService = passwordService;
    }

    /**
     * Save a userDTO.
     *
     * @param userDTO
     * @return the persisted entity
     */
    // @Autowired
    // private PasswordService passwordService;
    // Dans la méthode save ou create
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User: {}", userDTO);

        // Crypter le mot de passe avant de sauvegarder
        // if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
        //     userDTO.setPassword(passwordService.encryptPassword(userDTO.getPassword()));
        // }
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
        log.debug("Request to get User: {}", id);
        User user = userRepository.findById(id).orElse(null);
        UserDTO dto = UserFactory.userToUserDTO(user);
        return dto;
    }

    // utilisé pour l'authentication 
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findOneByUsername(username.toLowerCase());
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
        Collection<User> result = userRepository.findAll();
        return UserFactory.userToUserDTOs(result);
    }

    /**
     * Delete user by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete User: {}", id);

        // Get the user to check if it's assigned to a team
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Check if the user is assigned to a team
            if (user.getEquipe() != null) {
                throw new IllegalBusinessLogiqueException("user.assigned.to.team",
                        new Throwable("User " + id + " is assigned to team " + user.getEquipe().getDesignation()
                                + ". Please remove the user from the team before deletion."));
            }

            userRepository.deleteById(id);
        } else {
            // User not found, throw exception
            throw new MyResourceNotFoundException("user.NotFound");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO findUser() {
        User user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        return UserFactory.userToUserDTO(user);
    }
}
