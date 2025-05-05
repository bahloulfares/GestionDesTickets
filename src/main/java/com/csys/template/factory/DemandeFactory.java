package com.csys.template.factory;

import com.csys.template.domain.Demande;
import com.csys.template.dto.DemandeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DemandeFactory {
  public static DemandeDTO demandeToDemandeDTO(Demande demande) {
    DemandeDTO demandeDTO=new DemandeDTO();
    demandeDTO.setIdDemande(demande.getIdDemande());
    demandeDTO.setDateCreation(demande.getDateCreation());
    demandeDTO.setDescription(demande.getDescription());
    demandeDTO.setDateAffectationEquipe(demande.getDateAffectationEquipe());
    demandeDTO.setDateAffectationCollaborateur(demande.getDateAffectationCollaborateur());
    demandeDTO.setEtat(demande.getEtat());
    demandeDTO.setPriorite(demande.getPriorite());
    demandeDTO.setDateEcheance(demande.getDateEcheance());
    demandeDTO.setCommentaire(demande.getCommentaire());
    demandeDTO.setClient(demande.getClient());
    demandeDTO.setEquipe(demande.getEquipe());
    demandeDTO.setModule(demande.getModule());
    demandeDTO.setCreateur(demande.getCreateur());
    demandeDTO.setCollaborateur(demande.getCollaborateur());
    return demandeDTO;
  }

  public static Demande demandeDTOToDemande(DemandeDTO demandeDTO) {
    Demande demande=new Demande();
    demande.setIdDemande(demandeDTO.getIdDemande());
    demande.setDateCreation(demandeDTO.getDateCreation());
    demande.setDescription(demandeDTO.getDescription());
    demande.setDateAffectationEquipe(demandeDTO.getDateAffectationEquipe());
    demande.setDateAffectationCollaborateur(demandeDTO.getDateAffectationCollaborateur());
    demande.setEtat(demandeDTO.getEtat());
    demande.setPriorite(demandeDTO.getPriorite());
    demande.setDateEcheance(demandeDTO.getDateEcheance());
    demande.setCommentaire(demandeDTO.getCommentaire());
    demande.setClient(demandeDTO.getClient());
    demande.setEquipe(demandeDTO.getEquipe());
    demande.setModule(demandeDTO.getModule());
    demande.setCreateur(demandeDTO.getCreateur());
    demande.setCollaborateur(demandeDTO.getCollaborateur());
    return demande;
  }

  public static Collection<DemandeDTO> demandeToDemandeDTOs(Collection<Demande> demandes) {
    List<DemandeDTO> demandesDTO=new ArrayList<>();
    demandes.forEach(x -> {
      demandesDTO.add(demandeToDemandeDTO(x));
    } );
    return demandesDTO;
  }
}

