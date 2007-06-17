<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<table width="100%" border="0" >
	<tr>
		<td align="center">
			<script type="text/javascript"><!--
			google_ad_client = "pub-3030374396324619";
			google_ad_width = 728;
			google_ad_height = 90;
			google_ad_format = "728x90_as";
			google_ad_type = "text_image";
			google_ad_channel = "";
			//--></script>
			<script type="text/javascript"
			  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="1">
	<tr valign="top">
		<td>
			<c:if test="${empty userLists}">
				<br><br>
				<table align="center" class="formBorder" width="50%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
					<tr height="10" bgcolor="#000000">
						<td colspan="3"></td>
					</tr>
					<tr height="5">
						<td colspan="3"></td>
					</tr>
					<tr>
						<td align="center" width="15%">
							<b><fmt:message key="show.all.birdlists.not.found" /></b>
						</td>
					</tr>
					<tr height="5">
						<td colspan="3"></td>
					</tr>
				</table>
			</c:if>
		<table class="formBorder" width="100%" border="0" cellspacing="1">
		<c:if test="${!empty userLists}">
			<tr>
				<td>
				<b><font color="#FFFFFF">
				<table bgcolor="#000000" width="100%">
					<tr>
						<td width="25%">
							<fmt:message key="birdlist.list.name.label" />
						</td>
						<td width="25%">
							<fmt:message key="birdlist.list.type.label" />
						</td>
						<td align="center" width="25%">
							<fmt:message key="birdlist.add.species" />
						</td>
						<td align="center" width="12%">
							<fmt:message key="birdlist.edit" />
						</td>
						<td align="center" width="13%">
							<fmt:message key="birdlist.remove" />
						</td>
					</tr>
				</table>
				</font></b>							
			</c:if>				
				<c:set var="count" value="${0}" /> 
				<c:set var="color" value="${'#D8D8D8'}" /> 
				<c:forEach items="${userLists}" var="birdList">
					<c:if test="${(count % 2) == 0}">
						<c:set var="color" value="${'#D8D8D8'}" />
					</c:if>

					<c:if test="${(count % 2) != 0}">
						<c:set var="color" value="${'#AAD1D8'}" />
					</c:if>

					<font size="1" face="Verdana">
					<table bgcolor="${color}" width="100%">
						<tr>
							<td width="25%">
								${birdList.name}
							</td>
							<td width="25%">
								<fmt:message key="${birdList.typeAsString}" />
							</td>
							<td align="center" width="25%">
								<a href="<c:url value="/servlet/editBirdList?listId=${birdList.id}"/>"> 
									<fmt:message key="birdlist.add.species" var="addToolTip"/>
									<img title="${addToolTip}"src="<c:url value="/jsp/images/note_add.gif"/>"  width="36" height="36"border="0"> 
								</a>
							</td>
							<td align="center" width="12%">
								<a href="<c:url value="/servlet/initEditBirdList?listId=${birdList.id}"/>"> 
									<fmt:message key="birdlist.edit" var="editToolTip"/>
									<img title="${editToolTip}"src="<c:url value="/jsp/images/note_edit.gif"/>"  width="36" height="36"border="0"> 
								</a>
							</td>
							<td align="center" width="13%">
								<a href="<c:url value="/frame.jsp?pageToShow=/jsp/birdList/doDeleteBirdList.jsp&listId=${birdList.id}"/>"> 
									<fmt:message key="birdlist.remove" var="removeToolTip"/>
									<img title="${removeToolTip}"src="<c:url value="/jsp/images/note_delete.gif"/>"  width="36" height="36"border="0"> 
								</a>
							</td>
						</tr>
					</table>
					</font>	
					<c:set var="count" value="${count + 1}" />
				</c:forEach></td>
			</tr>
		</table>

		</td>
	</tr>
</table>
