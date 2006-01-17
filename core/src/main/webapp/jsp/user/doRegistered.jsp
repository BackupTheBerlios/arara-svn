<%@ taglib uri="/core" prefix="c"%>

<div align="center"></div>
<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
      
    <td width="15%"> 
      <div align="left"><b>Seu registro foi feito com sussesso.</b></div><br>
      <b>Dados</b>
      <b>Nome:</b> ${user.name}<br>
      <b>Login:</b> ${user.login}<br>
      <b>Email:</b>${user.email}<br>
      <br>
      <c:if test="${user.emailOnNewPhoto == true}">
      <br>Você escolheu ser notificado por email quando uma nova foto for inserida no site.
      </c:if>

      <c:if test="${user.emailOnNewSound == true}">
      <br>Você escolheu ser notificado por email quando um novo som for inserido no site.
      </c:if>
    </td>
    </tr>
  </table>
<div align="left"></div>
