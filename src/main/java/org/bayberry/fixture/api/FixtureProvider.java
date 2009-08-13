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
package org.bayberry.fixture.api;

import com.google.inject.Provider;

/**
 * Template of fixture provider which assumes the fixture is singleton
 *
 * @author taowen
 */
public abstract class FixtureProvider implements Provider<Object>, UsingFixture {

    private Object fixture;

    /**
     * create the fixture when the fixture is not cached inside
     *
     * @return the fixture created or cached
     */
    public final Object get() {
        if (fixture == null) {
            fixture = createFixture();
        }
        return fixture;
    }

    /**
     * extension for creating fixture
     *
     * @return the new fixture instance
     */
    protected abstract Object createFixture();
}
