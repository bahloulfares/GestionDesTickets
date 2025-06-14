package com.csys.template.web.rest;

import com.csys.template.dto.DemandeDTO;
import com.csys.template.service.DemandeService;
import com.csys.template.util.RestPreconditions;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * REST controller for managing Demande.
 */
@RestController
@RequestMapping("/api")
public class DemandeResource {

    private static final String ENTITY_NAME = "demande";

    private final DemandeService demandeService;

    private final Logger log = LoggerFactory.getLogger(DemandeService.class);

    public DemandeResource(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    /**
     * POST /demandes : Create a new demande.
     *
     * @param demandeDTO
     * @param bindingResult
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new demande, or with status 400 (Bad Request) if the demande has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws org.springframework.web.bind.MethodArgumentNotValidException
     */
    @PostMapping("/demandes")
    public ResponseEntity<DemandeDTO> createDemande(@Valid @RequestBody DemandeDTO demandeDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
        log.debug("REST request to save Demande : {}", demandeDTO);
        if (demandeDTO.getIdDemande() != null) {
            bindingResult.addError(new FieldError("DemandeDTO", "idDemande", "POST method does not accepte " + ENTITY_NAME + " with code"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        // Add additional validation for required relationships
        if (demandeDTO.getClient() == null) {
            bindingResult.addError(new FieldError("DemandeDTO", "client", "Client is required"));
        }
        if (demandeDTO.getModule() == null) {
            bindingResult.addError(new FieldError("DemandeDTO", "module", "Module is required"));
        }
        // if (demandeDTO.getCreateur() == null) {
        //   bindingResult.addError(new FieldError("DemandeDTO", "createur", "Createur is required"));
        // }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        DemandeDTO result = demandeService.save(demandeDTO);
        return ResponseEntity.created(new URI("/api/demandes/" + result.getIdDemande())).body(result);
    }

    /**
     * PUT /demandes : Updates an existing demande.
     *
     * @param id
     * @param demandeDTO the demande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * demande, or with status 400 (Bad Request) if the demande is not valid, or
     * with status 500 (Internal Server Error) if the demande couldn't be
     * updated
     * @throws org.springframework.web.bind.MethodArgumentNotValidException
     */
    @PutMapping("/demandes/{id}")
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Integer id, @Valid @RequestBody DemandeDTO demandeDTO) throws MethodArgumentNotValidException {
        log.debug("Request to update Demande: {}", id);
        demandeDTO.setIdDemande(id);
        DemandeDTO result = demandeService.update(demandeDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET /demandes/{id} : get the "id" demande.
     *
     * @param id the id of the demande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body of demande,
     * or with status 404 (Not Found)
     */
    @GetMapping("/demandes/{id}")
    public ResponseEntity<DemandeDTO> getDemande(@PathVariable Integer id) {
        log.debug("Request to get Demande: {}", id);
        DemandeDTO dto = demandeService.findOne(id);
        RestPreconditions.checkFound(dto, "demande.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    /**
     * GET /demandes : get all the demandes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of demandes
     * in body
     */
    @GetMapping("/demandes")
    public Collection<DemandeDTO> getAllDemandes() {
        log.debug("Request to get all  Demandes : {}");
        return demandeService.findAll();
    }

    /**
     * DELETE /demandes/{id} : delete the "id" demande.
     *
     * @param id the id of the demande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demandes/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDemande(@PathVariable Integer id) {
        log.debug("Request to delete Demande: {}", id);
        demandeService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * PUT /demandes/{id}/equipe/{idEquipe} : Assigne une équipe à une demande
     *
     * @param id l'identifiant de la demande
     * @param idEquipe l'identifiant de l'équipe à assigner
     * @return la ResponseEntity avec statut 200 (OK) et la demande mise à jour
     */
    @PutMapping("/demandes/{id}/equipe/{idEquipe}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DemandeDTO> assignerEquipe(@PathVariable Integer id, @PathVariable Integer idEquipe) {
        log.debug("REST request to assign team {} to demand {}", idEquipe, id);
        DemandeDTO result = demandeService.assignerEquipe(id, idEquipe);
        return ResponseEntity.ok().body(result);
    }

    /**
     * PUT /demandes/{id}/collaborateur/{username} : Assigne un collaborateur à
     * une demande
     *
     * @param id l'identifiant de la demande
     * @param username l'identifiant du collaborateur à assigner
     * @return la ResponseEntity avec statut 200 (OK) et la demande mise à jour
     */
//    @PutMapping("/demandes/{id}/collaborateur/{username}")
//    public ResponseEntity<DemandeDTO> assignerCollaborateur(@PathVariable Integer id, @PathVariable String username) {
//        log.debug("REST request to assign collaborator {} to demand {}", username, id);
//        DemandeDTO result = demandeService.assignerCollaborateur(id, username);
//        return ResponseEntity.ok().body(result);
//    }

    /**
     * PUT /demandes/{id}/equipe/{idEquipe}/collaborateur/{username} : Assigne
     * une équipe et un collaborateur à une demande
     *
     * @param id l'identifiant de la demande
     * @param idEquipe l'identifiant de l'équipe à assigner
     * @param username l'identifiant du collaborateur à assigner
     * @return la ResponseEntity avec statut 200 (OK) et la demande mise à jour
     */
    @PutMapping("/demandes/{id}/equipe/{idEquipe}/collaborateur/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DemandeDTO> assignerEquipeEtCollaborateur(
            @PathVariable Integer id,
            @PathVariable Integer idEquipe,
            @PathVariable String username) {
        log.debug("REST request to assign team {} and collaborator {} to demand {}", idEquipe, username, id);
        DemandeDTO result = demandeService.assignerEquipeEtCollaborateur(id, idEquipe, username);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/demandes/{id}/affectation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DemandeDTO> desaffecterDemande(@PathVariable("id") Integer id) {
        log.debug("REST request to unassign demand {}", id);
        DemandeDTO result = demandeService.desaffecterDemande(id);
        return ResponseEntity.ok().body(result);
    }

    //endpoint d'importation des demandes depuis un fichier json
    @PostMapping("/demandes/import")
    public ResponseEntity<DemandeDTO> importDemande(@RequestParam("file") MultipartFile file)
            throws URISyntaxException, IOException {
        log.debug("REST request to import Demande from file");

        DemandeDTO result = demandeService.importFromFile(file);

        return ResponseEntity
                .created(new URI("/api/demandes/" + result.getIdDemande()))
                .body(result);
    }

}
