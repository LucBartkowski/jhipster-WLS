package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ClassificatieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classificatie.class);
        Classificatie classificatie1 = new Classificatie();
        classificatie1.setId(1L);
        Classificatie classificatie2 = new Classificatie();
        classificatie2.setId(classificatie1.getId());
        assertThat(classificatie1).isEqualTo(classificatie2);
        classificatie2.setId(2L);
        assertThat(classificatie1).isNotEqualTo(classificatie2);
        classificatie1.setId(null);
        assertThat(classificatie1).isNotEqualTo(classificatie2);
    }
}
