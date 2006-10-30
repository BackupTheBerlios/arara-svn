<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>

<c:set var="col1" value="${5}" />
<c:set var="col2" value="${20}"/>
<c:set var="col3" value="${75}"/>

<br><br>
<form method="post" action="<c:url value="/servlet/update"/>">
<table align="center" class="formBorder" width="95%" border="0">
	<tr height="10" bgcolor="#000000">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.login" />
		</td>
		<td width="${col3}%">
			<input disabled type="text" name="login" size="16" maxlength="16" value="${user.login}">
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.name" />
		</td>
		<td width="${col3}%">
			<input type="text" name="name" size="64" maxlength="64" value="${user.name}">
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.email" />
		</td>
		<td width="${col3}%">
			<input type="text" name="email" size="64" maxlength="64" value="${user.email}">
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.language.label" />
		</td>
		<td width="${col3}%"><select name="language">
			<c:if test="${user.language == 'pt'}">
				<option selected value="pt"><fmt:message key="user.language.pt" /></option>
				<option value="en"><fmt:message key="user.language.en" /></option>
			</c:if>
			<c:if test="${user.language == 'en'}">
				<option value="pt"><fmt:message key="user.language.pt" /></option>
				<option selected value="en"><fmt:message key="user.language.en" /></option>
			</c:if>
		</select></td>
	</tr>

	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td width="${col3}%">
			<c:if test="${user.emailOnNewPhoto == true}">
				<input type="checkbox" checked name="emailOnNewPhoto" size="16"	maxlength="16">
				<fmt:message key="user.emailOnNewPhoto" />
			</c:if> 
			<c:if test="${user.emailOnNewPhoto == false}">
				<input type="checkbox" name="emailOnNewPhoto" size="16" maxlength="16">
				<fmt:message key="user.emailOnNewPhoto" />
			</c:if>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td width="${col3}%">
			<c:if test="${user.emailOnNewIdPhoto == true}">
				<input type="checkbox" checked name="emailOnNewIdPhoto" size="16"
					maxlength="16">
				<fmt:message key="user.emailOnNewIdPhoto" />
			</c:if> <c:if test="${user.emailOnNewIdPhoto == false}">
				<input type="checkbox" name="emailOnNewIdPhoto" size="16"
					maxlength="16">
				<fmt:message key="user.emailOnNewIdPhoto" />
			</c:if>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td width="${col3}%">
			<c:if test="${user.emailOnNewSound == true}">
				<input type="checkbox" checked name="emailOnNewSound" size="16"	maxlength="16">
				<fmt:message key="user.emailOnNewSound" />
			</c:if> <c:if test="${user.emailOnNewSound == false}">
				<input type="checkbox" name="emailOnNewSound" size="16" maxlength="16">
				<fmt:message key="user.emailOnNewSound" />
			</c:if>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td>
			<div align="left">
				<input type="SUBMIT" value="<fmt:message key="user.update.submit"/>">
			</div>
		</td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
</table>
<p>&nbsp;</p>
</form>
