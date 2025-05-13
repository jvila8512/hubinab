package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contacto.class);
        Contacto contacto1 = new Contacto();
        contacto1.setId(1L);
        Contacto contacto2 = new Contacto();
        contacto2.setId(contacto1.getId());
        assertThat(contacto1).isEqualTo(contacto2);
        contacto2.setId(2L);
        assertThat(contacto1).isNotEqualTo(contacto2);
        contacto1.setId(null);
        assertThat(contacto1).isNotEqualTo(contacto2);
    }
}
