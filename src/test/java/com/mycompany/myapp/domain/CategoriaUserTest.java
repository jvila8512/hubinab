package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaUser.class);
        CategoriaUser categoriaUser1 = new CategoriaUser();
        categoriaUser1.setId(1L);
        CategoriaUser categoriaUser2 = new CategoriaUser();
        categoriaUser2.setId(categoriaUser1.getId());
        assertThat(categoriaUser1).isEqualTo(categoriaUser2);
        categoriaUser2.setId(2L);
        assertThat(categoriaUser1).isNotEqualTo(categoriaUser2);
        categoriaUser1.setId(null);
        assertThat(categoriaUser1).isNotEqualTo(categoriaUser2);
    }
}
