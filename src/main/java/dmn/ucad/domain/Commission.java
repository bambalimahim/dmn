package dmn.ucad.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Commission.
 */
@Entity
@Table(name = "commission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "commission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Appartenance> appartences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Commission nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public Commission code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Appartenance> getAppartences() {
        return appartences;
    }

    public Commission appartences(Set<Appartenance> appartenances) {
        this.appartences = appartenances;
        return this;
    }

    public Commission addAppartence(Appartenance appartenance) {
        this.appartences.add(appartenance);
        appartenance.setCommission(this);
        return this;
    }

    public Commission removeAppartence(Appartenance appartenance) {
        this.appartences.remove(appartenance);
        appartenance.setCommission(null);
        return this;
    }

    public void setAppartences(Set<Appartenance> appartenances) {
        this.appartences = appartenances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commission)) {
            return false;
        }
        return id != null && id.equals(((Commission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
