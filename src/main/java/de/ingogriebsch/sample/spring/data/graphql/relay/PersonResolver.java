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

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * A {@link GraphQLQueryResolver} implementation that allows to request pages of persons.
 */
@Component
@RequiredArgsConstructor
class PersonResolver implements GraphQLQueryResolver {

    private final PersonRepository personRepository;

    // must be public
    public Connection<Person> persons(@NonNull Integer first, String cursor, DataFetchingEnvironment env) {
        // if first is null and cursor is null the client wants to have all persons (not allowed)!
        // if first is not null but cursor is null the client wants to have the first x persons.
        // if cursor is not null but first the client wants to have all remaining persons from a specific position (not allowed)!
        // if first and cursor are not null the client wants to have the x persons from a specific position.

        int page;
        if (cursor != null) {
            Long offset = Cursor.decode(cursor).getOffset();
            page = (int) (offset / first + 1);
        } else {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, first);
        return new SimplePageConnection<>(personRepository.findAll(pageable), pageable);
    }
}
