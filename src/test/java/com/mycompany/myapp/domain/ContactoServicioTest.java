package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactoServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoServicio.class);
        ContactoServicio contactoServicio1 = new ContactoServicio();
        contactoServicio1.setId(1L);
        ContactoServicio contactoServicio2 = new ContactoServicio();
        contactoServicio2.setId(contactoServicio1.getId());
        assertThat(contactoServicio1).isEqualTo(contactoServicio2);
        contactoServicio2.setId(2L);
        assertThat(contactoServicio1).isNotEqualTo(contactoServicio2);
        contactoServicio1.setId(null);
        assertThat(contactoServicio1).isNotEqualTo(contactoServicio2);
    }
}
