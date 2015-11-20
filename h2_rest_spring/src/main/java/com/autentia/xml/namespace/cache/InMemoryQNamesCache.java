/**
 *    Copyright 2013 Autentia Real Business Solution S.L.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.autentia.xml.namespace.cache;

import com.autentia.xml.namespace.QNamesCache;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

public class InMemoryQNamesCache implements QNamesCache {

    private final Map<Class<?>, QName> cache = new HashMap<Class<?>, QName>();

    public InMemoryQNamesCache() {
        // Default constructor do nothing.
    }

    public InMemoryQNamesCache(Map<Class<?>, QName> initialValues) {
        cache.putAll(initialValues);
    }

    @Override
    public QName getQNameFor(Class<?> classType) {
        return cache.get(classType);
    }

    @Override
    public void putQNameFor(Class<?> classType, QName qName) {
        cache.put(classType, qName);
    }

}
