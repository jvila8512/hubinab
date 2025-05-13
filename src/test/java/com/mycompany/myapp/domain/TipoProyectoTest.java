package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoProyectoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProyecto.class);
        TipoProyecto tipoProyecto1 = new TipoProyecto();
        tipoProyecto1.setId(1L);
        TipoProyecto tipoProyecto2 = new TipoProyecto();
        tipoProyecto2.setId(tipoProyecto1.getId());
        assertThat(tipoProyecto1).isEqualTo(tipoProyecto2);
        tipoProyecto2.setId(2L);
        assertThat(tipoProyecto1).isNotEqualTo(tipoProyecto2);
        tipoProyecto1.setId(null);
        assertThat(tipoProyecto1).isNotEqualTo(tipoProyecto2);
    }
}
