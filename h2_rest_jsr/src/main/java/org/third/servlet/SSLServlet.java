package org.third.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.X509Certificate;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class SSLServlet extends HttpServlet
{

    public SSLServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        X509Certificate certs[] = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        if(certs != null)
        {
            int count = certs.length;
            out.println((new StringBuilder("Found [")).append(count).append("] certficates").toString());
            for(int i = 0; i < count; i++)
            {
                out.println((new StringBuilder("Client Certifcate [")).append(++i).append("]: ").toString());
                out.println((new StringBuilder("Verification Result: ")).append(verifyCertificate(certs[--i])).toString());
                out.println((new StringBuilder("Certifcate Details: \r\n ")).append(certs[i].toString()).toString());
            }

        } else
        if("https".equalsIgnoreCase(request.getScheme()))
            out.println("\u8FD9\u662F\u4E00\u4E2AHTTPS\u8BF7\u6C42\uFF0C\u4F46\u662F\u6CA1\u6709\u53EF\u7528\u7684\u5BA2\u6237\u7AEF\u8BC1\u4E66");
        else
            out.println("\u8FD9\u4E0D\u662F\u4E00\u4E2AHTTPS\u8BF7\u6C42\uFF0C\u56E0\u6B64\u65E0\u6CD5\u83B7\u5F97\u5BA2\u6237\u7AEF\u8BC1\u4E66\u5217\u8868 ");
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doGet(request, response);
    }

    private boolean verifyCertificate(X509Certificate certificate)
    {
        boolean valid = true;
        try
        {
            certificate.checkValidity();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    private static final long serialVersionUID = 1601507150278487538L;
    private static final String ATTR_CER = "javax.servlet.request.X509Certificate";
    private static final String CONTENT_TYPE = "text/plain;charset=UTF-8";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String SCHEME_HTTPS = "https";
}
