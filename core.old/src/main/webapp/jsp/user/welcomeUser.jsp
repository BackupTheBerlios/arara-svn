<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java"%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<table width="100%" border="0" cellspacing="2" height="50%" bgcolor="#99CCFF">
  <tr bgcolor="#0000FF"> 
    <td colspan="2" height="68"> 
    	<jsp:include page="/title.jsp"/>
    </td>
  </tr>
  <tr> 
    <td width="16%" align="left" valign="top" bgcolor="#339966"> 
	    <jsp:include page="/menu.jsp"/>
    </td>
    <td width="84%"> 
    	<jsp:include page="/jsp/user/doWelcome.jsp"/>
    </td>
  </tr>
  <tr bgcolor="#0000FF"> 
    <td colspan="2">
	    <jsp:include page="/footer.jsp"/>
    </td>
  </tr>
</table>
</body>
</html>

