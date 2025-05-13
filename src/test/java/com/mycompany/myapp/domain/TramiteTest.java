package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TramiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tramite.class);
        Tramite tramite1 = new Tramite();
        tramite1.setId(1L);
        Tramite tramite2 = new Tramite();
        tramite2.setId(tramite1.getId());
        assertThat(tramite1).isEqualTo(tramite2);
        tramite2.setId(2L);
        assertThat(tramite1).isNotEqualTo(tramite2);
        tramite1.setId(null);
        assertThat(tramite1).isNotEqualTo(tramite2);
    }
}
