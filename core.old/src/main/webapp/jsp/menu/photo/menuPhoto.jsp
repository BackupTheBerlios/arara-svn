<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
        <tr>
        	<td><font size="2" face="Verdana">
        		<a href="<c:url value="/index.jsp?menuLevel1=1"/>">
        			<fmt:message key="menu.photos.photos"/>
        		</a>
        	</font></td>
        </tr>
		<c:if test="${menu == '1'}">
			<c:import url="/jsp/menu/photo/menuViewPhotos.jsp"/>
		</c:if>
