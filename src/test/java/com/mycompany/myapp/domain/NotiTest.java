package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noti.class);
        Noti noti1 = new Noti();
        noti1.setId(1L);
        Noti noti2 = new Noti();
        noti2.setId(noti1.getId());
        assertThat(noti1).isEqualTo(noti2);
        noti2.setId(2L);
        assertThat(noti1).isNotEqualTo(noti2);
        noti1.setId(null);
        assertThat(noti1).isNotEqualTo(noti2);
    }
}
