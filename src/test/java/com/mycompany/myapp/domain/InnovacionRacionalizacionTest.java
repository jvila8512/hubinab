package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InnovacionRacionalizacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InnovacionRacionalizacion.class);
        InnovacionRacionalizacion innovacionRacionalizacion1 = new InnovacionRacionalizacion();
        innovacionRacionalizacion1.setId(1L);
        InnovacionRacionalizacion innovacionRacionalizacion2 = new InnovacionRacionalizacion();
        innovacionRacionalizacion2.setId(innovacionRacionalizacion1.getId());
        assertThat(innovacionRacionalizacion1).isEqualTo(innovacionRacionalizacion2);
        innovacionRacionalizacion2.setId(2L);
        assertThat(innovacionRacionalizacion1).isNotEqualTo(innovacionRacionalizacion2);
        innovacionRacionalizacion1.setId(null);
        assertThat(innovacionRacionalizacion1).isNotEqualTo(innovacionRacionalizacion2);
    }
}
