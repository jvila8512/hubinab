package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComponentesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Componentes.class);
        Componentes componentes1 = new Componentes();
        componentes1.setId(1L);
        Componentes componentes2 = new Componentes();
        componentes2.setId(componentes1.getId());
        assertThat(componentes1).isEqualTo(componentes2);
        componentes2.setId(2L);
        assertThat(componentes1).isNotEqualTo(componentes2);
        componentes1.setId(null);
        assertThat(componentes1).isNotEqualTo(componentes2);
    }
}
