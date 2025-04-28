/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Poste")
@NamedQueries({
    @NamedQuery(name = "Poste.findAll", query = "SELECT p FROM Poste p")})
public class Poste implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "designation")
    private String designation;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_poste")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPoste;
    
    
    @Column(name = "actif")
    private Boolean actif;
    
    @OneToMany(mappedBy = "poste")
    private List<User> users;

    public Poste() {
    }

    public Poste(Integer idPoste) {
        this.idPoste = idPoste;
    }

    public Poste(Integer idPoste, String designation) {
        this.idPoste = idPoste;
        this.designation = designation;
    }

    public Integer getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Integer idPoste) {
        this.idPoste = idPoste;
    }


    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoste != null ? idPoste.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Poste other = (Poste) obj;
        return Objects.equals(this.idPoste, other.idPoste);
    }

    @Override
    public String toString() {
        return "Poste [idPoste=" + idPoste + ", designation=" + designation + ", actif=" + actif + "]";
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}
