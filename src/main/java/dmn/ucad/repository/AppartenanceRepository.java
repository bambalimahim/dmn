package dmn.ucad.repository;

import dmn.ucad.domain.Appartenance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Appartenance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartenanceRepository extends JpaRepository<Appartenance, Long> {
}
