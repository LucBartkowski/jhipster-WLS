package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CrestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crest.class);
        Crest crest1 = new Crest();
        crest1.setId(1L);
        Crest crest2 = new Crest();
        crest2.setId(crest1.getId());
        assertThat(crest1).isEqualTo(crest2);
        crest2.setId(2L);
        assertThat(crest1).isNotEqualTo(crest2);
        crest1.setId(null);
        assertThat(crest1).isNotEqualTo(crest2);
    }
}
