package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RedesSocialesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedesSociales.class);
        RedesSociales redesSociales1 = new RedesSociales();
        redesSociales1.setId(1L);
        RedesSociales redesSociales2 = new RedesSociales();
        redesSociales2.setId(redesSociales1.getId());
        assertThat(redesSociales1).isEqualTo(redesSociales2);
        redesSociales2.setId(2L);
        assertThat(redesSociales1).isNotEqualTo(redesSociales2);
        redesSociales1.setId(null);
        assertThat(redesSociales1).isNotEqualTo(redesSociales2);
    }
}
