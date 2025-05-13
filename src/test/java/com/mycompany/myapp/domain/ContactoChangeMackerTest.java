package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactoChangeMackerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoChangeMacker.class);
        ContactoChangeMacker contactoChangeMacker1 = new ContactoChangeMacker();
        contactoChangeMacker1.setId(1L);
        ContactoChangeMacker contactoChangeMacker2 = new ContactoChangeMacker();
        contactoChangeMacker2.setId(contactoChangeMacker1.getId());
        assertThat(contactoChangeMacker1).isEqualTo(contactoChangeMacker2);
        contactoChangeMacker2.setId(2L);
        assertThat(contactoChangeMacker1).isNotEqualTo(contactoChangeMacker2);
        contactoChangeMacker1.setId(null);
        assertThat(contactoChangeMacker1).isNotEqualTo(contactoChangeMacker2);
    }
}
