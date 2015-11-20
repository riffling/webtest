<%@page import="java.util.Enumeration, java.util.Iterator"%>
<html>

<body>
  <a href="forward.jsp">forward.jsp</a>
	<pre>
<h2>Cookie Info:</h2>
	<%
	    for (Cookie c : request.getCookies()) {
	        out.println("\t" + c.getDomain() + ",	" + c.getName() + "=" + c.getValue());
	    }
	%>
<h2>Request Info:</h2>
<%
    out.println("Attribute Info:");
    for (Enumeration<String> attrNames = request.getAttributeNames(); attrNames.hasMoreElements();) {
        String attrName = attrNames.nextElement();
        out.println("\t" + attrName + "=" + request.getAttribute(attrName));
    }
    out.println("Header Info:");
    for (Enumeration<String> attrNames = request.getHeaderNames(); attrNames.hasMoreElements();) {
        String attrName = attrNames.nextElement();
        out.println("\t" + attrName + "=" + request.getHeader(attrName));
    }
%>
<h2>Response Info:</h2>
<%
    out.println("Basic Info:");
    out.println("\t"+response.getStatus()+"  " + response.getBufferSize() + "=" + response.getCharacterEncoding() + ","
            + response.getContentType() + "," + response.getLocale());
    out.println("Header Info:");
    for (Iterator<String> attrNames = response.getHeaderNames().iterator(); attrNames.hasNext();) {
        String attrName = attrNames.next();
        out.println("\t" + attrName + "=" + request.getHeader(attrName));
    }
%>
<h2>Session Info:</h2>
<%
    out.println("Basic Info:");
    out.println("\t" + session.getId() + ",getCreationTime=" + session.getCreationTime()
            + ",getLastAccessedTime=" + session.getLastAccessedTime() + ",getMaxInactiveInterval="
            + session.getMaxInactiveInterval() + "," + response.getLocale());
    out.println("Attribute Info:");
    for (Enumeration<String> attrNames = session.getAttributeNames(); attrNames.hasMoreElements();) {
        String attrName = attrNames.nextElement();
        out.println(attrName + "=" + session.getAttribute(attrName));
    }
%>
</pre>  
</body>
</html>