package com.csys.template.domain;

import com.csys.template.domain.enums.EtatDemande;
import com.csys.template.domain.enums.PrioriteDemande;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "Demande")
@NamedQueries({
        @NamedQuery(name = "Demande.findAll", query = "SELECT d FROM Demande d") })
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDemande")
    private Integer idDemande;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @NotNull
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date_affectation_equipe")
    private LocalDate dateAffectationEquipe;

    @Column(name = "date_affectation_collaborateur")
    private LocalDate dateAffectationCollaborateur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, length = 30)
    private EtatDemande etat;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priorite", nullable = false, length = 20)
    private PrioriteDemande priorite;

    @Column(name = "date_echeance")
    private LocalDate dateEcheance;

    @Size(max = 1000)
    @Column(name = "commentaire")
    private String commentaire;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idClient", referencedColumnName = "id_client", nullable = false)
    private Client client;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "idEquipe", referencedColumnName = "id_equipe")
    private Equipe equipe;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idModule", referencedColumnName = "id_module", nullable = false)
    private Module module;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    private User createur;

    
    private String username;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "idCollaborateur", referencedColumnName = "username")
    private User collaborateur;
    
    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDate.now();
        this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // === Constructeurs ===
    public Demande() {
    }

    public Demande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Demande(Integer idDemande, LocalDate dateCreation, EtatDemande etat, PrioriteDemande priorite) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateAffectationEquipe() {
        return dateAffectationEquipe;
    }

    public void setDateAffectationEquipe(LocalDate dateAffectationEquipe) {
        this.dateAffectationEquipe = dateAffectationEquipe;
    }

    public LocalDate getDateAffectationCollaborateur() {
        return dateAffectationCollaborateur;
    }

    public void setDateAffectationCollaborateur(LocalDate dateAffectationCollaborateur) {
        this.dateAffectationCollaborateur = dateAffectationCollaborateur;
    }

    public void setPriorite(PrioriteDemande priorite) {
        this.priorite = priorite;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // === Méthodes utilitaires ===
    @Override
    public int hashCode() {
        return idDemande != null ? idDemande.hashCode() : 0;
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
