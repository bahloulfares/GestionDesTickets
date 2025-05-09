package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.domain.Demande;
import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.domain.User;
import com.csys.template.dto.DemandeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DemandeFactory {
  public static DemandeDTO demandeToDemandeDTO(Demande demande) {
    if (demande == null) {
      return null;
    }
    
    DemandeDTO demandeDTO = new DemandeDTO();
    demandeDTO.setIdDemande(demande.getIdDemande());
    demandeDTO.setDateCreation(demande.getDateCreation());
    demandeDTO.setDescription(demande.getDescription());
    demandeDTO.setDateAffectationEquipe(demande.getDateAffectationEquipe());
    demandeDTO.setDateAffectationCollaborateur(demande.getDateAffectationCollaborateur());
    demandeDTO.setEtat(demande.getEtat());
    demandeDTO.setPriorite(demande.getPriorite());
    demandeDTO.setDateEcheance(demande.getDateEcheance());
    demandeDTO.setCommentaire(demande.getCommentaire());
    
    // Add null checks for related entities
    demandeDTO.setClient(demande.getClient());
    demandeDTO.setEquipe(demande.getEquipe());
    demandeDTO.setModule(demande.getModule());
    demandeDTO.setCreateur(demande.getCreateur());
    demandeDTO.setCollaborateur(demande.getCollaborateur());
    
    return demandeDTO;
  }

  public static Demande demandeDTOToDemande(DemandeDTO demandeDTO) {
    Demande demande = new Demande();
    demande.setIdDemande(demandeDTO.getIdDemande());
    demande.setDateCreation(demandeDTO.getDateCreation());
    demande.setDescription(demandeDTO.getDescription());
    demande.setDateAffectationEquipe(demandeDTO.getDateAffectationEquipe());
    demande.setDateAffectationCollaborateur(demandeDTO.getDateAffectationCollaborateur());
    demande.setEtat(demandeDTO.getEtat());
    demande.setPriorite(demandeDTO.getPriorite());
    demande.setDateEcheance(demandeDTO.getDateEcheance());
    demande.setCommentaire(demandeDTO.getCommentaire());
    
    // Gestion correcte des relations avec les entités
    if (demandeDTO.getClient() != null) {
      Client client = new Client();
      client.setIdClient(demandeDTO.getClient().getIdClient());
      demande.setClient(client);
    }
    
    // Gestion de l'équipe - peut être null lors de la création initiale
    if (demandeDTO.getEquipe() != null) {
      Equipe equipe = new Equipe();
      equipe.setIdEquipe(demandeDTO.getEquipe().getIdEquipe());
      demande.setEquipe(equipe);
      // Si une équipe est assignée, mettre à jour la date d'affectation si elle n'existe pas déjà
      if (demande.getDateAffectationEquipe() == null) {
        demande.setDateAffectationEquipe(new java.util.Date());
      }
    } else {
      // Assurer que l'équipe est explicitement null pour la première étape
      demande.setEquipe(null);
    }
    
    if (demandeDTO.getModule() != null) {
      Module module = new Module();
      module.setIdModule(demandeDTO.getModule().getIdModule());
      demande.setModule(module);
    }
    
//    if (demandeDTO.getCreateur() != null) {
//      User createur = new User();
//      createur.setUsername(demandeDTO.getCreateur().getUsername());
//      demande.setCreateur(createur);
//    }
        demande.setCreateur(demandeDTO.getCreateur());
    // Gestion du collaborateur - peut être null lors de la création initiale
    if (demandeDTO.getCollaborateur() != null) {
      User collaborateur = new User();
      collaborateur.setUsername(demandeDTO.getCollaborateur().getUsername());
      demande.setCollaborateur(collaborateur);
      // Si un collaborateur est assigné, mettre à jour la date d'affectation si elle n'existe pas déjà
      if (demande.getDateAffectationCollaborateur() == null) {
        demande.setDateAffectationCollaborateur(new java.util.Date());
      }
    } else {
      // Assurer que le collaborateur est explicitement null pour la première étape
      demande.setCollaborateur(null);
    }
    
    return demande;
  }

  public static Collection<DemandeDTO> demandeToDemandeDTOs(Collection<Demande> demandes) {
    List<DemandeDTO> demandesDTO = new ArrayList<>();
    demandes.forEach(x -> {
      demandesDTO.add(demandeToDemandeDTO(x));
    });
    return demandesDTO;
  }
}

