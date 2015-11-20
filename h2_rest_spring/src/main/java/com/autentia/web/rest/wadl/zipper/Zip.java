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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class Zip {

    private final ZipOutputStream zipOutputStream;

    Zip(OutputStream outputStream) {
        zipOutputStream = new ZipOutputStream(outputStream);
    }

    Zip(File outputFile) throws FileNotFoundException {
        this(new FileOutputStream(outputFile));
    }

    public void add(String name, InputStream inputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(name));

        IOUtils.copy(inputStream, zipOutputStream);

        zipOutputStream.closeEntry();
    }

    public void close() {
        IOUtils.closeQuietly(zipOutputStream);
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
