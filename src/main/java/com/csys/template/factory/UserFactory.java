package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.domain.User;
import com.csys.template.domain.Equipe;
import com.csys.template.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserFactory {
    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        // Mappage des champs existants
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setDescription(user.getDescription());
        
        // Mappage des nouveaux champs
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setRole(user.getRole());
        userDTO.setActif(user.getActif());
        
        // Mappage de la relation avec Poste
        if (user.getPoste() != null) {
            userDTO.setIdPoste(user.getPoste().getIdPoste());
        }
        
        // Mappage de la relation avec Equipe
        if (user.getEquipe() != null) {
            userDTO.setIdEquipe(user.getEquipe().getIdEquipe());
            //userDTO.setEquipeDesignation(user.getEquipe().getDesignation());
        }
        
        return userDTO;
    }

    public static User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        // Mappage des champs existants
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setDescription(userDTO.getDescription());
        
        // Mappage des nouveaux champs
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setRole(userDTO.getRole());
        user.setActif(userDTO.getActif());
        
        // Mappage de la relation avec Poste
        if (userDTO.getIdPoste() != null) {
            Poste poste = new Poste();
            poste.setIdPoste(userDTO.getIdPoste());
            user.setPoste(poste);
        }
        
        // Mappage de la relation avec Equipe
        if (userDTO.getIdEquipe() != null) {
            Equipe equipe = new Equipe();
            equipe.setIdEquipe(userDTO.getIdEquipe());
            user.setEquipe(equipe);
        }
        
        return user;
    }

  public static Collection<UserDTO> userToUserDTOs(Collection<User> users) {
    List<UserDTO> usersDTO=new ArrayList<>();
    users.forEach(x -> {
      usersDTO.add(userToUserDTO(x));
    } );
    return usersDTO;
  }
}

