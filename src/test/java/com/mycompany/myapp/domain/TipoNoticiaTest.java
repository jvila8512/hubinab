package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoNoticiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoNoticia.class);
        TipoNoticia tipoNoticia1 = new TipoNoticia();
        tipoNoticia1.setId(1L);
        TipoNoticia tipoNoticia2 = new TipoNoticia();
        tipoNoticia2.setId(tipoNoticia1.getId());
        assertThat(tipoNoticia1).isEqualTo(tipoNoticia2);
        tipoNoticia2.setId(2L);
        assertThat(tipoNoticia1).isNotEqualTo(tipoNoticia2);
        tipoNoticia1.setId(null);
        assertThat(tipoNoticia1).isNotEqualTo(tipoNoticia2);
    }
}
