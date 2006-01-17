<%@ page import="net.indrix.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>

<% User user = (User)session.getAttribute("user"); %>

<c:if test="${!empty param.menuLevel1}">
	<c:set var="menu" scope="session" value="${param.menuLevel1}"/>
</c:if>

      <table width="100%" border="0" cellspacing="5" align="center" vspace="25">
				<c:import url="/jsp/menu/menuCommon.jsp"/>

				<c:import url="/jsp/menu/photo/menuPhoto.jsp"/>


				<c:import url="/jsp/menu/sound/menuSound.jsp"/>
	
        <tr bgcolor="#005599">
        	<td></td>
        </tr>
      </table>
      