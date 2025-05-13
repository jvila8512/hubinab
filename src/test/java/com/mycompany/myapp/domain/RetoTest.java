package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RetoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reto.class);
        Reto reto1 = new Reto();
        reto1.setId(1L);
        Reto reto2 = new Reto();
        reto2.setId(reto1.getId());
        assertThat(reto1).isEqualTo(reto2);
        reto2.setId(2L);
        assertThat(reto1).isNotEqualTo(reto2);
        reto1.setId(null);
        assertThat(reto1).isNotEqualTo(reto2);
    }
}
