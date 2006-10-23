<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>


<c:if test="${messages != null && !empty messages}">
<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<br>
<table class="formBorder" width="90%" align="center" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="2"></td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
	</tr>
	<tr>
		<td width="${col1}" align="left"></td>
    	<td width="${col2}" align="left">
			<h3><fmt:message key="messages.title" /></h3>
			<c:forEach items="${messages}" var="msg">
				<font color="#FF0000">
					<fmt:message key="${msg}" />
				</font>
				<br>
			</c:forEach>
			
		</td>
	</tr>
</table>
</c:if>



	