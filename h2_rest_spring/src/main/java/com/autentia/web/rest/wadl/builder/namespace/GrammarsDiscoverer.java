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

import com.autentia.lang.ClassMetadata;
import com.autentia.xml.schema.ClassTypeDiscoverer;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class GrammarsDiscoverer {
    public static final String getSchemaFile(String clazzType) {
        return "schema/" + clazzType + ".xsd";
    }
    private final ClassTypeDiscoverer classTypeDiscoverer;

    public GrammarsDiscoverer(ClassTypeDiscoverer classTypeDiscoverer) {
        this.classTypeDiscoverer = classTypeDiscoverer;
    }

    public QName discoverQNameFor(ClassMetadata classMetadata) {
        final QName qName = findInBasicTypesBy(classMetadata.getType());
        return qName != null ? qName : classTypeDiscoverer.discoverFor(classMetadata).getQName();
    }

    private QName findInBasicTypesBy(Class<?> clazz) {
        final QName qName = QNameConstants.BASIC_SINGLE_TYPES.get(clazz);
        return qName != null ? qName : QNameConstants.BASIC_COLLECTION_TYPES.get(clazz);
    }

    public List<String> getSchemaUrlsForComplexTypes() {
        final List<String> urls = new ArrayList<String>();
        for (String localPart : classTypeDiscoverer.getAllByLocalPart().keySet()) {
//            urls.add("schema/" + localPart);
            urls.add(getSchemaFile(localPart));
        }
        return urls;
    }

}
