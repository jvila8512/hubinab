package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcosistemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecosistema.class);
        Ecosistema ecosistema1 = new Ecosistema();
        ecosistema1.setId(1L);
        Ecosistema ecosistema2 = new Ecosistema();
        ecosistema2.setId(ecosistema1.getId());
        assertThat(ecosistema1).isEqualTo(ecosistema2);
        ecosistema2.setId(2L);
        assertThat(ecosistema1).isNotEqualTo(ecosistema2);
        ecosistema1.setId(null);
        assertThat(ecosistema1).isNotEqualTo(ecosistema2);
    }
}
