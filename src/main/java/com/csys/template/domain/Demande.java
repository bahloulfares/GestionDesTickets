/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Demande")
@NamedQueries({
    @NamedQuery(name = "Demande.findAll", query = "SELECT d FROM Demande d")})
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDemande")
    private Integer idDemande;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Size(max = 500)
    @Column(name = "description")
    private String description;
    @Column(name = "date_affectation_equipe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectationEquipe;
    @Column(name = "date_affectation_collaborateur")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectationCollaborateur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "etat")
    private String etat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "priorite")
    private String priorite;
    @Column(name = "date_echeance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEcheance;
    @Size(max = 1000)
    @Column(name = "commentaire")
    private String commentaire;
    @JoinColumn(name = "idClient", referencedColumnName = "id_client")
    @ManyToOne
    private Client client;
    @JoinColumn(name = "idEquipe", referencedColumnName = "id_equipe")
    @ManyToOne
    private Equipe equipe;
    @JoinColumn(name = "idModule", referencedColumnName = "id_module")
    @ManyToOne
    private Module module;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    @JoinColumn(name = "idCollaborateur", referencedColumnName = "username")
    private User user;

    public Demande() {
    }

    public Demande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Demande(Integer idDemande, Date dateCreation, String etat, String priorite) {
        this.idDemande = idDemande;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.priorite = priorite;
    }

    public Integer getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAffectationEquipe() {
        return dateAffectationEquipe;
    }

    public void setDateAffectationEquipe(Date dateAffectationEquipe) {
        this.dateAffectationEquipe = dateAffectationEquipe;
    }

    public Date getDateAffectationCollaborateur() {
        return dateAffectationCollaborateur;
    }

    public void setDateAffectationCollaborateur(Date dateAffectationCollaborateur) {
        this.dateAffectationCollaborateur = dateAffectationCollaborateur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDemande != null ? idDemande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demande)) {
            return false;
        }
        Demande other = (Demande) object;
        if ((this.idDemande == null && other.idDemande != null) || (this.idDemande != null && !this.idDemande.equals(other.idDemande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.Demande[ idDemande=" + idDemande + " ]";
    }
    
}
