package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiciosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicios.class);
        Servicios servicios1 = new Servicios();
        servicios1.setId(1L);
        Servicios servicios2 = new Servicios();
        servicios2.setId(servicios1.getId());
        assertThat(servicios1).isEqualTo(servicios2);
        servicios2.setId(2L);
        assertThat(servicios1).isNotEqualTo(servicios2);
        servicios1.setId(null);
        assertThat(servicios1).isNotEqualTo(servicios2);
    }
}
