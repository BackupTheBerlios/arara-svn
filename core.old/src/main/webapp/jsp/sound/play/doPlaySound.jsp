<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<html>
<body bgcolor="#A6D2D2">

<table width="240" >
<%--
	<tr>
		<td align="left">
			Família: <c:out value="${sound.specie.family.name}"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			Espécie: <c:out value="${sound.specie.name}"/>
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
					ALT="Houve um erro no seu browser. O som não será executado.">
					<PARAM NAME="specieId" VALUE="${param.specieId}">
					
					Seu browser não suporta applets. O som não será executado.
				</APPLET>		
		</td>
	</tr>
</table>

</body>
</html>