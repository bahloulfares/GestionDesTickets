package com.csys.template.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StatistiquesGlobalesDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long totalDemandes;
    private Map<String, Long> demandesParEtat;
    private Map<String, Long> demandesParClient;
    private Map<String, Long> demandesParModule;
    private Map<String, Long> demandesParEquipe;
    private Map<String, Long> demandesParPriorite;
    private List<StatistiqueDTO> statistiquesPersonnalisees;
    
    public Long getTotalDemandes() {
        return totalDemandes;
    }
    
    public void setTotalDemandes(Long totalDemandes) {
        this.totalDemandes = totalDemandes;
    }
    
    public Map<String, Long> getDemandesParEtat() {
        return demandesParEtat;
    }
    
    public void setDemandesParEtat(Map<String, Long> demandesParEtat) {
        this.demandesParEtat = demandesParEtat;
    }
    
    public Map<String, Long> getDemandesParClient() {
        return demandesParClient;
    }
    
    public void setDemandesParClient(Map<String, Long> demandesParClient) {
        this.demandesParClient = demandesParClient;
    }
    
    public Map<String, Long> getDemandesParModule() {
        return demandesParModule;
    }
    
    public void setDemandesParModule(Map<String, Long> demandesParModule) {
        this.demandesParModule = demandesParModule;
    }
    
    public Map<String, Long> getDemandesParEquipe() {
        return demandesParEquipe;
    }
    
    public void setDemandesParEquipe(Map<String, Long> demandesParEquipe) {
        this.demandesParEquipe = demandesParEquipe;
    }
    
    public Map<String, Long> getDemandesParPriorite() {
        return demandesParPriorite;
    }
    
    public void setDemandesParPriorite(Map<String, Long> demandesParPriorite) {
        this.demandesParPriorite = demandesParPriorite;
    }
    
    public List<StatistiqueDTO> getStatistiquesPersonnalisees() {
        return statistiquesPersonnalisees;
    }
    
    public void setStatistiquesPersonnalisees(List<StatistiqueDTO> statistiquesPersonnalisees) {
        this.statistiquesPersonnalisees = statistiquesPersonnalisees;
    }
}