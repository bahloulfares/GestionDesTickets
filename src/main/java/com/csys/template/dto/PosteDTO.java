package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PosteDTO {
  private Integer idPoste;

  @NotNull
  @Size(
      min = 1,
      max = 100
  )
  private String designation;

  private Boolean actif;

  public Integer getIdPoste() {
    return idPoste;
  }

  public void setIdPoste(Integer idPoste) {
    this.idPoste = idPoste;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public Boolean getActif() {
    return actif;
  }

  public void setActif(Boolean actif) {
    this.actif = actif;
  }
}

