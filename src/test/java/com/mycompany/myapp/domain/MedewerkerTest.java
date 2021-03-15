package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MedewerkerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medewerker.class);
        Medewerker medewerker1 = new Medewerker();
        medewerker1.setId(1L);
        Medewerker medewerker2 = new Medewerker();
        medewerker2.setId(medewerker1.getId());
        assertThat(medewerker1).isEqualTo(medewerker2);
        medewerker2.setId(2L);
        assertThat(medewerker1).isNotEqualTo(medewerker2);
        medewerker1.setId(null);
        assertThat(medewerker1).isNotEqualTo(medewerker2);
    }
}
