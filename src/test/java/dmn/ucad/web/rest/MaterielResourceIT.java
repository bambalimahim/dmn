package dmn.ucad.web.rest;

import dmn.ucad.GestionStockApp;
import dmn.ucad.domain.Materiel;
import dmn.ucad.repository.MaterielRepository;

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
 * Integration tests for the {@link MaterielResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaterielResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterielMockMvc;

    private Materiel materiel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiel createEntity(EntityManager em) {
        Materiel materiel = new Materiel()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return materiel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiel createUpdatedEntity(EntityManager em) {
        Materiel materiel = new Materiel()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return materiel;
    }

    @BeforeEach
    public void initTest() {
        materiel = createEntity(em);
    }

    @Test
    @Transactional
    public void createMateriel() throws Exception {
        int databaseSizeBeforeCreate = materielRepository.findAll().size();
        // Create the Materiel
        restMaterielMockMvc.perform(post("/api/materiels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isCreated());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate + 1);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMateriel.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createMaterielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materielRepository.findAll().size();

        // Create the Materiel with an existing ID
        materiel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterielMockMvc.perform(post("/api/materiels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isBadRequest());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materielRepository.findAll().size();
        // set the field null
        materiel.setCode(null);

        // Create the Materiel, which fails.


        restMaterielMockMvc.perform(post("/api/materiels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isBadRequest());

        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMateriels() throws Exception {
        // Initialize the database
        materielRepository.saveAndFlush(materiel);

        // Get all the materielList
        restMaterielMockMvc.perform(get("/api/materiels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getMateriel() throws Exception {
        // Initialize the database
        materielRepository.saveAndFlush(materiel);

        // Get the materiel
        restMaterielMockMvc.perform(get("/api/materiels/{id}", materiel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingMateriel() throws Exception {
        // Get the materiel
        restMaterielMockMvc.perform(get("/api/materiels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMateriel() throws Exception {
        // Initialize the database
        materielRepository.saveAndFlush(materiel);

        int databaseSizeBeforeUpdate = materielRepository.findAll().size();

        // Update the materiel
        Materiel updatedMateriel = materielRepository.findById(materiel.getId()).get();
        // Disconnect from session so that the updates on updatedMateriel are not directly saved in db
        em.detach(updatedMateriel);
        updatedMateriel
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restMaterielMockMvc.perform(put("/api/materiels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMateriel)))
            .andExpect(status().isOk());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMateriel.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielMockMvc.perform(put("/api/materiels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isBadRequest());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMateriel() throws Exception {
        // Initialize the database
        materielRepository.saveAndFlush(materiel);

        int databaseSizeBeforeDelete = materielRepository.findAll().size();

        // Delete the materiel
        restMaterielMockMvc.perform(delete("/api/materiels/{id}", materiel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
