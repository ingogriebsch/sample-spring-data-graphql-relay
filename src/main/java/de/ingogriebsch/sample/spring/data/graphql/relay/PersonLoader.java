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

import static java.lang.String.valueOf;

import static graphql.com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A {@link CommandLineRunner} implementation the adds some example data to the repository during startup.
 */
@Component
@RequiredArgsConstructor
class PersonLoader implements CommandLineRunner {

    private static final String[] forenames =
        new String[] { "Max", "Paul", "Peter", "Sira", "Leonhard", "Ingo", "Steve", "Yago", "Christian" };
    private static final String[] surnames =
        new String[] { "Lopez", "Mustermann", "Poster", "Fonda", "Gabriel", "Müller", "Meyer", "Hawking" };

    private final PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Person> persons = newArrayList();
        for (int i = 0; i < 10; i++) {
            persons.add(person(valueOf(i), name(), age()));
        }
        persons.forEach(personRepository::save);
    }

    private static Person person(String id, String name, Integer age) {
        return new Person(id, name, age);
    }

    private static String name() {
        return new StringBuilder() //
            .append(forenames[nextInt(0, forenames.length)]) //
            .append(" ") //
            .append(surnames[nextInt(0, surnames.length)]) //
            .toString();
    }

    private static Integer age() {
        return nextInt(18, 100);
    }
}
