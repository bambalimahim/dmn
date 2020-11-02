package dmn.ucad.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Materiel.
 */
@Entity
@Table(name = "materiel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Materiel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "materiel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Appartenance> appartenances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Materiel code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Materiel libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Appartenance> getAppartenances() {
        return appartenances;
    }

    public Materiel appartenances(Set<Appartenance> appartenances) {
        this.appartenances = appartenances;
        return this;
    }

    public Materiel addAppartenance(Appartenance appartenance) {
        this.appartenances.add(appartenance);
        appartenance.setMateriel(this);
        return this;
    }

    public Materiel removeAppartenance(Appartenance appartenance) {
        this.appartenances.remove(appartenance);
        appartenance.setMateriel(null);
        return this;
    }

    public void setAppartenances(Set<Appartenance> appartenances) {
        this.appartenances = appartenances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materiel)) {
            return false;
        }
        return id != null && id.equals(((Materiel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materiel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
