package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcosistemaComponenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcosistemaComponente.class);
        EcosistemaComponente ecosistemaComponente1 = new EcosistemaComponente();
        ecosistemaComponente1.setId(1L);
        EcosistemaComponente ecosistemaComponente2 = new EcosistemaComponente();
        ecosistemaComponente2.setId(ecosistemaComponente1.getId());
        assertThat(ecosistemaComponente1).isEqualTo(ecosistemaComponente2);
        ecosistemaComponente2.setId(2L);
        assertThat(ecosistemaComponente1).isNotEqualTo(ecosistemaComponente2);
        ecosistemaComponente1.setId(null);
        assertThat(ecosistemaComponente1).isNotEqualTo(ecosistemaComponente2);
    }
}
