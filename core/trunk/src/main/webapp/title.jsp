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
		  <div class="outerbar">
		  <div class="dojo-FisheyeList"
				dojo:itemWidth="30" 
				dojo:itemHeight="30"
				dojo:itemMaxWidth="200" 
				dojo:itemMaxHeight="200"
				dojo:orientation="horizontal"
				dojo:effectUnits="2"
				dojo:itemPadding="10"
				dojo:attachEdge="top"
				dojo:labelEdge="bottom"
				dojo:enableCrappySvgSupport="false">
				
			<div class="dojo-FisheyeListItem" onClick="load_app('index.jsp');" 
				dojo:iconsrc="<c:url value="/images/home.png"/>" caption="<fmt:message key="menu.common.home" />">
			</div>

			<c:if test="${user == null}">
				<div class="dojo-FisheyeListItem" onClick="load_app('frame.jsp?pageToShow=/jsp/user/doLogin.jsp');"
					dojo:iconsrc="<c:url value="/images/login.png"/>" caption="<fmt:message key="menu.common.login" />">
				</div>
				<div class="dojo-FisheyeListItem" onClick="load_app('frame.jsp?pageToShow=/jsp/user/doRegister.jsp');"
					dojo:iconsrc="<c:url value="/images/users.png"/>" caption="<fmt:message key="menu.common.register" />">			
				</div>
			</c:if>
			<c:if test="${user != null}">
				<div class="dojo-FisheyeListItem" onClick="load_app('servlet/logout');"
					dojo:iconsrc="<c:url value="/images/logout.png"/>" caption="<fmt:message key="menu.common.logout" />">
				</div>
				<div class="dojo-FisheyeListItem" onClick="load_app('frame.jsp?pageToShow=/jsp/user/doUpdateUser.jsp');"
					dojo:iconsrc="<c:url value="/images/edit.png"/>" caption="<fmt:message key="menu.common.updateUser" />">			
				</div>			
				<div class="dojo-FisheyeListItem" onClick="load_app('frame.jsp?pageToShow=/jsp/user/doChangePassword.jsp');"
					dojo:iconsrc="<c:url value="/images/key.png"/>" caption="<fmt:message key="menu.common.changePassword" />">			
				</div>		
			</c:if>
			
		  </div>
		  </div>
<!--

			<a href="<c:url value="/index.jsp"/>">
				<img border="0" src="<c:url value="/images/home.png"/>" title="<fmt:message key="menu.common.home" />"/>
			</a>

			<c:if test="${user == null}">
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doLogin.jsp"/>">
					<img border="0" src="<c:url value="/images/login.png"/>" title="<fmt:message key="menu.common.login" />"/>
				</a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doRegister.jsp"/>">
					<img border="0" src="<c:url value="/images/users.png"/>" title="<fmt:message key="menu.common.register" />"/>
				</a>
			</c:if>
			<c:if test="${user != null}">
				<a href="<c:url value="/servlet/logout"/>">
					<img border="0" src="<c:url value="/images/logout.png"/>" title="<fmt:message key="menu.common.logout" />"/>
				</a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doUpdateUser.jsp"/>">
					<img border="0" src="<c:url value="/images/edit.png"/>" title="<fmt:message key="menu.common.updateUser" />"/>
				</a>
				<a href="<c:url value="/frame.jsp?pageToShow=/jsp/user/doChangePassword.jsp"/>">
					<img border="0" src="<c:url value="/images/key.png"/>" title="<fmt:message key="menu.common.changePassword"/>"/>
				</a>
			</c:if>
-->

		  
		</td>		
	</tr>
	<tr bgcolor="${mainBgColor}">	
		<td align="left" colspan="3" height="10" bgcolor="#333300">
<!-- 			<img width="100%" src="<c:url value="/images/top.jpg"/>"> -->
		</td>
	</tr>
</table>
