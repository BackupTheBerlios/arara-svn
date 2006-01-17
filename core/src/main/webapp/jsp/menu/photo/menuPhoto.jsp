<%@ taglib uri="/core" prefix="c"%>
        <tr>
        	<td><font size="2" face="Verdana">
        			<a href="<c:url value="/index.jsp?menuLevel1=1"/>">Fotos</a>
        	</font></td>
        </tr>
				<c:if test="${menu == '1'}">
					<c:import url="/jsp/menu/photo/menuViewPhotos.jsp"/>
				</c:if>
