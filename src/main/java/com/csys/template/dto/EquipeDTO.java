package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EquipeDTO {
  private Integer idEquipe;

  @NotNull
  @Size(
      min = 1,
      max = 100
  )
  private String designation;

  private Boolean actif;

  private List<UserDTO> users = new ArrayList<>();

  public Integer getIdEquipe() {
    return idEquipe;
  }

  public void setIdEquipe(Integer idEquipe) {
    this.idEquipe = idEquipe;
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

  public List<UserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<UserDTO> users) {
    this.users = users;
  }
}

