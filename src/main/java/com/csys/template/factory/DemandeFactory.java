package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.domain.Demande;
import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.domain.User;
import com.csys.template.dto.ClientDTO;
import com.csys.template.dto.DemandeDTO;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.dto.ModuleDTO;
import com.csys.template.dto.UserDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;


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

        // Conversion des entités en DTOs
        if (demande.getClient() != null) {
            demandeDTO.setClient(ClientFactory.clientToClientDTO(demande.getClient()));
        }
        
        if (demande.getEquipe() != null) {
            demandeDTO.setEquipe(EquipeFactory.equipeToEquipeDTO(demande.getEquipe()));
        }
        
        if (demande.getModule() != null) {
            demandeDTO.setModule(ModuleFactory.moduleToModuleDTO(demande.getModule()));
        }
        
        if (demande.getCreateur() != null) {
            demandeDTO.setCreateur(UserFactory.userToUserDTO(demande.getCreateur()));
        }
        
        if (demande.getCollaborateur() != null) {
            demandeDTO.setCollaborateur(UserFactory.userToUserDTO(demande.getCollaborateur()));
        }

        return demandeDTO;
    }

    public static Demande demandeDTOToDemande(DemandeDTO demandeDTO) {
        Demande demande = new Demande();
        demande.setIdDemande(demandeDTO.getIdDemande());
        // Préserver la date de création lors des mises à jour
        if (demandeDTO.getDateCreation() != null) {
            demande.setDateCreation(demandeDTO.getDateCreation());
        } else if (demande.getIdDemande() == null) {
            // Pour les nouvelles demandes, la date est définie dans @PrePersist
            // Ne rien faire ici, la méthode prePersist s'en chargera
        } else {
            // Pour les mises à jour sans date fournie, définir la date actuelle
            demande.setDateCreation(LocalDate.now());
        }
        
        demande.setDescription(demandeDTO.getDescription());
        demande.setDateAffectationEquipe(demandeDTO.getDateAffectationEquipe());
        demande.setDateAffectationCollaborateur(demandeDTO.getDateAffectationCollaborateur());
        demande.setEtat(demandeDTO.getEtat());
        demande.setPriorite(demandeDTO.getPriorite());
        demande.setDateEcheance(demandeDTO.getDateEcheance());
        demande.setCommentaire(demandeDTO.getCommentaire());

        // Conversion des DTOs en entités
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
                demande.setDateAffectationEquipe(LocalDate.now());
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

        // Préserver le créateur original lors des mises à jour
        if (demandeDTO.getCreateur() != null) {
            User createur = new User();
            createur.setUsername(demandeDTO.getCreateur().getUsername());
            demande.setCreateur(createur);
        } else if (demande.getIdDemande() == null) {
            // Pour les nouvelles demandes, définir le créateur comme l'utilisateur actuel
            User createur = new User();
            createur.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            demande.setCreateur(createur);
        }
        
        // Gestion du collaborateur - peut être null lors de la création initiale
        if (demandeDTO.getCollaborateur() != null) {
            User collaborateur = new User();
            collaborateur.setUsername(demandeDTO.getCollaborateur().getUsername());
            demande.setCollaborateur(collaborateur);
            // Si un collaborateur est assigné, mettre à jour la date d'affectation si elle n'existe pas déjà
            if (demande.getDateAffectationCollaborateur() == null) {
                demande.setDateAffectationCollaborateur(LocalDate.now());
            }
        } else {
            // Assurer que le collaborateur est explicitement null pour la première étape
            demande.setCollaborateur(null);
        }

        return demande;
    }

    public static List<DemandeDTO> demandeToDemandeDTOs(Collection<Demande> demandes) {
        List<DemandeDTO> demandesDTO = new ArrayList<>();
        demandes.forEach(x -> {
            demandesDTO.add(demandeToDemandeDTO(x));
        });
        return demandesDTO;
    }
}
