package dmn.ucad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Appartenance.
 */
@Entity
@Table(name = "appartenance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appartenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @ManyToOne
    @JsonIgnoreProperties(value = "appartences", allowSetters = true)
    private Commission commission;

    @ManyToOne
    @JsonIgnoreProperties(value = "appartenances", allowSetters = true)
    private Materiel materiel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public Appartenance designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Commission getCommission() {
        return commission;
    }

    public Appartenance commission(Commission commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public Appartenance materiel(Materiel materiel) {
        this.materiel = materiel;
        return this;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appartenance)) {
            return false;
        }
        return id != null && id.equals(((Appartenance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appartenance{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
