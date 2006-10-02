<%@ taglib uri="/fmt" prefix="fmt"%>

<table width="100%" border="0" cellspacing="0" height="100" bgcolor="#669900">
	<tr bgcolor="#669900">
		<td colspan="2" height="25">
			<div align="left"><font color="#FFFF00"
			face="Georgia, Times New Roman, Times, serif" size="+1"> <fmt:message
			key="site.title" /> </font>
			</div>
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
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/login.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/login.png" caption="<fmt:message key="menu.common.login" />">
			</div>
			<div class="dojo-FisheyeListItem" onClick="load_app('/jsp/user/register.jsp');"
				dojo:iconsrc="<%= request.getContextPath()%>/images/users.png" caption="<fmt:message key="menu.common.register" />">			
			</div>
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
