package com.csys.template.dto;

import com.csys.template.domain.Client;
import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.domain.User;
import com.csys.template.domain.enums.EtatDemande;
import com.csys.template.domain.enums.PrioriteDemande;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class DemandeDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer idDemande;

  //@NotNull
  private LocalDate dateCreation;

  @Size(max = 500)
  private String description;

  private LocalDate dateAffectationEquipe;

  private LocalDate dateAffectationCollaborateur;

  @NotNull
  private EtatDemande etat;

  @NotNull
  private PrioriteDemande priorite;

  private LocalDate dateEcheance;

  @Size(max = 1000)
  private String commentaire;

  private Client client;

  private Equipe equipe;

  private Module module;

  private User createur;

  private User collaborateur;

  // === Getters and Setters ===

  public Integer getIdDemande() {
    return idDemande;
  }

  public void setIdDemande(Integer idDemande) {
    this.idDemande = idDemande;
  }

  public LocalDate getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(LocalDate dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDateAffectationEquipe() {
    return dateAffectationEquipe;
  }

  public void setDateAffectationEquipe(LocalDate dateAffectationEquipe) {
    this.dateAffectationEquipe = dateAffectationEquipe;
  }

  public LocalDate getDateAffectationCollaborateur() {
    return dateAffectationCollaborateur;
  }

  public void setDateAffectationCollaborateur(LocalDate dateAffectationCollaborateur) {
    this.dateAffectationCollaborateur = dateAffectationCollaborateur;
  }

  public EtatDemande getEtat() {
    return etat;
  }

  public void setEtat(EtatDemande etat) {
    this.etat = etat;
  }

  public PrioriteDemande getPriorite() {
    return priorite;
  }

  public void setPriorite(PrioriteDemande priorite) {
    this.priorite = priorite;
  }

  public LocalDate getDateEcheance() {
    return dateEcheance;
  }

  public void setDateEcheance(LocalDate dateEcheance) {
    this.dateEcheance = dateEcheance;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Equipe getEquipe() {
    return equipe;
  }

  public void setEquipe(Equipe equipe) {
    this.equipe = equipe;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public User getCreateur() {
    return createur;
  }

  public void setCreateur(User createur) {
    this.createur = createur;
  }

  public User getCollaborateur() {
    return collaborateur;
  }

  public void setCollaborateur(User collaborateur) {
    this.collaborateur = collaborateur;
  }
}
