package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class GeregistreerdeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geregistreerde.class);
        Geregistreerde geregistreerde1 = new Geregistreerde();
        geregistreerde1.setId(1L);
        Geregistreerde geregistreerde2 = new Geregistreerde();
        geregistreerde2.setId(geregistreerde1.getId());
        assertThat(geregistreerde1).isEqualTo(geregistreerde2);
        geregistreerde2.setId(2L);
        assertThat(geregistreerde1).isNotEqualTo(geregistreerde2);
        geregistreerde1.setId(null);
        assertThat(geregistreerde1).isNotEqualTo(geregistreerde2);
    }
}
