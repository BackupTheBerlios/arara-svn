<%@ taglib uri="/core" prefix="c"%>

<form name="uploadForm" method="post" 
      action="<c:url value="/servlet/uploadPhotoIdentify"/>"
      enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="15%"><b>Camera</b></td>
    <td width="85%"> 
        <input type="text" name="camera" value="${lastUploadBean.camera}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Lente</b></td>
    <td width="85%"> 
        <input type="text" name="lens" value="${lastUploadBean.lens}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Filme</b></td>
    <td width="85%"> 
        <input type="text" name="film" value="${lastUploadBean.film}" size="64" maxlength="64">
    </td>
  </tr>

  <tr> 
    <td width="15%"><b>Local</b></td>
    <td width="85%"> 
        <input type="text" name="location" value="${lastUploadBean.location}" size="64" maxlength="64">
    </td>
  </tr>
  
  <tr>
    <td width="15%"><b>Data</b></td>
    <td width="85%">
        <input type="text" name="date" value="${lastUploadBean.date}" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
    <td width="15%"><b>Comentário do Autor</b></td>
    <td width="85%">
        <textarea rows="5" cols="70" name="comment">${lastUploadBean.comment}</textarea>
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Arquivo</b></td>
    <td width="85%"> 
        <input type="file" name="fileName"> 
        <font color="#FF000">Max 250Kb</font>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="Enviar">
        </div>
      </td>
    </tr>
  </table>  
</form>
