<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="col1" value="${10}" />
<c:set var="col2" value="${25}" />
<c:set var="col3" value="${65}" />

<br><br>
<form name="showPhotosByCommonNameForm" method="get"
	action="<c:url value="${servletToCall}"/>">

<c:import url="/jsp/common/display/hiddenFields.jsp"/>

<c:import url="/jsp/common/display/hiddenFields.jsp"/>

<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr align="center">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<br><b><fmt:message key="common.name" /></b>
		</td>
		<td align="left" width="${col3}%">
			<select name="id">
				<c:forEach items="${commonNameList}" var="cnBean">
					<option value="${cnBean.value}">${cnBean.label}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr align="center">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td align="left" width="${col3}%">
			<input name="text" type="text" size="30"/>
		</td>
	</tr>
	<tr height="10" >
		<td colspan="3"></td>
	</tr>
	<tr align="center">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td align="left" width="${col3}%">
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
		</td>
	</tr>
	<tr height="5" >
		<td colspan="3"></td>
	</tr>
</table>
