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
package com.autentia.web.rest.wadl.builder;

import com.autentia.lang.ClassMetadataFromReturnType;
import com.autentia.web.rest.wadl.builder.namespace.GrammarsDiscoverer;
import net.java.dev.wadl._2009._02.Representation;
import org.springframework.http.MediaType;

import javax.xml.namespace.QName;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import static com.autentia.lang.ClassUtils.isVoid;

class RepresentationBuilder {

    Collection<Representation> build(MethodContext ctx) {
        final Collection<Representation> representations = new ArrayList<Representation>();
        final Method javaMethod = ctx.getJavaMethod();
        final GrammarsDiscoverer grammarsDiscoverer = ctx.getParentContext().getGrammarsDiscoverer();

        for (MediaType mediaType : ctx.getMediaTypes()) {
            final Class<?> returnType = javaMethod.getReturnType();
            if (isVoid(returnType)) {
                continue;
            }

            final QName qName = grammarsDiscoverer.discoverQNameFor(new ClassMetadataFromReturnType(javaMethod));
            representations.add(new Representation().withMediaType(mediaType.toString()).withElement(qName));
        }
        return representations;
    }
}
