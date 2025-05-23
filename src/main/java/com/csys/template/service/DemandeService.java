package com.csys.template.service;

import com.csys.template.domain.Demande;
import com.csys.template.dto.DemandeDTO;
import com.csys.template.factory.DemandeFactory;
import com.csys.template.repository.DemandeRepository;
import com.csys.template.web.rest.errors.MyResourceNotFoundException;
import com.csys.template.web.rest.errors.IllegalBusinessLogiqueException;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csys.template.domain.Equipe;
import com.csys.template.domain.User;
import com.csys.template.domain.enums.EtatDemande;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Service Implementation for managing Demande.
 */
@Service
@Transactional
public class DemandeService {

    private final Logger log = LoggerFactory.getLogger(DemandeService.class);

    private final DemandeRepository demandeRepository;
    private final EquipeRepository equipeRepository;
    private final UserRepository userRepository;

    public DemandeService(DemandeRepository demandeRepository, EquipeRepository equipeRepository, UserRepository userRepository) {
        this.demandeRepository = demandeRepository;
        this.equipeRepository = equipeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a demandeDTO.
     *
     * @param demandeDTO
     * @return the persisted entity
     */
    public DemandeDTO save(DemandeDTO demandeDTO) {
        log.debug("Request to save Demande: {}", demandeDTO);

        // Vérification des relations essentielles
        if (demandeDTO.getClient() == null) {
            throw new IllegalBusinessLogiqueException("client.NotNull");
        }
        if (demandeDTO.getModule() == null) {
            throw new IllegalBusinessLogiqueException("module.NotNull");
        }
//        if (demandeDTO.getCreateur() == null) {
//            throw new IllegalBusinessLogiqueException("createur.NotNull");
//        }
        log.debug("test user {}", SecurityContextHolder.getContext().getAuthentication().getName());
        Demande demande = DemandeFactory.demandeDTOToDemande(demandeDTO);
        demande = demandeRepository.save(demande);
        DemandeDTO resultDTO = DemandeFactory.demandeToDemandeDTO(demande);
        return resultDTO;
    }

    /**
     * Update a demandeDTO.
     *
     * @param demandeDTO
     * @return the updated entity
     */
    public DemandeDTO update(DemandeDTO demandeDTO) {
        log.debug("Request to update Demande: {}", demandeDTO);
        Demande inBase = demandeRepository.findById(demandeDTO.getIdDemande()).orElse(null);
        // Instead of using Preconditions.checkArgument
        if (inBase == null) {
            throw new MyResourceNotFoundException("demande.NotFound");
        }
        // Préserver le créateur original
        String originalUsername = inBase.getUsername();
        LocalDate originalDateCreation = inBase.getDateCreation();
        LocalDate originalDateAssigneEquipe = inBase.getDateAffectationEquipe();
        LocalDate originalDateAssigneCollab = inBase.getDateAffectationCollaborateur();
        Demande demande = DemandeFactory.demandeDTOToDemande(demandeDTO);
        // Restaurer le créateur original
        demande.setUsername(originalUsername);
        demande.setDateCreation(originalDateCreation);
        demande.setDateAffectationEquipe(originalDateAssigneEquipe);
        demande.setDateAffectationCollaborateur(originalDateAssigneCollab);
        demande = demandeRepository.save(demande);
        DemandeDTO resultDTO = DemandeFactory.demandeToDemandeDTO(demande);
        return resultDTO;
    }

    /**
     * Get one demandeDTO by id.
     *
     * @param id the id of the entity
     * @return the entity DTO
     */
    @Transactional(
            readOnly = true
    )
    public DemandeDTO findOne(Integer id) {
        log.debug("Request to get Demande: {}", id);
        Demande demande = demandeRepository.findById(id).orElse(null);
        DemandeDTO dto = DemandeFactory.demandeToDemandeDTO(demande);
        return dto;
    }

    /**
     * Get one demande by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(
            readOnly = true
    )
    public Demande findDemande(Integer id) {
        log.debug("Request to get Demande: {}", id);
        Demande demande = demandeRepository.findById(id).orElse(null);
        return demande;
    }

    /**
     * Get all the demandes.
     *
     * @return the the list of entities
     */
    @Transactional(
            readOnly = true
    )
    public Collection<DemandeDTO> findAll() {
        log.debug("Request to get All Demandes");
        Collection<Demande> result = demandeRepository.findAll();
        return DemandeFactory.demandeToDemandeDTOs(result);
    }

    /**
     * Delete demande by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete Demande: {}", id);
        demandeRepository.deleteById(id);
    }

    /**
     * Assigne une équipe à une demande existante
     *
     * @param idDemande l'identifiant de la demande
     * @param idEquipe l'identifiant de l'équipe à assigner
     * @return la demande mise à jour
     */
    @Transactional
    public DemandeDTO assignerEquipe(Integer idDemande, Integer idEquipe) {
        log.debug("Assignation de l'équipe {} à la demande {}", idEquipe, idDemande);

        // Récupération de la demande
        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new MyResourceNotFoundException("demande.NotFound"));

        // Récupération de l'équipe
        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new MyResourceNotFoundException("equipe.NotFound"));

        // Vérification de l'état de la demande
        if (demande.getEtat() == EtatDemande.DEMANDE_EN_COURS_DE_VALIDATION || demande.getEtat() == EtatDemande.DEMANDE_CREEE || demande.getEtat() == EtatDemande.DEMANDE_REJETEE || demande.getEtat() == EtatDemande.TERMINEE || demande.getEtat() == EtatDemande.CLOTUREE) {
            throw new IllegalBusinessLogiqueException("demande.etat.invalid.pour.affectation");
        }

        // // Vérification que la demande est dans un état valide pour l'affectation
        // if (demande.getEtat() != EtatDemande.DEMANDE_VALIDEE || demande.getEtat() != EtatDemande.ASSIGNEE || demande.getEtat() != EtatDemande.EN_ATTENTE_INFORMATIONS || demande.getEtat() != EtatDemande.EN_COURS_DE_TRAITEMENT) {
        //     throw new IllegalBusinessLogiqueException("demande.etat.non.valide.pour.affectation");
        // }
        // Assignation de l'équipe à la demande
        demande.setEquipe(equipe);
        demande.setCollaborateur(null);
        // Mise à jour de la date d'affectation
        demande.setDateAffectationEquipe(LocalDate.now());

        // Mise à jour de l'état de la demande
        demande.setEtat(EtatDemande.ASSIGNEE);

        // Sauvegarde de la demande
        demande = demandeRepository.save(demande);

        // Conversion en DTO (dans la même transaction)
        return DemandeFactory.demandeToDemandeDTO(demande);
    }

    /**
     * Assigne un collaborateur à une demande existante
     *
     * @param idDemande l'identifiant de la demande
     * @param username l'identifiant du collaborateur à assigner
     * @return la demande mise à jour
     */
    @Transactional
    public DemandeDTO assignerCollaborateur(Integer idDemande, String username) {
        log.debug("Assignation du collaborateur {} à la demande {}", username, idDemande);

        // Récupération de la demande
        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new MyResourceNotFoundException("demande.NotFound"));

        // Récupération du collaborateur
        User collaborateur = userRepository.findById(username)
                .orElseThrow(() -> new MyResourceNotFoundException("user.NotFound"));

        // Vérification de l'état de la demande
        if (demande.getEtat() == EtatDemande.DEMANDE_EN_COURS_DE_VALIDATION || demande.getEtat() == EtatDemande.DEMANDE_CREEE || demande.getEtat() == EtatDemande.DEMANDE_REJETEE || demande.getEtat() == EtatDemande.TERMINEE || demande.getEtat() == EtatDemande.CLOTUREE) {
            throw new IllegalBusinessLogiqueException("demande.etat.invalid.pour.affectation");
        }

        // // Vérification que la demande est dans un état valide pour l'affectation
        // if (demande.getEtat() != EtatDemande.DEMANDE_VALIDEE || demande.getEtat() != EtatDemande.ASSIGNEE || demande.getEtat() != EtatDemande.EN_ATTENTE_INFORMATIONS || demande.getEtat() != EtatDemande.EN_COURS_DE_TRAITEMENT) {
        //     throw new IllegalBusinessLogiqueException("demande.etat.non.valide.pour.affectation");
        // }
        // Assignation du collaborateur à la demande
        demande.setCollaborateur(collaborateur);

        // Mise à jour de la date d'affectation
        demande.setDateAffectationCollaborateur(LocalDate.now());

        // Mise à jour de l'état de la demande
        demande.setEtat(EtatDemande.ASSIGNEE);

        // Sauvegarde de la demande
        demande = demandeRepository.save(demande);

        // Conversion en DTO (dans la même transaction)
        return DemandeFactory.demandeToDemandeDTO(demande);
    }

    /**
     * Méthode combinée pour assigner à la fois une équipe et un collaborateur
     * Utile lorsque le collaborateur fait partie de l'équipe assignée
     *
     * @param idDemande l'identifiant de la demande
     * @param idEquipe l'identifiant de l'équipe à assigner
     * @param username l'identifiant du collaborateur à assigner
     * @return la demande mise à jour
     */
    @Transactional
    public DemandeDTO assignerEquipeEtCollaborateur(Integer idDemande, Integer idEquipe, String username) {
        log.debug("Assignation de l'équipe {} et du collaborateur {} à la demande {}", idEquipe, username, idDemande);

        // Récupération de la demande
        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new MyResourceNotFoundException("demande.NotFound"));

        // Vérification de l'état de la demande
        if (demande.getEtat() == EtatDemande.DEMANDE_EN_COURS_DE_VALIDATION || demande.getEtat() == EtatDemande.DEMANDE_CREEE || demande.getEtat() == EtatDemande.DEMANDE_REJETEE || demande.getEtat() == EtatDemande.TERMINEE || demande.getEtat() == EtatDemande.CLOTUREE) {
            throw new IllegalBusinessLogiqueException("demande.etat.invalid.pour.affectation");
        }

        // // Vérification que la demande est dans un état valide pour l'affectation
        // if (demande.getEtat() != EtatDemande.DEMANDE_VALIDEE || demande.getEtat() != EtatDemande.ASSIGNEE || demande.getEtat() != EtatDemande.EN_ATTENTE_INFORMATIONS || demande.getEtat() != EtatDemande.EN_COURS_DE_TRAITEMENT) {
        //     throw new IllegalBusinessLogiqueException("demande.etat.non.valide.pour.affectation");
        // }
        // Récupération de l'équipe
        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new MyResourceNotFoundException("equipe.NotFound"));

        // Récupération du collaborateur
        User collaborateur = userRepository.findById(username)
                .orElseThrow(() -> new MyResourceNotFoundException("user.NotFound"));

        // Vérification que le collaborateur appartient à l'équipe
        boolean collaborateurDansEquipe = collaborateur.getEquipe() != null
                && collaborateur.getEquipe().getIdEquipe().equals(idEquipe);

        if (!collaborateurDansEquipe) {
            log.warn("Le collaborateur {} n'appartient pas à l'équipe {}", username, idEquipe);
            // Remplacer le simple avertissement par une exception métier
            throw new IllegalBusinessLogiqueException("collaborateur.not.in.team");
        }

        // Assignation de l'équipe et du collaborateur
        demande.setEquipe(equipe);
        demande.setCollaborateur(collaborateur);

        // Mise à jour des dates d'affectation
        LocalDate maintenant = LocalDate.now();
        demande.setDateAffectationEquipe(maintenant);
        demande.setDateAffectationCollaborateur(maintenant);

        // Mise à jour de l'état de la demande
        demande.setEtat(EtatDemande.ASSIGNEE);

        // Sauvegarde de la demande
        demande = demandeRepository.save(demande);

        // Conversion en DTO (dans la même transaction)
        return DemandeFactory.demandeToDemandeDTO(demande);
    }

    /**
     * Désaffecte l'équipe et le collaborateur d'une demande
     *
     * @param idDemande l'identifiant de la demande
     * @return la demande mise à jour
     */
    @Transactional
    public DemandeDTO desaffecterDemande(Integer idDemande) {
        log.debug("Désaffectation de la demande {}", idDemande);

        // Récupération de la demande
        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new MyResourceNotFoundException("demande.NotFound"));

        // Vérification que la demande est bien dans un état permettant la désaffectation
        if (demande.getEtat() != EtatDemande.ASSIGNEE && demande.getEtat() != EtatDemande.EN_COURS_DE_TRAITEMENT) {
            throw new IllegalBusinessLogiqueException("demande.cannot.unassign");
        }

        // Désaffectation
        demande.setEquipe(null);
        demande.setCollaborateur(null);
        demande.setDateAffectationEquipe(null);
        demande.setDateAffectationCollaborateur(null);

        // Mise à jour de l'état
        demande.setEtat(EtatDemande.DEMANDE_VALIDEE);

        // Sauvegarde de la demande
        demande = demandeRepository.save(demande);

        // Conversion en DTO
        return DemandeFactory.demandeToDemandeDTO(demande);
    }


}
