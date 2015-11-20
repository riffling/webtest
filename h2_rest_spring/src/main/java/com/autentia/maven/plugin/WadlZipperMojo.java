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
package com.autentia.maven.plugin;

//import com.autentia.web.rest.wadl.zipper.WadlZipper;
//import org.apache.maven.plugin.AbstractMojo;
//import org.apache.maven.plugins.annotations.Mojo;
//import org.apache.maven.plugin.MojoExecutionException;
//import org.apache.maven.plugin.MojoFailureException;
//import org.apache.maven.plugins.annotations.Parameter;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
///**
// * This plugin connects to the URI where WADL is located, then get it and all the related grammars,
// * and store all in a zip file.
// */
//@Mojo(name = "zip")
public class WadlZipperMojo {
//extends AbstractMojo {
//
//    /** URI of the WADL */
//    @Parameter(required = true)
//    private String wadlUri;
//
//    /** Path to the zip file where store all the data. By default is 'target/wadl.zip' */
//    @Parameter(defaultValue = "${project.build.directory}/wadl.zip")
//    private String zipFile;
//
//    @Override
//    public void execute() throws MojoExecutionException, MojoFailureException {
//        getLog().info("Extracting WADL from: " + wadlUri + ", to zip file: " + zipFile);
//
//        try {
//            final WadlZipper wadlZipper = new WadlZipper(wadlUri);
//            wadlZipper.saveTo(zipFile);
//
//        } catch (URISyntaxException e) {
//            throw new MojoFailureException("WADL URI appears not be valid: " + wadlUri, e);
//
//        } catch (IOException e) {
//            throw new MojoFailureException("Cannot write WADL zip file: " + zipFile, e);
//        }
//    }

}
