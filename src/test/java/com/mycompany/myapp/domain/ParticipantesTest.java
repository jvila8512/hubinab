package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipantesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participantes.class);
        Participantes participantes1 = new Participantes();
        participantes1.setId(1L);
        Participantes participantes2 = new Participantes();
        participantes2.setId(participantes1.getId());
        assertThat(participantes1).isEqualTo(participantes2);
        participantes2.setId(2L);
        assertThat(participantes1).isNotEqualTo(participantes2);
        participantes1.setId(null);
        assertThat(participantes1).isNotEqualTo(participantes2);
    }
}
