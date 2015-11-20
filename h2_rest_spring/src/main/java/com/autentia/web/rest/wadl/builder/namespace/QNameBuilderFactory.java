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
package com.autentia.web.rest.wadl.builder.namespace;

import com.autentia.xml.namespace.QNameBuilder;
import com.autentia.xml.namespace.QNameMemory;
import com.autentia.xml.namespace.QNamePrefixesCache;
import com.autentia.xml.namespace.QNamesCache;
import com.autentia.xml.namespace.cache.InMemoryQNamePrefixesCache;
import com.autentia.xml.namespace.cache.InMemoryQNamesCache;

import static com.autentia.web.rest.wadl.builder.namespace.QNameConstants.BASIC_COLLECTION_TYPES;
import static com.autentia.web.rest.wadl.builder.namespace.QNameConstants.BASIC_SINGLE_TYPES;

public class QNameBuilderFactory {

    public QNameBuilder getBuilder() {
        return new QNameBuilder(
                new QNameMemory(
                        initCacheForSingleTypes(),
                        initCacheForCollectionTypes(),
                        initCacheForAlreadyUsedPrefixes()));
    }

    private QNamesCache initCacheForSingleTypes() {
        return new InMemoryQNamesCache(BASIC_SINGLE_TYPES);
    }

    private QNamesCache initCacheForCollectionTypes() {
        return new InMemoryQNamesCache(BASIC_COLLECTION_TYPES);
    }

    private QNamePrefixesCache initCacheForAlreadyUsedPrefixes() {
        final QNamePrefixesCache cache = new InMemoryQNamePrefixesCache();
        cache.add("xs");
        cache.add("wadl");
        cache.add("tc");

        return cache;
    }
}
