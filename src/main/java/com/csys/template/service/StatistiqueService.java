package com.csys.template.service;

import com.csys.template.domain.Demande;
import com.csys.template.domain.QDemande;
import com.csys.template.dto.DemandeDTO;
import com.csys.template.dto.StatistiqueDTO;
import com.csys.template.factory.DemandeFactory;
import com.csys.template.repository.DemandeRepository;
import com.csys.template.util.WhereClauseBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatistiqueService {
    
    private final Logger log = LoggerFactory.getLogger(StatistiqueService.class);
    
    private final DemandeRepository demandeRepository;
    
    public StatistiqueService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }
    
    /**
     * Recherche des demandes selon les filtres spécifiés
     *
     * @param codeModule Code du module (optionnel)
     * @param codeClient Code du client (optionnel)
     * @param dateCreationDu Date de début pour la création
     * @param dateCreationAu Date de fin pour la création
     * @return Liste des demandes correspondant aux critères
     */
    @Transactional(readOnly = true)
    public List<Demande> findListDemandeByFilter(Integer codeModule, Integer codeClient, LocalDate dateCreationDu, LocalDate dateCreationAu) {
        log.debug("Recherche de demandes avec filtres - Module: {}, Client: {}, Période: {} à {}", 
                codeModule, codeClient, dateCreationDu, dateCreationAu);
        
        QDemande qDemande = QDemande.demande;
        WhereClauseBuilder builder = new WhereClauseBuilder()
                .optionalAnd(codeModule, () -> qDemande.module().idModule.eq(codeModule))
                .optionalAnd(codeClient, () -> qDemande.client().idClient.eq(codeClient))
                .optionalAnd(dateCreationDu, () -> qDemande.dateCreation.between(dateCreationDu, dateCreationAu));
        
        List<Demande> demandes = (List<Demande>) demandeRepository.findAll(builder);
        return demandes;
    }

    /**
     * Récupère la liste des demandes selon les filtres spécifiés et les convertit en DTOs
     *
     * @param codeModule Code du module (optionnel)
     * @param codeClient Code du client (optionnel)
     * @param dateCreationDu Date de début pour la création
     * @param dateCreationAu Date de fin pour la création
     * @return Liste des DTOs de demandes correspondant aux critères
     */
    @Transactional(readOnly = true)
    public List<DemandeDTO> findListDemande(Integer codeModule, Integer codeClient, LocalDate dateCreationDu, LocalDate dateCreationAu) {
        List<DemandeDTO> demandeDTOs = DemandeFactory.demandeToDemandeDTOs(
                findListDemandeByFilter(codeModule, codeClient, dateCreationDu, dateCreationAu));
        return demandeDTOs;
    }
    
    /**
     * Calcule les statistiques des demandes par type (état) pour une période donnée
     *
     * @param dateCreationDu Date de début pour la création
     * @param dateCreationAu Date de fin pour la création
     * @return Liste des statistiques par type (état)
     */
    public List<StatistiqueDTO> calculDemandeByType(LocalDate dateCreationDu, LocalDate dateCreationAu) {
        List<StatistiqueDTO> list = new ArrayList<>();
        List<DemandeDTO> listDemande = findListDemande(null, null, dateCreationDu, dateCreationAu);
        
        Map<String, Long> map = listDemande.stream()
                .collect(Collectors.groupingBy(x -> x.getEtat().name(), Collectors.counting()));
        
        for (Map.Entry<String, Long> mapByType : map.entrySet()) {
            StatistiqueDTO statistique = new StatistiqueDTO();
            statistique.setLabel(mapByType.getKey());
            statistique.setValeur(mapByType.getValue());
            list.add(statistique);
        }
        
        return list;
    }
}
