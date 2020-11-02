package dmn.ucad.web.rest;

import dmn.ucad.domain.Appartenance;
import dmn.ucad.repository.AppartenanceRepository;
import dmn.ucad.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dmn.ucad.domain.Appartenance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppartenanceResource {

    private final Logger log = LoggerFactory.getLogger(AppartenanceResource.class);

    private static final String ENTITY_NAME = "appartenance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppartenanceRepository appartenanceRepository;

    public AppartenanceResource(AppartenanceRepository appartenanceRepository) {
        this.appartenanceRepository = appartenanceRepository;
    }

    /**
     * {@code POST  /appartenances} : Create a new appartenance.
     *
     * @param appartenance the appartenance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appartenance, or with status {@code 400 (Bad Request)} if the appartenance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appartenances")
    public ResponseEntity<Appartenance> createAppartenance(@Valid @RequestBody Appartenance appartenance) throws URISyntaxException {
        log.debug("REST request to save Appartenance : {}", appartenance);
        if (appartenance.getId() != null) {
            throw new BadRequestAlertException("A new appartenance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appartenance result = appartenanceRepository.save(appartenance);
        return ResponseEntity.created(new URI("/api/appartenances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appartenances} : Updates an existing appartenance.
     *
     * @param appartenance the appartenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartenance,
     * or with status {@code 400 (Bad Request)} if the appartenance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appartenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appartenances")
    public ResponseEntity<Appartenance> updateAppartenance(@Valid @RequestBody Appartenance appartenance) throws URISyntaxException {
        log.debug("REST request to update Appartenance : {}", appartenance);
        if (appartenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appartenance result = appartenanceRepository.save(appartenance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appartenance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appartenances} : get all the appartenances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appartenances in body.
     */
    @GetMapping("/appartenances")
    public ResponseEntity<List<Appartenance>> getAllAppartenances(Pageable pageable) {
        log.debug("REST request to get a page of Appartenances");
        Page<Appartenance> page = appartenanceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appartenances/:id} : get the "id" appartenance.
     *
     * @param id the id of the appartenance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appartenance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appartenances/{id}")
    public ResponseEntity<Appartenance> getAppartenance(@PathVariable Long id) {
        log.debug("REST request to get Appartenance : {}", id);
        Optional<Appartenance> appartenance = appartenanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appartenance);
    }

    /**
     * {@code DELETE  /appartenances/:id} : delete the "id" appartenance.
     *
     * @param id the id of the appartenance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appartenances/{id}")
    public ResponseEntity<Void> deleteAppartenance(@PathVariable Long id) {
        log.debug("REST request to delete Appartenance : {}", id);
        appartenanceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
