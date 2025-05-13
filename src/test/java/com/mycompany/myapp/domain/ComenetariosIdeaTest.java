package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComenetariosIdeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComenetariosIdea.class);
        ComenetariosIdea comenetariosIdea1 = new ComenetariosIdea();
        comenetariosIdea1.setId(1L);
        ComenetariosIdea comenetariosIdea2 = new ComenetariosIdea();
        comenetariosIdea2.setId(comenetariosIdea1.getId());
        assertThat(comenetariosIdea1).isEqualTo(comenetariosIdea2);
        comenetariosIdea2.setId(2L);
        assertThat(comenetariosIdea1).isNotEqualTo(comenetariosIdea2);
        comenetariosIdea1.setId(null);
        assertThat(comenetariosIdea1).isNotEqualTo(comenetariosIdea2);
    }
}
