<%@ taglib uri="/core" prefix="c"%>

<script language="javascript">
function changeState(status){
	alert(status);
	document.maintenanceForm.maintenance = status;
	document.maintenanceForm.submit();
	return true;	
}

</script>

<c:set var="col1" value="${10}%"/>
<c:set var="col2" value="${15}%"/>
<c:set var="col3" value="${75}%"/>

<%@ page import="net.indrix.arara.vo.*"%>
<%@ page import="net.indrix.arara.model.*"%>

<% 
	request.setAttribute("valid", false);
	String maintenanceStr = request.getParameter("maintenance");
	
	Boolean status;
	if (maintenanceStr == null){
		if (application.getAttribute("maintenance") != null){
			status = (Boolean)application.getAttribute("maintenance");
		} else {
			status = false;
			application.setAttribute("maintenance", status);
		}
	} else {
		status = new Boolean(maintenanceStr);
	}
	
	String password = request.getParameter("password");
	if (password != null && password.trim().length() > 0){
		UserModel model = new UserModel();
		try {
			User user = model.login("jefferson", password);
			if (user != null){
				application.setAttribute("maintenance", status);
				request.setAttribute("valid", true);
			}
		} catch(Exception e){
		}
	} 
%>

<form name="maintenanceForm" method="post" action="<c:url value="/jsp/maintenance/doMaintenance.jsp"/>">

<br><br>
<table align="center" class="formBorder" width="80%" border="0" cellspacing="2">
	<input type=hidden name="maintenance" value="${!maintenance}">
	<tr height="10" bgcolor="#000000">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}">
			Em manutenção
		</td>
		<td align="left" width="${col3}">
			${maintenance}
		</td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}">
			Password
		</td>
		<td align="left" width="${col3}">
			<input type="password" name="password" value="">
		</td>
	</tr>
	<tr align="center">
		<td width="${col1}">
		</td>
		<td align="left" width="${col2}">
		</td>
		<td align="left" width="${col3}">
			<c:if test="${maintenance == true}">
				<input type="SUBMIT" value="Sair da manutenção" onSubmit="changeState(false)">
			</c:if>
			<c:if test="${maintenance == false}">
				<input type="SUBMIT" value="Entrar em manutenção" onSubmit="changeState(true)">
			</c:if>
		</td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<c:if test="${valid == false}">
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}">
		</td>
		<td align="left" width="${col3}">
			Senha inválida
		</td>
	</tr>
	</c:if>
</table>

<p>&nbsp;</p>
</form>
