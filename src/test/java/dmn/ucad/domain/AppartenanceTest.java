package dmn.ucad.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dmn.ucad.web.rest.TestUtil;

public class AppartenanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appartenance.class);
        Appartenance appartenance1 = new Appartenance();
        appartenance1.setId(1L);
        Appartenance appartenance2 = new Appartenance();
        appartenance2.setId(appartenance1.getId());
        assertThat(appartenance1).isEqualTo(appartenance2);
        appartenance2.setId(2L);
        assertThat(appartenance1).isNotEqualTo(appartenance2);
        appartenance1.setId(null);
        assertThat(appartenance1).isNotEqualTo(appartenance2);
    }
}
