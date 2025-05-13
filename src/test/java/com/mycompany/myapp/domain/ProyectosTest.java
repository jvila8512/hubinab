package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProyectosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyectos.class);
        Proyectos proyectos1 = new Proyectos();
        proyectos1.setId(1L);
        Proyectos proyectos2 = new Proyectos();
        proyectos2.setId(proyectos1.getId());
        assertThat(proyectos1).isEqualTo(proyectos2);
        proyectos2.setId(2L);
        assertThat(proyectos1).isNotEqualTo(proyectos2);
        proyectos1.setId(null);
        assertThat(proyectos1).isNotEqualTo(proyectos2);
    }
}
