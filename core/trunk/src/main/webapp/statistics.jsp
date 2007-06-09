<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<font size="2" face="Verdana"> 
<table width="100%">
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.users" />:${f:numberOfUsers()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.users.photos" />:${f:numberOfUsersWithPhoto()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.users.sounds" />:${f:numberOfUsersWithSound()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.photos" /> : ${f:numberOfPhotos()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="stat.sounds" /> : ${f:numberOfSounds()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="family.with.photos" /> :${f:numberOfFamiliesWithPhoto()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="family.with.sounds" /> :${f:numberOfFamiliesWithSound()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="species.with.photos" /> :	${f:numberOfSpeciesWithPhoto()}</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td width="95%"><fmt:message key="species.with.sounds" /> :	${f:numberOfSpeciesWithSound()}</td>
	</tr>
</table>
</font> 

