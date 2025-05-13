package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcosistemaPeticionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcosistemaPeticiones.class);
        EcosistemaPeticiones ecosistemaPeticiones1 = new EcosistemaPeticiones();
        ecosistemaPeticiones1.setId(1L);
        EcosistemaPeticiones ecosistemaPeticiones2 = new EcosistemaPeticiones();
        ecosistemaPeticiones2.setId(ecosistemaPeticiones1.getId());
        assertThat(ecosistemaPeticiones1).isEqualTo(ecosistemaPeticiones2);
        ecosistemaPeticiones2.setId(2L);
        assertThat(ecosistemaPeticiones1).isNotEqualTo(ecosistemaPeticiones2);
        ecosistemaPeticiones1.setId(null);
        assertThat(ecosistemaPeticiones1).isNotEqualTo(ecosistemaPeticiones2);
    }
}
