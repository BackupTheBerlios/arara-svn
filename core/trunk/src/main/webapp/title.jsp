<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>

<% User user = (User)session.getAttribute("user"); %>

<table width="100%" border="0" cellspacing="0" height="100" bgcolor="#669900">
	<tr bgcolor="#669900">
		<td height="25">
			<div align="left"><font color="#FFFF00"
			face="Georgia, Times New Roman, Times, serif" size="+1"> <fmt:message
			key="site.title" /> </font>
			</div>
		</td>
		<td height="25" width="200">
		<font size="-1" face="Verdana"><b>		
		<c:choose>		
		<c:when test='<%= user == null %>'>
		 	  <fmt:message key="user.comein" />		
		</c:when>
		<c:otherwise>
		 	  <fmt:message key="user.welcome" /> &nbsp; <%= user.getName()%>	
		</c:otherwise>
		</c:choose>		
		</b></font>	
		</td>	 	  
		<td height="25" width="100">	 	  		
		  <div class="outerbar">
		  <div class="dojo-FisheyeList"
				dojo:itemWidth="30" dojo:itemHeight="30"
				dojo:itemMaxWidth="200" dojo:itemMaxHeight="200"
				dojo:orientation="horizontal"
				dojo:effectUnits="2"
				dojo:itemPadding="10"
				dojo:attachEdge="top"
				dojo:labelEdge="bottom"
				dojo:enableCrappySvgSupport="false">
				
			<div class="dojo-FisheyeListItem" onClick="load_app('/index.jsp');" 
				dojo:iconsrc="<%= request.getContextPath()%>/images/home.png" caption="<fmt:message key="menu.common.home" />">
			</div>

		<c:choose>
		<c:when test='<%= user == null %>'>
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/login.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/login.png" caption="<fmt:message key="menu.common.login" />">
			</div>
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/register.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/users.png" caption="<fmt:message key="menu.common.register" />">			
			</div>
		</c:when>
		<c:otherwise>
			<div class="dojo-FisheyeListItem" onClick="load_app('/servlet/logout');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/logout.png" caption="<fmt:message key="menu.common.logout" />">
			</div>
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/updateUser.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/edit.png" caption="<fmt:message key="menu.common.updateUser" />">			
			</div>			
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/changePassword.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/key.png" caption="<fmt:message key="menu.common.changePassword" />">			
			</div>		
		</c:otherwise>
		</c:choose>			
			
		  </div>
		  </div>
		</td>		
	</tr>
	<tr bgcolor="#669900">	
		<td colspan="3" height="75">
			<hr>
			<img src="<%= request.getContextPath()%>/images/top.jpg" alt="">
		</td>
	</tr>
		
</table>
