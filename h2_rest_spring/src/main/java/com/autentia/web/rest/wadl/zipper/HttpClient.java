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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpClient {

//    public String getAsString(URI uri) throws IOException {
//        return org.apache.http.client.fluent.Request.Get(uri).execute().returnContent().asString();
//    }
//
//    public InputStream getAsStream(URI uri) throws IOException {
//        return org.apache.http.client.fluent.Request.Get(uri).execute().returnContent().asStream();
//    }
    
    public String getAsString(URI uri) throws IOException {
        org.apache.http.impl.client.CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(uri);  
        // 执行get请求.    
        CloseableHttpResponse response = httpclient.execute(httpget);  
        try {  
            // 获取响应实体    
            HttpEntity entity = response.getEntity();  
            // 打印响应状态    
            System.out.println(response.getStatusLine());  
            if (entity != null) {  
                // 打印响应内容长度    
                System.out.println("Response content length: " + entity.getContentLength());  
                System.out.println("Response content: " + EntityUtils.toString(entity));
                return EntityUtils.toString(entity);
            }  
            return "";
//            System.out.println("------------------------------------");  
        } finally {  
            response.close();  
        }  

//        return Request.Get(uri).execute().returnContent().asString();
    }

    public InputStream getAsStream(URI uri) throws IOException {
        org.apache.http.impl.client.CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(uri);  
        // 执行get请求.    
        CloseableHttpResponse response = httpclient.execute(httpget);  
        try {  
            // 获取响应实体    
            HttpEntity entity = response.getEntity();  
            // 打印响应状态    
            System.out.println(response.getStatusLine());  
            if (entity != null) {  
                // 打印响应内容长度    
                System.out.println("Response content length: " + entity.getContentLength());  
                System.out.println("Response content: " + EntityUtils.toString(entity));
                return entity.getContent();
            }  
            return null;
//            System.out.println("------------------------------------");  
        } finally {  
            response.close();  
        }  

//        return Request.Get(uri).execute().returnContent().asString();
//        return Request.Get(uri).execute().returnContent().asStream();
    }
}
