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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class GrammarsUrisExtractor {

    private static final Pattern BY_GRAMMARS = Pattern.compile("grammars", Pattern.CASE_INSENSITIVE);
    private static final Pattern BY_INCLUDE = Pattern.compile("include", Pattern.CASE_INSENSITIVE);

    List<String> extractFrom(String wadl) {
        final List<String> uris = new ArrayList<String>();

        final String[] includeElements = BY_INCLUDE.split(extractGrammarsElement(wadl));
        for (int i = 1; i < includeElements.length; i++) {
            uris.add(extractIncludeUriFrom(includeElements[i]));
        }

        return uris;
    }

    private String extractGrammarsElement(String wadl) {
        final String[] grammars = BY_GRAMMARS.split(wadl);
        if (grammars.length != 3) {
            return "";
        }
        return grammars[1];
    }

    private String extractIncludeUriFrom(String includeElement) {
        final char uriDelimiter = findUriDelimiterIn(includeElement);
        final int uriIni = includeElement.indexOf(uriDelimiter) + 1;
        final int uriEnd = includeElement.lastIndexOf(uriDelimiter);
        return includeElement.substring(uriIni, uriEnd);
    }

    private char findUriDelimiterIn(String includeElement) {
        final int indexOfDoubleQuotes = indexOf(includeElement, '"');
        final int indexOfQuotes = indexOf(includeElement, '\'');

        if (indexOfQuotes < indexOfDoubleQuotes) {
            return '\'';
        }
        return '"';
    }

    private int indexOf(String source, char ch) {
        final int index = source.indexOf(ch);
        return index == -1 ? Integer.MAX_VALUE : index;
    }
}
