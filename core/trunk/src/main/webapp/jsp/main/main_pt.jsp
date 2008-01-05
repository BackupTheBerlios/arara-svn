<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

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
						<c:import url="/jsp/main/main_pt_text.jsp" /> 
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="10">
		<td>
		</td>
	</tr>
	<tr>
		<td>
			<c:import url="/jsp/photo/search/doShowPhotosWithMoreComments.jsp" /> 
		</td>
	</tr>
	<tr height="10">
		<td>
		</td>
	</tr>
	<tr>
		<td>
			<c:import url="/jsp/photo/search/doShowPhotosWithMoreCommentsOfWeek.jsp" /> 
		</td>
	</tr>
	<tr height="10">
		<td>
		</td>
	</tr>
	<tr>
		<td>
			<table class="comBordaSimples"  width="100%" cellspacing="2" height="100%" border="0">
				<tr>
					<td>
						<b><fmt:message key="site.sponsors" /></b><br><br>
					</td>
				</tr>
				<tr>
					<td align="left" colspan="4">
						<a href="http://www.ecorotas.com.br"  target="_blanck">
							<img border="0" width="200" height="100" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="bottom"/> 
						</a>
						&nbsp;&nbsp; 
						<a href="http://www.itamambuca.com.br"  target="_blanck">
							<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
							codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="200"
							height="100">
							<param name="movie" value="itamambuca.swf">
							<param name="quality" value="high">
							<embed src="itamambuca.swf" quality="high"
							pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="200"
							height="100"></embed>
							</object>
						</a> 
						&nbsp;&nbsp; 
						<a href="http://www.aquasuper.com"  target="_blanck">
							<img border="0" width="200" height="100" src="<c:url value="/jsp/images/aqua.jpg"/>"	align="bottom"/> 
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
			<a href="<c:url value="/jsp/language.jsp?lang=en"/>">English version</a>
		</td>
	</tr>
	
</table>
</div>

