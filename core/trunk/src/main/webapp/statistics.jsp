<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<!--
<font size="0" face="Verdana"> 
    <c:choose>
        <c:when test='${statistics == "1"}'>
			<c:set var="statistics" scope="session" value="2" />
			<fmt:message key="stat.users" />:${f:numberOfUsers()}<br>
			<fmt:message key="stat.photos" /> : ${f:numberOfPhotos()}<br>
			<br>
        </c:when>
        <c:when test='${statistics == "2"}'>
			<c:set var="statistics" scope="session" value="3" />
			<fmt:message key="family.with.photos" /> :${f:numberOfFamilies()}<br>
			<fmt:message key="species.with.photos" /> :	${f:numberOfSpecies()}<br>
			<br>
        </c:when>
        <c:otherwise>
			<c:set var="statistics" scope="session" value="1" />
		  	<fmt:message key="stat.users" />:${f:numberOfUsers()}<br> 
			<fmt:message key="stat.photos" /> : ${f:numberOfPhotos()}<br>
			<br>
        </c:otherwise>
    </c:choose>
</font>   			
-->
<font size="2" face="Verdana"> 
<table width="100%">
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.users" />:${f:numberOfUsers()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.photos" /> : ${f:numberOfPhotos()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="family.with.photos" /> :${f:numberOfFamilies()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="species.with.photos" /> :	${f:numberOfSpecies()}</td>
	</tr>
</table>
</font> 

