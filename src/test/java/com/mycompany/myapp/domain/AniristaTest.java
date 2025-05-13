package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AniristaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anirista.class);
        Anirista anirista1 = new Anirista();
        anirista1.setId(1L);
        Anirista anirista2 = new Anirista();
        anirista2.setId(anirista1.getId());
        assertThat(anirista1).isEqualTo(anirista2);
        anirista2.setId(2L);
        assertThat(anirista1).isNotEqualTo(anirista2);
        anirista1.setId(null);
        assertThat(anirista1).isNotEqualTo(anirista2);
    }
}
