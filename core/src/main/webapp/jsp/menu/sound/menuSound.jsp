<%@ taglib uri="/core" prefix="c"%>
        <tr>
        	<td><font size="2" face="Verdana">
        			<a href="<c:url value="/index.jsp?menuLevel1=2"/>">Sons</a>
        	</font></td>
        </tr>
				<c:if test="${menu == '2'}">
					<c:import url="/jsp/menu/sound/menuViewSounds.jsp"/>
				</c:if>
