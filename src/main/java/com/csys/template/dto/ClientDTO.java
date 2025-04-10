package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDTO {
  private Integer idClient;

  @NotNull
  @Size(
      min = 1,
      max = 100
  )
  private String nom;

  private Integer telephone;

  @NotNull
  @Size(
      min = 1,
      max = 100
  )
  private String email;

  @Size(
      min = 0,
      max = 255
  )
  private String adresse;

  public Integer getIdClient() {
    return idClient;
  }

  public void setIdClient(Integer idClient) {
    this.idClient = idClient;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Integer getTelephone() {
    return telephone;
  }

  public void setTelephone(Integer telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }
}

