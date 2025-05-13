package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticiasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noticias.class);
        Noticias noticias1 = new Noticias();
        noticias1.setId(1L);
        Noticias noticias2 = new Noticias();
        noticias2.setId(noticias1.getId());
        assertThat(noticias1).isEqualTo(noticias2);
        noticias2.setId(2L);
        assertThat(noticias1).isNotEqualTo(noticias2);
        noticias1.setId(null);
        assertThat(noticias1).isNotEqualTo(noticias2);
    }
}
