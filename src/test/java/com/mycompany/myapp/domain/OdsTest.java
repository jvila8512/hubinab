package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OdsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ods.class);
        Ods ods1 = new Ods();
        ods1.setId(1L);
        Ods ods2 = new Ods();
        ods2.setId(ods1.getId());
        assertThat(ods1).isEqualTo(ods2);
        ods2.setId(2L);
        assertThat(ods1).isNotEqualTo(ods2);
        ods1.setId(null);
        assertThat(ods1).isNotEqualTo(ods2);
    }
}
