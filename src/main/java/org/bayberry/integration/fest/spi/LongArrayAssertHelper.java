/*
 * Copyright 2009 Wen Tao. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.bayberry.integration.fest.spi;

import org.fest.assertions.LongArrayAssert;
import org.fest.assertions.Assertions;
import com.google.inject.Singleton;
import com.google.inject.ImplementedBy;

/**
 * @author taowen
 */
@ImplementedBy(LongArrayAssertHelper.Impl.class)
public interface LongArrayAssertHelper {

    LongArrayAssert assertThat(long[] actual);

    @Singleton
    public static class Impl implements LongArrayAssertHelper {

        public LongArrayAssert assertThat(long[] actual) {
            return Assertions.assertThat(actual);
        }
    }
}
