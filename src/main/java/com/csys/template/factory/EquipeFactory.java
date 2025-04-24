package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.User;
import com.csys.template.dto.EquipeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipeFactory {
  public static EquipeDTO equipeToEquipeDTO(Equipe equipe) {
    EquipeDTO equipeDTO=new EquipeDTO();
    equipeDTO.setIdEquipe(equipe.getIdEquipe());
    equipeDTO.setDesignation(equipe.getDesignation());
    equipeDTO.setActif(equipe.getActif());
    
    // Convert User entities to UserDTOs
    if (equipe.getUsers() != null && !equipe.getUsers().isEmpty()) {
      equipeDTO.setUsers(new ArrayList<>(UserFactory.userToUserDTOs(equipe.getUsers())));
    }
    
    return equipeDTO;
  }

  public static Equipe equipeDTOToEquipe(EquipeDTO equipeDTO) {
    Equipe equipe=new Equipe();
    equipe.setIdEquipe(equipeDTO.getIdEquipe());
    equipe.setDesignation(equipeDTO.getDesignation());
    equipe.setActif(equipeDTO.getActif());
    
    // Traiter les utilisateurs si présents dans le DTO
    if (equipeDTO.getUsers() != null && !equipeDTO.getUsers().isEmpty()) {
      List<User> users = new ArrayList<>();
      equipeDTO.getUsers().forEach(userDTO -> {
        User user = UserFactory.userDTOToUser(userDTO);
        user.setEquipe(equipe); // Définir l'équipe pour chaque utilisateur
        users.add(user);
      });
      equipe.setUsers(users);
    }
    return equipe;
  }

  public static Collection<EquipeDTO> equipeToEquipeDTOs(Collection<Equipe> equipes) {
    List<EquipeDTO> equipesDTO=new ArrayList<>();
    equipes.forEach(x -> {
      equipesDTO.add(equipeToEquipeDTO(x));
    } );
    return equipesDTO;
  }
}

