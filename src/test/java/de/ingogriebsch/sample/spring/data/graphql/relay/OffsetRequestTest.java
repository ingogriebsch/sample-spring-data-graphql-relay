package de.ingogriebsch.sample.spring.data.graphql.relay;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OffsetRequestTest {

    @Nested
    class OfTest {

        @Test
        void should_fail_if_offset_is_not_legal() {
            assertThatThrownBy(() -> OffsetRequest.of(-1L, 20)) //
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void should_fail_if_size_is_not_legal() {
            assertThatThrownBy(() -> OffsetRequest.of(10L, -1)) //
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void should_return_matching_pageable() {
            assertThat(OffsetRequest.of(10L, 20)) //
                .hasFieldOrPropertyWithValue("offset", 10L) //
                .hasFieldOrPropertyWithValue("size", 20);
        }
    }

    @Nested
    class PageNumberTest {

        @CsvSource(value = { //
            "0,1,0", "1,1,1", "2,1,2", "3,1,3", //
            "0,20,0", "5,20,1", "1,20,1", "10,20,1", //
            "11,20,1", "19,20,1", "20,20,1", "21,20,2", //
            "25,20,2" //
        })
        @DisplayName("The page-number should be")
        @ParameterizedTest(name = "{2} if offset is {0} and size is {1}")
        void should_return_matching_page_number(Long offset, Integer size, Integer pageNumber) {
            assertThat(OffsetRequest.of(offset, size).getPageNumber()).isEqualTo(pageNumber);
        }
    }

    @Nested
    class PreviousTest {

        @Test
        void should_return_matching_pageable() {
            assertThat(OffsetRequest.of(50L, 20).previous()) //
                .isEqualTo(OffsetRequest.of(30L, 20));
        }

        @Test
        void should_return_matching_pageable_if_size_is_greater_than_offset() {
            assertThat(OffsetRequest.of(10L, 20).previous()) //
                .isEqualTo(OffsetRequest.of(0L, 20));
        }
    }

    @Nested
    class NextTest {

        @Test
        void should_return_matching_pageable() {
            assertThat(OffsetRequest.of(10L, 20).next()) //
                .isEqualTo(OffsetRequest.of(30L, 20));
        }
    }

    @Nested
    class FirstTest {

        @Test
        void should_return_matching_pageable() {
            assertThat(OffsetRequest.of(10L, 20).first()) //
                .isEqualTo(OffsetRequest.of(0L, 20));
        }
    }
}
