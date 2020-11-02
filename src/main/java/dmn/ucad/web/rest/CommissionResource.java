package dmn.ucad.web.rest;

import dmn.ucad.domain.Commission;
import dmn.ucad.repository.CommissionRepository;
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
 * REST controller for managing {@link dmn.ucad.domain.Commission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommissionResource {

    private final Logger log = LoggerFactory.getLogger(CommissionResource.class);

    private static final String ENTITY_NAME = "commission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionRepository commissionRepository;

    public CommissionResource(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    /**
     * {@code POST  /commissions} : Create a new commission.
     *
     * @param commission the commission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commission, or with status {@code 400 (Bad Request)} if the commission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commissions")
    public ResponseEntity<Commission> createCommission(@Valid @RequestBody Commission commission) throws URISyntaxException {
        log.debug("REST request to save Commission : {}", commission);
        if (commission.getId() != null) {
            throw new BadRequestAlertException("A new commission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commission result = commissionRepository.save(commission);
        return ResponseEntity.created(new URI("/api/commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commissions} : Updates an existing commission.
     *
     * @param commission the commission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commission,
     * or with status {@code 400 (Bad Request)} if the commission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commissions")
    public ResponseEntity<Commission> updateCommission(@Valid @RequestBody Commission commission) throws URISyntaxException {
        log.debug("REST request to update Commission : {}", commission);
        if (commission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commission result = commissionRepository.save(commission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commissions} : get all the commissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissions in body.
     */
    @GetMapping("/commissions")
    public ResponseEntity<List<Commission>> getAllCommissions(Pageable pageable) {
        log.debug("REST request to get a page of Commissions");
        Page<Commission> page = commissionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commissions/:id} : get the "id" commission.
     *
     * @param id the id of the commission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commissions/{id}")
    public ResponseEntity<Commission> getCommission(@PathVariable Long id) {
        log.debug("REST request to get Commission : {}", id);
        Optional<Commission> commission = commissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commission);
    }

    /**
     * {@code DELETE  /commissions/:id} : delete the "id" commission.
     *
     * @param id the id of the commission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commissions/{id}")
    public ResponseEntity<Void> deleteCommission(@PathVariable Long id) {
        log.debug("REST request to delete Commission : {}", id);
        commissionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
