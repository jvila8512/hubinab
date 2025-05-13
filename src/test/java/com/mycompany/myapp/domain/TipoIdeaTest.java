package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoIdeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoIdea.class);
        TipoIdea tipoIdea1 = new TipoIdea();
        tipoIdea1.setId(1L);
        TipoIdea tipoIdea2 = new TipoIdea();
        tipoIdea2.setId(tipoIdea1.getId());
        assertThat(tipoIdea1).isEqualTo(tipoIdea2);
        tipoIdea2.setId(2L);
        assertThat(tipoIdea1).isNotEqualTo(tipoIdea2);
        tipoIdea1.setId(null);
        assertThat(tipoIdea1).isNotEqualTo(tipoIdea2);
    }
}
