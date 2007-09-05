<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<style type="text/css">
table.comBordaSimples {
	border-collapse: collapse; /* CSS2 */
	border: 2px solid #404040;
}

</style>
<div align="left">
<table width="100%" border="0" cellspacing="2" height="100%">
	<tr>
		<td>
			<table class="comBordaSimples"  width="100%" cellspacing="2" height="100%" border="0">
				<tr>
					<td>
						<c:import url="/jsp/main/main_en_text.jsp" /> 
					</td>
				</tr>
			</table>
		
		</td>
	</tr>
	<tr height="15">
		<td>
		</td>
	</tr>
	<tr>
		<td>
			<c:import url="/jsp/photo/search/doShowPhotosWithMoreComments.jsp" /> 
		</td>
	</tr>
	<tr height="15">
		<td>
		</td>
	</tr>
	<tr>
		<td>
			<table class="comBordaSimples"  width="100%" cellspacing="2" height="100%" border="0">
				<tr>
					<td>
						<b><fmt:message key="site.sponsors"/></b><br><br>
					</td>
				</tr>
				<tr>
					<td align="left" colspan="4">
						<a href="http://www.ecorotas.com.br"  target="_blanck">
							<img src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="bottom"/> 
						</a> 
					</td>
				</tr>
				<tr>
					<td align="right" colspan="4">
						<a href="mailto:webmaster@aves.brasil.nom.br?subject=Patrocinio"> 
							<img src="<c:url value="/jsp/images/Propaganda.jpg"/>"	align="bottom"/> 
						</a> 
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<br>
			<a href="<c:url value="/jsp/language.jsp?lang=pt"/>">Versão em Português</a>
		</td>
	</tr>
</table>
</div>