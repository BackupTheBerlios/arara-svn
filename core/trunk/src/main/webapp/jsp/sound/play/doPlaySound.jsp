<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>

<html>
<body bgcolor="#A6D2D2">

<table width="240" >
<%--
	<tr>
		<td align="left">
			Fam�lia: <c:out value="${sound.specie.family.name}"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			Esp�cie: <c:out value="${sound.specie.name}"/>
		</td>
	</tr>
--%>
	<tr>
		<td align="left">
				<APPLET 
					CODE="net/indrix/applets/sound/SoundPlayer" 
					ARCHIVE="applet.jar" 
					WIDTH="180" 
					HEIGHT="30"
					ALT="Houve um erro no seu browser. O som n�o ser� executado.">
					<PARAM NAME="specieId" VALUE="${param.specieId}">
					
					Seu browser n�o suporta applets. O som n�o ser� executado.
				</APPLET>		
		</td>
	</tr>
</table>

</body>
</html>