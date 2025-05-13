package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcosistemaRolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcosistemaRol.class);
        EcosistemaRol ecosistemaRol1 = new EcosistemaRol();
        ecosistemaRol1.setId(1L);
        EcosistemaRol ecosistemaRol2 = new EcosistemaRol();
        ecosistemaRol2.setId(ecosistemaRol1.getId());
        assertThat(ecosistemaRol1).isEqualTo(ecosistemaRol2);
        ecosistemaRol2.setId(2L);
        assertThat(ecosistemaRol1).isNotEqualTo(ecosistemaRol2);
        ecosistemaRol1.setId(null);
        assertThat(ecosistemaRol1).isNotEqualTo(ecosistemaRol2);
    }
}
