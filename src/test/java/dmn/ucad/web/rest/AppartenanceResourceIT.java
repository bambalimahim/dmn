package dmn.ucad.web.rest;

import dmn.ucad.GestionStockApp;
import dmn.ucad.domain.Appartenance;
import dmn.ucad.repository.AppartenanceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppartenanceResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppartenanceResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private AppartenanceRepository appartenanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppartenanceMockMvc;

    private Appartenance appartenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartenance createEntity(EntityManager em) {
        Appartenance appartenance = new Appartenance()
            .designation(DEFAULT_DESIGNATION);
        return appartenance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartenance createUpdatedEntity(EntityManager em) {
        Appartenance appartenance = new Appartenance()
            .designation(UPDATED_DESIGNATION);
        return appartenance;
    }

    @BeforeEach
    public void initTest() {
        appartenance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppartenance() throws Exception {
        int databaseSizeBeforeCreate = appartenanceRepository.findAll().size();
        // Create the Appartenance
        restAppartenanceMockMvc.perform(post("/api/appartenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isCreated());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeCreate + 1);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createAppartenanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appartenanceRepository.findAll().size();

        // Create the Appartenance with an existing ID
        appartenance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppartenanceMockMvc.perform(post("/api/appartenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = appartenanceRepository.findAll().size();
        // set the field null
        appartenance.setDesignation(null);

        // Create the Appartenance, which fails.


        restAppartenanceMockMvc.perform(post("/api/appartenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isBadRequest());

        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppartenances() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        // Get all the appartenanceList
        restAppartenanceMockMvc.perform(get("/api/appartenances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }
    
    @Test
    @Transactional
    public void getAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        // Get the appartenance
        restAppartenanceMockMvc.perform(get("/api/appartenances/{id}", appartenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartenance.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }
    @Test
    @Transactional
    public void getNonExistingAppartenance() throws Exception {
        // Get the appartenance
        restAppartenanceMockMvc.perform(get("/api/appartenances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();

        // Update the appartenance
        Appartenance updatedAppartenance = appartenanceRepository.findById(appartenance.getId()).get();
        // Disconnect from session so that the updates on updatedAppartenance are not directly saved in db
        em.detach(updatedAppartenance);
        updatedAppartenance
            .designation(UPDATED_DESIGNATION);

        restAppartenanceMockMvc.perform(put("/api/appartenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppartenance)))
            .andExpect(status().isOk());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc.perform(put("/api/appartenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeDelete = appartenanceRepository.findAll().size();

        // Delete the appartenance
        restAppartenanceMockMvc.perform(delete("/api/appartenances/{id}", appartenance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
