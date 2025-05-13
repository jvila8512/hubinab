package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LineaInvestigacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaInvestigacion.class);
        LineaInvestigacion lineaInvestigacion1 = new LineaInvestigacion();
        lineaInvestigacion1.setId(1L);
        LineaInvestigacion lineaInvestigacion2 = new LineaInvestigacion();
        lineaInvestigacion2.setId(lineaInvestigacion1.getId());
        assertThat(lineaInvestigacion1).isEqualTo(lineaInvestigacion2);
        lineaInvestigacion2.setId(2L);
        assertThat(lineaInvestigacion1).isNotEqualTo(lineaInvestigacion2);
        lineaInvestigacion1.setId(null);
        assertThat(lineaInvestigacion1).isNotEqualTo(lineaInvestigacion2);
    }
}
