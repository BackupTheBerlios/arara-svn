<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<table width="100%" border="0" cellspacing="1" >
<tr>
<td>
<table width="100%" border="0" cellspacing="1" >

<c:if test="${empty listOfSounds}">
   <br><h3>N�o h� sons para a op��o selecionada.</h3>
</c:if>
  <tr>
    <td><b>Usu�rio</b></td>
    <td><b>Fam�lia</b></td>
    <td><b>Esp�cie</b></td>
  </tr>
<c:forEach items="${listOfSounds}" var="sound">
   <tr>
     <td>${sound.user.login}</td>
     <td>${sound.specie.family.name}</td>
     <td>${sound.specie.name}</td>
   </tr>
</c:forEach>
</table>
</td>
</tr>
<c:import url="/jsp/sound/display/pagination.jsp"/>
</table>   