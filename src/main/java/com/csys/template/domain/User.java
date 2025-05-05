/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.csys.template.domain.enums.Role;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

/**
 *
 * @author adamb
 */
@Entity
@Table(name = "[user]")
@NamedQueries({
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100) // Augmenter la taille maximale à 100 caractères
    @Column(name = "password", length = 100) // Modifier également la longueur de la colonne
    private String password;
    @Size(max = 50)
    @Column(name = "description")
    private String description;
    
    // Nouveaux attributs
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    
    @Size(max = 50)
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    // Supprimer ou commenter cette ligne
    //@OneToMany(mappedBy = "user")
    //private List<Demande> demandeList;

    @OneToMany(mappedBy = "createur")
    private List<Demande> demandesCreees;
    
    @OneToMany(mappedBy = "collaborateur")
    private List<Demande> demandesAssignees;

    @ManyToOne
    @JoinColumn(name = "id_poste", referencedColumnName = "id_poste")
    private Poste poste;
    
    @ManyToOne
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    private Equipe equipe;
    
    @Column(name = "actif")
    private Boolean actif;
   
    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
    
    // Getters et setters pour les nouveaux attributs
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

    // public List<Demande> getDemandeList() {
    //     return demandeList;
    // }

    // public void setDemandeList(List<Demande> demandeList) {
    //     this.demandeList = demandeList;
    // }

    public List<Demande> getDemandesCreees() {
        return demandesCreees;
    }
    
    public void setDemandesCreees(List<Demande> demandesCreees) {
        this.demandesCreees = demandesCreees;
    }
    
    public List<Demande> getDemandesAssignees() {
        return demandesAssignees;
    }
    
    public void setDemandesAssignees(List<Demande> demandesAssignees) {
        this.demandesAssignees = demandesAssignees;
    }
    
    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.caisse.domain.User[ username=" + username + " ]";
    }
    
}
