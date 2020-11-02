package dmn.ucad.repository;

import dmn.ucad.domain.Materiel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Materiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Long> {
}
