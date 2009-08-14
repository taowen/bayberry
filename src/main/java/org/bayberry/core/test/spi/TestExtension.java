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
package org.bayberry.core.test.spi;

import java.lang.reflect.Method;

/**
 * Used to hook into the test execution process.
 *
 * @author taowen
 */
public interface TestExtension {

    void before(Object testCase, Method testMethod) throws Throwable;

    void after(Object testCase, Method testMethod) throws Throwable;
}
