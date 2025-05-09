package com.csys.template.domain;

import com.csys.template.domain.enums.EtatDemande;
import com.csys.template.domain.enums.PrioriteDemande;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Demande")
@NamedQueries({
    @NamedQuery(name = "Demande.findAll", query = "SELECT d FROM Demande d")})
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDemande")
    private Integer idDemande;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

    @NotNull
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_affectation_equipe")
    private Date dateAffectationEquipe;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_affectation_collaborateur")
    private Date dateAffectationCollaborateur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, length = 30)
    private EtatDemande etat;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priorite", nullable = false, length = 20)
    private PrioriteDemande priorite;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_echeance")
    private Date dateEcheance;

    
    @Size(max = 1000)
    @Column(name = "commentaire")  // Remove nullable = false since it's not marked @NotNull
    private String commentaire;

    @NotNull
    @ManyToOne(optional = false)
    //@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idClient", referencedColumnName = "id_client", nullable = false)
    private Client client;


    //@ManyToOne(optional = false)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipe", referencedColumnName = "id_equipe")
    private Equipe equipe;

    @NotNull
    @ManyToOne(optional = false)
    //@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idModule", referencedColumnName = "id_module", nullable = false)
    private Module module;

    @NotNull
    @ManyToOne(optional = false)
    //@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User createur;


    //@ManyToOne(optional = false)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCollaborateur", referencedColumnName = "username")
    private User collaborateur;

    // === Constructeurs ===
    public Demande() {}

    public Demande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Demande(Integer idDemande, Date dateCreation, EtatDemande etat, PrioriteDemande priorite) {
        this.idDemande = idDemande;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.priorite = priorite;
    }

    // === Getters & Setters ===
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

    // === Méthodes utilitaires ===
    @Override
    public int hashCode() {
        return idDemande != null ? idDemande.hashCode() : 0;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Demande)) return false;
//        Demande other = (Demande) obj;
//        return this.idDemande != null && this.idDemande.equals(other.idDemande);
//    }

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
        final Demande other = (Demande) obj;
        if (!Objects.equals(this.idDemande, other.idDemande)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Demande{idDemande=" + idDemande + '}';
    }
}
