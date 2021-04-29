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

import static graphql.com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import graphql.kickstart.tools.TypeDefinitionFactory;
import graphql.language.Definition;
import graphql.language.FieldDefinition;
import graphql.language.ListType;
import graphql.language.ObjectTypeDefinition;
import graphql.language.TypeName;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author Ingo Griebsch
 */
@Component
class RelayConnectionFactory implements TypeDefinitionFactory {

    @Override
    public List<Definition<?>> create(List<Definition<?>> definitions) {
        List<Definition<?>> result = newArrayList();

        for (Definition<?> definition : definitions) {
            ObjectTypeDefinition connection = definition(definition, s -> s.endsWith("Connection"));
            if (connection == null) {
                result.add(definition);
                continue;
            }

            String nodeType = nodeType(edge(definitions, connection.getName()));
            result.add(extend(connection, nodeType));
        }

        return result;
    }

    private static ObjectTypeDefinition definition(Definition<?> definition, Predicate<String> nameMatch) {
        if (!ObjectTypeDefinition.class.isAssignableFrom(definition.getClass())) {
            return null;
        }

        ObjectTypeDefinition otd = (ObjectTypeDefinition) definition;
        return nameMatch.test(otd.getName()) ? otd : null;
    }

    private static ObjectTypeDefinition edge(List<Definition<?>> definitions, String connectionName) {
        return definitions.stream() //
            .map(d -> definition(d, s -> s.equals(connectionName + "Edge"))) //
            .filter(Objects::nonNull) //
            .findAny() //
            .orElseThrow();
    }

    private static String nodeType(ObjectTypeDefinition edge) {
        return edge.getFieldDefinitions().stream() //
            .filter(d -> "node".equals(d.getName())) //
            .map(FieldDefinition::getType) //
            .map(TypeName.class::cast) //
            .findAny().map(TypeName::getName) //
            .orElseThrow();
    }

    private static ObjectTypeDefinition extend(ObjectTypeDefinition connection, String nodeType) {
        return ObjectTypeDefinition.newObjectTypeDefinition() //
            .sourceLocation(connection.getSourceLocation()) //
            .comments(connection.getComments())//
            .name(connection.getName()) //
            .description(connection.getDescription()) //
            .directives(connection.getDirectives()) //
            .implementz(connection.getImplements()) //
            .ignoredChars(connection.getIgnoredChars()) //
            .additionalData(connection.getAdditionalData()) //
            .fieldDefinitions(connection.getFieldDefinitions()) //
            .fieldDefinition(new FieldDefinition("totalCount", new TypeName("Int"))) //
            .fieldDefinition(new FieldDefinition("nodes", new ListType(new TypeName(nodeType)))) //
            .build();
    }
}
