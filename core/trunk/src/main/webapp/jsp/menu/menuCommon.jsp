<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<br/>
<font size="2" face="Verdana"> <a
		href="<c:url value="/index.jsp?menuLevel1=0"/>"> <fmt:message
		key="menu.common.home" /> </a> </font>
<br/>
<font size="2" face="Verdana"> <a
		href="<c:url value="/jsp/user/login.jsp"/>"> <fmt:message
		key="menu.common.login" /> </a> </font>
<br/>
<font size="2" face="Verdana"> <a
		href="<c:url value="/jsp/user/register.jsp"/>"> <fmt:message
		key="menu.common.register" /> </a> </font>

