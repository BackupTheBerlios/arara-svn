<%@ page language="java"%>
<%@ taglib uri="/core" prefix="c"%>
<html>
<head>
<title>Aves do Brasil</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript">
function abrir_nova_janela(pagina){
    window.open(pagina,'NovaJanela','left=0,top=0,width=656,height=550');
}
</script>
</head>

<body bgcolor="#A6D2D2">
<table width="100%" border="0" cellspacing="2" height="50%"
	bgcolor="#A6D2D2">
	<tr bgcolor="#005500">
		<td colspan="2" height="10"></td>
	</tr>
	<tr>
		<td width="17%" align="left" valign="top" bgcolor="#339966"><c:import
			url="/menu.jsp">
			<c:param name="submenu" value="changePhoto" />
			<c:param name="photoId" value="${currentPhoto.id}" />
			<c:param name="userId" value="${currentPhoto.user.id}" />
			<c:param name="currentPage" value="showOnePhoto" />
		</c:import></td>
		<td width="83%"><c:import url="/jsp/photo/search/doShowOnePhoto.jsp">
		</c:import></td>
	</tr>
	<tr bgcolor="#005500">
		<td colspan="2"><jsp:include page="/footer.jsp" /></td>
	</tr>
</table>
</body>
</html>


