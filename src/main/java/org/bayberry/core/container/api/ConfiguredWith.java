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
package org.bayberry.core.container.api;

import com.google.inject.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify modules used to create injector. By default,
 * the module class will be sinleton between tests. You
 * can override the euqals and hashcode to change this
 * behavior. But this way is kinda of hacky because the
 * hashcode in java is supposed to be static. So, you
 * should use @Provides in this case. For example:
 * <pre>
 * <b>@Provides</b>
 * Module myModule() {
 * &nbsp;&nbsp;return new AbstractModule() {
 * &nbsp;&nbsp;...
 * &nbsp;&nbsp; };
 * }
 * </pre>
 *
 * @author taowen
 * @see org.bayberry.core.container.api.OverriddenBy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfiguredWith {
    Class<? extends Module>[] value();
}
