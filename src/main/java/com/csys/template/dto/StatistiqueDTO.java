package com.csys.template.dto;

import java.io.Serializable;
import java.util.Map;

public class StatistiqueDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String label;
    private Long valeur;
    private Map<String, Long> details;
    
    public StatistiqueDTO() {
    }
    
    public StatistiqueDTO(String label, Long valeur) {
        this.label = label;
        this.valeur = valeur;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public Long getValeur() {
        return valeur;
    }
    
    public void setValeur(Long valeur) {
        this.valeur = valeur;
    }
    
    public Map<String, Long> getDetails() {
        return details;
    }
    
    public void setDetails(Map<String, Long> details) {
        this.details = details;
    }
}