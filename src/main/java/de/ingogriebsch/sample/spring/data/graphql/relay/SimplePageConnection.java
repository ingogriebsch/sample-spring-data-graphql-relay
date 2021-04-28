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

import static java.util.Optional.empty;

import static graphql.com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A {@link Connection} implementation that transforms a {@link Page} into a {@link Connection}.
 */
@RequiredArgsConstructor
class SimplePageConnection<T> implements Connection<T> {

    @NonNull
    private final Page<T> page;
    @NonNull
    private final Pageable pageable;
    private final IdExtractor<T> idExtractor;

    SimplePageConnection(Page<T> page, Pageable pageable) {
        this(page, pageable, new IdExtractor<T>() {
        });
    }

    @Override
    public List<Edge<T>> getEdges() {
        List<T> nodes = page.getContent();
        if (nodes.isEmpty()) {
            return newArrayList();
        }

        List<Edge<T>> edges = newArrayList();
        for (int i = 0; i < nodes.size(); i++) {
            edges.add(edge(nodes.get(i), pageable.getOffset() + i));
        }
        return edges;
    }

    @Override
    public PageInfo getPageInfo() {
        List<T> nodes = page.getContent();
        if (nodes.isEmpty()) {
            return new DefaultPageInfo(null, null, false, false);
        }

        return new DefaultPageInfo( //
            cursor(nodes.get(0), pageable.getOffset()), //
            cursor(nodes.get(nodes.size() - 1), pageable.getOffset() + nodes.size() - 1), //
            page.hasPrevious(), //
            page.hasNext() //
        );
    }

    @Override
    public List<T> getNodes() {
        return page.getContent();
    }

    @Override
    public Long getTotalCount() {
        return page.getTotalElements();
    }

    private Edge<T> edge(T node, Long offset) {
        return new DefaultEdge<>(node, cursor(node, offset));
    }

    private ConnectionCursor cursor(T node, Long offset) {
        String id = idExtractor.extract(node).map(i -> i).orElse("cursor");
        return new DefaultConnectionCursor(Cursor.of(id, offset).encode());
    }

    interface IdExtractor<T> {

        default Optional<String> extract(T node) {
            return empty();
        }
    }
}
