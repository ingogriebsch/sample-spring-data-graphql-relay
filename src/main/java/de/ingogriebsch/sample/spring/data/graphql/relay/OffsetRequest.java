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

import static java.lang.Math.max;

import static lombok.AccessLevel.PRIVATE;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * A {@link Pageable} implementation that allows to request a {@link Page} based on an offset and a size.
 */
@RequiredArgsConstructor(access = PRIVATE)
@Value
class OffsetRequest implements Pageable {

    Long offset;
    Integer size;
    Sort sort;

    static OffsetRequest of(Long offset, Integer size) {
        return of(offset, size, Sort.unsorted());
    }

    static OffsetRequest of(@NonNull Long offset, @NonNull Integer size, @NonNull Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Property 'offset' must be greater than or equal to 0");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Property 'size' must be greater than or equal to 1");
        }
        return new OffsetRequest(offset, size, sort);
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / size) + (offset % size > 0 ? 1 : 0);
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public Pageable previous() {
        return hasPrevious() ? OffsetRequest.of(max(getOffset() - getPageSize(), 0), getPageSize()) : this;
    }

    @Override
    public Pageable next() {
        return OffsetRequest.of(getOffset() + getPageSize(), getPageSize());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return OffsetRequest.of(0L, getPageSize());
    }

    @Override
    public boolean hasPrevious() {
        return offset > 0;
    }
}
