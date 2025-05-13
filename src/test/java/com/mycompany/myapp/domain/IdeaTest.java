package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Idea.class);
        Idea idea1 = new Idea();
        idea1.setId(1L);
        Idea idea2 = new Idea();
        idea2.setId(idea1.getId());
        assertThat(idea1).isEqualTo(idea2);
        idea2.setId(2L);
        assertThat(idea1).isNotEqualTo(idea2);
        idea1.setId(null);
        assertThat(idea1).isNotEqualTo(idea2);
    }
}
