package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ModuleDTO {
  private Integer idModule;

  @NotNull
  @Size(
      min = 1,
      max = 100
  )
  private String designation;

  private Boolean actif;

  public Integer getIdModule() {
    return idModule;
  }

  public void setIdModule(Integer idModule) {
    this.idModule = idModule;
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

