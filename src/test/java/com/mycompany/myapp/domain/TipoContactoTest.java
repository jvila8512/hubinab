package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoContactoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoContacto.class);
        TipoContacto tipoContacto1 = new TipoContacto();
        tipoContacto1.setId(1L);
        TipoContacto tipoContacto2 = new TipoContacto();
        tipoContacto2.setId(tipoContacto1.getId());
        assertThat(tipoContacto1).isEqualTo(tipoContacto2);
        tipoContacto2.setId(2L);
        assertThat(tipoContacto1).isNotEqualTo(tipoContacto2);
        tipoContacto1.setId(null);
        assertThat(tipoContacto1).isNotEqualTo(tipoContacto2);
    }
}
