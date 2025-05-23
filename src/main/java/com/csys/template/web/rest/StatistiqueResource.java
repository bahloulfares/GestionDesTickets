package com.csys.template.web.rest;

import com.csys.template.dto.DemandeDTO;
import com.csys.template.dto.StatistiqueDTO;
import com.csys.template.service.StatistiqueService;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller pour les statistiques.
 */
@RestController
@RequestMapping("/api")
public class StatistiqueResource {
    
    private final Logger log = LoggerFactory.getLogger(StatistiqueResource.class);
    
    private final StatistiqueService statistiqueService;
    
    public StatistiqueResource(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }
    
    /**
     * GET /demandes/filter : récupère les demandes selon les filtres spécifiés
     *
     * @param dateCreationDu Date de début pour la création
     * @param dateCreationAu Date de fin pour la création
     * @param codeClient Code du client (optionnel)
     * @param codeModule Code du module (optionnel)
     * @return Liste des demandes correspondant aux critères
     */
    @GetMapping("/demandes/filter")
    public List<DemandeDTO> getDemandeByFilters(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateCreationDu, 
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateCreationAu, 
            @RequestParam(required = false) Integer codeClient, 
            @RequestParam(required = false) Integer codeModule) {
        log.debug("REST request to get filtered demands");
        return statistiqueService.findListDemande(codeModule, codeClient, dateCreationDu, dateCreationAu);
    }

    /**
     * GET /demandes/count : récupère les statistiques des demandes par type pour une période donnée
     *
     * @param dateCreationDu Date de début pour la création
     * @param dateCreationAu Date de fin pour la création
     * @return Liste des statistiques par type
     */
    @GetMapping("/demandes/count")
    public List<StatistiqueDTO> getDemandeByFilters(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateCreationDu, 
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateCreationAu) {
        log.debug("REST request to get demand statistics by type");
        return statistiqueService.calculDemandeByType(dateCreationDu, dateCreationAu);
    }
}