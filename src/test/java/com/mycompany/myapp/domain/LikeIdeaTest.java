package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LikeIdeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeIdea.class);
        LikeIdea likeIdea1 = new LikeIdea();
        likeIdea1.setId(1L);
        LikeIdea likeIdea2 = new LikeIdea();
        likeIdea2.setId(likeIdea1.getId());
        assertThat(likeIdea1).isEqualTo(likeIdea2);
        likeIdea2.setId(2L);
        assertThat(likeIdea1).isNotEqualTo(likeIdea2);
        likeIdea1.setId(null);
        assertThat(likeIdea1).isNotEqualTo(likeIdea2);
    }
}
