package com.csys.template.dto;

import java.lang.String;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.csys.template.domain.enums.Role;

public class UserDTO {
  @NotNull
  @Size(
      min = 1,
      max = 50
  )
  private String username;

  @NotNull
  @Size(
      min = 1,
      max = 15
  )
  private String password;

  @Size(
      min = 0,
      max = 50
  )
  private String description;

  // private List mvtConsigneList;

  // private List mvtConsigneList1;

  private String nom;
  private String prenom;
  private Role role;
  private Integer idPoste;
  private Integer idEquipe;
  //private String equipeDesignation;
  private Boolean actif;
  //private String posteDesignation;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNom() {
      return nom;
  }

  public void setNom(String nom) {
      this.nom = nom;
  }

  public String getPrenom() {
      return prenom;
  }

  public void setPrenom(String prenom) {
      this.prenom = prenom;
  }

  public Role getRole() {
      return role;
  }

  public void setRole(Role role) {
      this.role = role;
  }

  public Integer getIdPoste() {
      return idPoste;
  }

  public void setIdPoste(Integer idPoste) {
      this.idPoste = idPoste;
  }

  public Integer getIdEquipe() {
      return idEquipe;
  }

  public void setIdEquipe(Integer idEquipe) {
      this.idEquipe = idEquipe;
  }

//  public String getEquipeDesignation() {
//      return equipeDesignation;
//  }
//
//  public void setEquipeDesignation(String equipeDesignation) {
//      this.equipeDesignation = equipeDesignation;
//  }

  public Boolean getActif() {
      return actif;
  }

  public void setActif(Boolean actif) {
      this.actif = actif;
  }
  
//  public String getPosteDesignation() {
//      return posteDesignation;
//  }
//
//  public void setPosteDesignation(String posteDesignation) {
//      this.posteDesignation = posteDesignation;
//  }
}

