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
package com.autentia.web.rest.wadl.zipper;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class WadlZipper {

    static final String DEFAULT_WADL_FILENAME = "wadl.xml";
    static final String DEFAULT_SCHEMA_EXTENSION = ".xsd";
    private final URI wadlUri;

    public WadlZipper(String wadlUri) throws URISyntaxException {
        this.wadlUri = new URI(wadlUri);
    }

    public void saveTo(String zipPathName) throws IOException, URISyntaxException {
        saveTo(new File(zipPathName));
    }

    public void saveTo(File zipFile) throws IOException, URISyntaxException {
        final HttpClient httpClient = new HttpClient();
        final Zip zip = new Zip(zipFile);
        saveTo(httpClient, zip);
    }

    /**
     * Just for easily inject dependencies on tests.
     */
    void saveTo(HttpClient httpClient, Zip zip) throws IOException, URISyntaxException {
        try {
            final String wadlContent = httpClient.getAsString(wadlUri);
            zip.add(DEFAULT_WADL_FILENAME, IOUtils.toInputStream(wadlContent));

            for (String grammarUri : new GrammarsUrisExtractor().extractFrom(wadlContent)) {
                final URI uri = new URI(grammarUri);
                final String name = composesGrammarFileNameWith(uri);
                final InputStream inputStream = httpClient.getAsStream(wadlUri.resolve(uri));
                zip.add(name, inputStream);
            }

        } finally {
            zip.close();
        }
    }

    private String composesGrammarFileNameWith(URI grammarUri) {
        String pathName = "";

        final String host = grammarUri.getHost();
        if (host != null) {
            pathName = host + '/';
        }

        String uriPath = grammarUri.getPath();
        if (uriPath.startsWith("/")) {
            uriPath = uriPath.substring(1);
        }
        pathName += uriPath;

        if (!pathName.toLowerCase().endsWith(DEFAULT_SCHEMA_EXTENSION)) {
            pathName += DEFAULT_SCHEMA_EXTENSION;
        }
        return pathName;
    }

    /**
     * 
     * <pre>
     * com.authentia.maven.plugin.WadlZipperMojo  -----maven plugin
     
        package com.autentia.maven.plugin;
    
        import com.autentia.web.rest.wadl.zipper.WadlZipper;
        import org.apache.maven.plugin.AbstractMojo;
        import org.apache.maven.plugins.annotations.Mojo;
        import org.apache.maven.plugin.MojoExecutionException;
        import org.apache.maven.plugin.MojoFailureException;
        import org.apache.maven.plugins.annotations.Parameter;
        
        import java.io.IOException;
        import java.net.URISyntaxException;
        &#64;Mojo(name = "zip")
        public class WadlZipperMojo extends AbstractMojo {
        
        &#64;Parameter(required = true)
        private String wadlUri;
        
        &#64;Parameter(defaultValue = "${project.build.directory}/wadl.zip")
        private String zipFile;
        
        &#64;Override
        public void execute() throws MojoExecutionException, MojoFailureException {
            getLog().info("Extracting WADL from: " + wadlUri + ", to zip file: " + zipFile);
        
            try {
                final WadlZipper wadlZipper = new WadlZipper(wadlUri);
                wadlZipper.saveTo(zipFile);
        
            } catch (URISyntaxException e) {
                throw new MojoFailureException("WADL URI appears not be valid: " + wadlUri, e);
        
            } catch (IOException e) {
                throw new MojoFailureException("Cannot write WADL zip file: " + zipFile, e);
            }
        }
    
    }
     * 
     * 
     * 
     *  
 &lt;plugin&gt;
    &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
    &lt;artifactId&gt;maven-plugin-plugin&lt;/artifactId&gt;
    &lt;version&gt;3.2&lt;/version&gt;
    &lt;configuration&gt;
        &lt;!-- see http://jira.codehaus.org/browse/MNG-5346 --&gt;
        &lt;skipErrorNoDescriptorsFound&gt;true&lt;/skipErrorNoDescriptorsFound&gt;
    &lt;/configuration&gt;
    &lt;executions&gt;
        &lt;execution&gt;
            &lt;id&gt;mojo-descriptor&lt;/id&gt;
            &lt;goals&gt;
                &lt;goal&gt;descriptor&lt;/goal&gt;
            &lt;/goals&gt;
        &lt;/execution&gt;
        &lt;execution&gt;
            &lt;id&gt;help-goal&lt;/id&gt;
            &lt;goals&gt;
                &lt;goal&gt;helpmojo&lt;/goal&gt;
            &lt;/goals&gt;
        &lt;/execution&gt;
    &lt;/executions&gt;
&lt;/plugin&gt;

            
     * Usage:
     *            
&lt;plugin&gt;
    &lt;groupId&gt;com.autentia.web.rest&lt;/groupId&gt;
    &lt;artifactId&gt;wadl-zipper-maven-plugin&lt;/artifactId&gt;
    &lt;version&gt;1.1-SNAPSHOT&lt;/version&gt;
    &lt;configuration&gt;
        &lt;wadlUri&gt;http://localhost:8080/${project.artifactId}/rest/wadl&lt;/wadlUri&gt;
        &lt;zipFile&gt;${project.build.directory}/wadl.zip&lt;/&gt;
    &lt;/configuration&gt;
    &lt;executions&gt;
        &lt;execution&gt;
            &lt;id&gt;wadl-zip&lt;/id&gt;
            &lt;phase&gt;integration-test&lt;/phase&gt;
            &lt;goals&gt;
                &lt;goal&gt;zip&lt;/goal&gt;
            &lt;/goals&gt;
        &lt;/execution&gt;
    &lt;/executions&gt;
&lt;/plugin&gt;
     * 
     * </pre>
     */
    public static void main(String[] args) {
//        String wadlUri = "http://localhost:8080/${project.artifactId}/rest/wadl";
        String wadlUri="http://localhost:8080/myapp/application.wadl";
        String zipFile = "spring_wadl.zip";
        WadlZipper wadlZipper;
        try {
            wadlZipper = new WadlZipper(wadlUri);
            wadlZipper.saveTo(zipFile);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
