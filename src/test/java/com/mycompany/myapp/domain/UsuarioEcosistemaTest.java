package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioEcosistemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioEcosistema.class);
        UsuarioEcosistema usuarioEcosistema1 = new UsuarioEcosistema();
        usuarioEcosistema1.setId(1L);
        UsuarioEcosistema usuarioEcosistema2 = new UsuarioEcosistema();
        usuarioEcosistema2.setId(usuarioEcosistema1.getId());
        assertThat(usuarioEcosistema1).isEqualTo(usuarioEcosistema2);
        usuarioEcosistema2.setId(2L);
        assertThat(usuarioEcosistema1).isNotEqualTo(usuarioEcosistema2);
        usuarioEcosistema1.setId(null);
        assertThat(usuarioEcosistema1).isNotEqualTo(usuarioEcosistema2);
    }
}
