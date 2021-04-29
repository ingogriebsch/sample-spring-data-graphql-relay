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

import static org.apache.commons.lang3.RandomUtils.nextInt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A {@link CommandLineRunner} implementation the adds some example data to the repository during startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
class PersonLoader implements CommandLineRunner {

    private static final String[] forenames =
        new String[] { "Max", "Paul", "Peter", "Sira", "Leonhard", "Ingo", "Steve", "Yago", "Christian", "Jan", "Pablo", "Alex",
            "Alexandre", "David", "Elias", "Eloy", "Emanuele", "Fabio", "Francisco", "Frederik", "Lukas", "Mohamed" };
    private static final String[] surnames = new String[] { "Lopez", "Mustermann", "Poster", "Fonda", "Gabriel", "Müller",
        "Meyer", "Hawking", "Grünig", "Rhazi", "Videira", "Iglesias", "Castelo", "Otero", "Hermann" };

    private final PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        int count = 10;
        log.info("Loading {} persons into the database...", count);
        for (int i = 0; i < count; i++) {
            personRepository.save(person(valueOf(i), name(), age()));
        }
        log.info("Loading done!", count);
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
