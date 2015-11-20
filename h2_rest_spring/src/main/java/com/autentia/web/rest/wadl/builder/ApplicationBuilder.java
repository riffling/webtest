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

import com.autentia.web.HttpServletRequestUtils;
import net.java.dev.wadl._2009._02.Application;
import net.java.dev.wadl._2009._02.Doc;
import net.java.dev.wadl._2009._02.Grammars;

import javax.servlet.http.HttpServletRequest;

public class ApplicationBuilder {

    private final ResourcesBuilder resourcesBuilder;
    private final IncludeBuilder includeBuilder;

    public ApplicationBuilder(ApplicationContext ctx) {
        resourcesBuilder = new ResourcesBuilder(ctx);
        includeBuilder = new IncludeBuilder(ctx.getGrammarsDiscoverer());
    }

    public Application build(HttpServletRequest request) {
        return build(HttpServletRequestUtils.getBaseUrlOf(request));
    }

    public Application build(String baseUrl) {
        return new Application()
                .withDoc(new Doc().withTitle("REST Service WADL"))
                .withResources(resourcesBuilder.build(baseUrl))
                .withGrammars(new Grammars().withInclude(includeBuilder.build()));
    }
}
