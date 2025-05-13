package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChangeMackerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChangeMacker.class);
        ChangeMacker changeMacker1 = new ChangeMacker();
        changeMacker1.setId(1L);
        ChangeMacker changeMacker2 = new ChangeMacker();
        changeMacker2.setId(changeMacker1.getId());
        assertThat(changeMacker1).isEqualTo(changeMacker2);
        changeMacker2.setId(2L);
        assertThat(changeMacker1).isNotEqualTo(changeMacker2);
        changeMacker1.setId(null);
        assertThat(changeMacker1).isNotEqualTo(changeMacker2);
    }
}
