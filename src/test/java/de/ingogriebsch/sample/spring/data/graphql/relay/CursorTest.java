package de.ingogriebsch.sample.spring.data.graphql.relay;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CursorTest {

    @Test
    void encode_should_return_encoded_string_that_contains_all_parts_of_the_cursor() {
        Cursor cursor = Cursor.of("id", 10L);
        String encoded = cursor.encode();

        assertThat(Cursor.decode(encoded)).isEqualTo(cursor);
    }
}
