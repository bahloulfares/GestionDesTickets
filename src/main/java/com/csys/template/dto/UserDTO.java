package com.csys.template.dto;

import java.lang.String;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

  private List mvtConsigneList;

  private List mvtConsigneList1;

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

  public List getMvtConsigneList() {
    return mvtConsigneList;
  }

  public void setMvtConsigneList(List mvtConsigneList) {
    this.mvtConsigneList = mvtConsigneList;
  }

  public List getMvtConsigneList1() {
    return mvtConsigneList1;
  }

  public void setMvtConsigneList1(List mvtConsigneList1) {
    this.mvtConsigneList1 = mvtConsigneList1;
  }
}

