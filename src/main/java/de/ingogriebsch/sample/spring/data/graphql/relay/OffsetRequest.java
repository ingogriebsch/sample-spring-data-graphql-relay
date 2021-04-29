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

import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * A {@link Pageable} implementation that allows to request a {@link Page} based on an offset and a size.
 */
@Value(staticConstructor = "of")
class OffsetRequest implements Pageable {

    Long offset;
    Integer size;
    Sort sort;

    static OffsetRequest of(Long offset, Integer size) {
        return of(offset, size, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        // FIXME how to do it right?
        return (int) (offset / size + offset % size);
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

    @Override
    public Pageable next() {
        // FIXME implement me... :)
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // FIXME implement me... :)
        return null;
    }

    @Override
    public Pageable first() {
        // FIXME implement me... :)
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset > 0;
    }

}
