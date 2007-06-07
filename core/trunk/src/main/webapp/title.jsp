<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>

<table width="100%" border="0" cellspacing="0" bgcolor="${mainBgColor}">
	<tr bgcolor="${mainBgColor}">
		<td width="40%" height="25">
			<div align="left">
			<font color="#FFFF00" face="Verdana, Georgia, Times New Roman, Times, serif" size="+1"> 
				<fmt:message key="site.title" /> 
			</font>
			</div>
		</td>
		<td align="right" width="40%" height="25">
			<font size="-1" face="Verdana"><b>
				<c:if test="${user == null}">
				 	  <fmt:message key="user.comein" />		
				</c:if>
				<c:if test="${user != null}">
				 	  <fmt:message key="user.welcome" /> &nbsp; ${user.name}	
				</c:if>
			</b></font>	
		</td>	 	  
		<td width="20%" height="25">
			<a href="<c:url value="/index.jsp"/>">
				<img width="36" heigth="36" border="0" src="<c:url value="/images/home.gif"/>" title="<fmt:message key="menu.common.home" />"/></a>

			<c:if test="${user == null}">
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doLogin.jsp"/>"><img width="36" heigth="36" border="0" src="<c:url value="/images/login.gif"/>" title="<fmt:message key="menu.common.login" />"/></a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doRegister.jsp"/>"><img width="36" heigth="36" border="0" src="<c:url value="/images/users.gif"/>" title="<fmt:message key="menu.common.register" />"/></a>
			</c:if>
			<c:if test="${user != null}">
				<a href="<c:url value="/servlet/logout"/>"><img width="36" heigth="36" border="0" src="<c:url value="/images/logout.gif"/>" title="<fmt:message key="menu.common.logout" />"/></a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doUpdateUser.jsp"/>"><img width="36" heigth="36" border="0" src="<c:url value="/images/edit.gif"/>" title="<fmt:message key="menu.common.updateUser" />"/></a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doChangePassword.jsp"/>"><img width="36" heigth="36" border="0" src="<c:url value="/images/key.gif"/>" title="<fmt:message key="menu.common.changePassword"/>"/></a>
			</c:if>


		  
		</td>		
	</tr>
	<tr bgcolor="${mainBgColor}">	
		<td align="left" colspan="3" height="10" bgcolor="#333300">
		</td>
	</tr>
</table>
