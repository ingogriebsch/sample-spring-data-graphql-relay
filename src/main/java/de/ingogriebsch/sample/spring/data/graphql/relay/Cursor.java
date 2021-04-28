/*-
 * Copyright 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ingogriebsch.sample.spring.data.graphql.relay;

import static java.lang.Long.valueOf;
import static java.util.Base64.getEncoder;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.Base64;

import lombok.Value;

/**
 * A basic implementation of our interpretation of a cursor.
 */
@Value(staticConstructor = "of")
class Cursor {

    private static final String SEPARATOR = ":";

    String id;
    Long offset;

    String encode() {
        return getEncoder().encodeToString(new StringBuilder(id).append(SEPARATOR).append(offset).toString().getBytes());
    }

    static Cursor decode(String source) {
        String decoded = new String(Base64.getDecoder().decode(source));
        return Cursor.of(substringBefore(decoded, SEPARATOR), valueOf(substringAfter(decoded, SEPARATOR)));
    }
}
