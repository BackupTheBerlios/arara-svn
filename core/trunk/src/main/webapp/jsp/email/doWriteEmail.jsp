<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>


<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${80}"/>
<br>
<form onsubmit="return submit_form()"
	  name="sendEmail" method="post" 
      action="<c:url value="/servlet/sendEmail"/>"
      >

  <table class="formBorder" width="80%" align="center" border="0" cellspacing="2">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="user.title" /></b>
    </td>
    <td width="${col3}%"> 
        <input type="text" name="loginTo" value="${param.loginTo}" size="80" maxlength="128" >
    </td>
  </tr>
  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="email.subject"/></b>
    </td>
    <td width="${col3}%"> 
		<input type="text" name="subject" size="80" maxlength="128">
    </td>
  </tr>
  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="email.body"/></b>
    </td>
    <td width="${col3}%">
        <textarea rows="15" cols="60" name="body"></textarea>
    </td>
  </tr>
  <tr>
	<td width="${col1}%"></td>
    <td width="${col2}%"></td>
    <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="photo.submit"/>">
        </div>
    </td>
  </tr>
  <tr height="5">
	<td colspan="2"></td>
  </tr>
  </table>  
</form>
