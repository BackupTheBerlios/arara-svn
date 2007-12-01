<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<SCRIPT language="JavaScript">  
	function submitForm ( ){
		for (var i = 0; i < document.birdlistCreation.selectedCities.length; i++) {
            document.birdlistCreation.selectedCities.options[i].selected = true;
		}	
	    return submit_form();
	}  

    function stateSelected() { 
		for (var i = 0; i < document.birdlistCreation.selectedCities.length; i++) {
            document.birdlistCreation.selectedCities.options[i].selected = true;
		}
		document.birdlistCreation.action = "<c:url value="/servlet/retrieveCitiesForState?nextPage=/frame.jsp&pageToShow=${pageToShow}&servletToCall=${servletToCall}&data=BIRDLIST&doAction=${action}"/>"; 
		document.birdlistCreation.method = "post";
		document.birdlistCreation.submit(); 
    }    

	function sort(array){
        for (var i = 0; i < array.length-1; i++) {	
        	for (var j = i; j < array.length; j++) {	
        		if (array.options[i].text > array.options[j].text){
	        		temp = array.options[i];
	        		array.options[i] = array.options[j];
	        		array.options[j] = temp;
	        	}
        	}
        }
	}
	
	function addElement(state, objSourceElement, objTargetElement){
        var aryTempSourceOptions = new Array();
        var x = 0;
        
        //looping through source element to find selected options
        for (var i = 0; i < objSourceElement.length; i++) {
            if (objSourceElement.options[i].selected) {
	            found = false;
		        for (var j = 0; j < objTargetElement.length && !found; j++) {
		        	if (objTargetElement.options[j].text.substring(5) == objSourceElement.options[i].text){
		        		found = true;
		        	}
		        }
            	if (!found){
	                //need to move this option to target element
	                var intTargetLen = objTargetElement.length++;
	                objTargetElement.options[intTargetLen].text = state + " - " + objSourceElement.options[i].text;
	                objTargetElement.options[intTargetLen].value = objSourceElement.options[i].value;
	            }
            }
        }
        
        sort(objTargetElement);
    }

	function removeElement(state, objSourceElement, objTargetElement){
        var aryTempSourceOptions = new Array();
        var x = 0;
        
        //looping through source element to find selected options
        for (var i = 0; i < objSourceElement.length; i++) {
            if (objSourceElement.options[i].selected) {
                //need to move this option to target element
                if (objSourceElement.options[i].text.substring(0, 2) == state){
	                var intTargetLen = objTargetElement.length++;
	                objTargetElement.options[intTargetLen].text = objSourceElement.options[i].text.substring(5);
	                objTargetElement.options[intTargetLen].value = objSourceElement.options[i].value;
	            }
            }
            else {
                //storing options that stay to recreate select element
                var objTempValues = new Object();
                objTempValues.text = objSourceElement.options[i].text;
                objTempValues.value = objSourceElement.options[i].value;
                aryTempSourceOptions[x] = objTempValues;
                x++;
            }
        }
        
        //resetting length of source
        objSourceElement.length = aryTempSourceOptions.length;
        //looping through temp array to recreate source select element
        for (var i = 0; i < aryTempSourceOptions.length; i++) {
            objSourceElement.options[i].text = aryTempSourceOptions[i].text;
            objSourceElement.options[i].value = aryTempSourceOptions[i].value;
            objSourceElement.options[i].selected = false;
        }
        
        //sort(objSourceElement);
        //sort(objTargetElement);
    }

    
</script> 


<c:set var="selectedType" value="${birdListBean.selectedType}"/>
<c:set var="selectedStateId" value="${birdListBean.selectedStateId}"/>

<fmt:message key="birdlist.list.type.public" var="publicType"/>
<fmt:message key="birdlist.list.type.private" var="privateType"/>

<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${80}"/>

<table width="100%" border="0">
<tr>
  <td width="85%" valign="top">
	<br>
	<form onsubmit="return submitForm()"
		  name="birdlistCreation" method="post" 
	      action="<c:url value="${servletToCall}"/>">

      <input type=hidden name="servletToCall" value="${servletToCall}">
      <input type=hidden name="doAction" value="${action}">
      
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
	    	<b><fmt:message key="birdlist.list.name.label"/></b>
	    </td>
	    <td width="${col3}%"> 
	      <c:if test="${doAction == 'CREATE'}">
	        <input type="text" name="name" value="${birdListBean.name}" size="64" maxlength="64">
	      </c:if>
	      <c:if test="${doAction == 'EDIT'}">
	        <input type="text" name="name" readonly value="${birdListBean.name}" size="64" maxlength="64">
	      </c:if>
	    </td>
	  </tr>
	  <tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="birdlist.list.type.label"/></b>
	    </td>
	    <td width="${col3}%"> 
	        <select name="type">
			  <c:if test="${selectedType != null && selectedType == 'public'}">
			      <option selected value="public">${publicType}</option>
			  </c:if>
			  <c:if test="${selectedType == null || selectedType != 'public'}">
			      <option value="public">${publicType}</option>
			  </c:if>
			  <c:if test="${selectedType != null && selectedType == 'private'}">
			      <option selected value="private">${privateType}</option>
			  </c:if>
			  <c:if test="${selectedType == null || selectedType != 'private'}">
			      <option value="private">${privateType}</option>
			  </c:if>
	        </select>
	    </td>
	  </tr>
	  <tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="birdlist.list.location.label"/></b>
	    </td>
	    <td width="${col3}%"> 
	        <input type="text" name="location" value="${birdListBean.location}" size="64" maxlength="64">
	    </td>
	  </tr>
	
	  <tr> 
		<td width="${col1}%"></td>
	    <td valign="top" width="${col2}%">
	    	<b><fmt:message key="state"/></b>
	    </td>
	    <td valign="top" width="${col3}%">
	        <select name="stateId" onChange="stateSelected()">
	          <c:forEach items="${birdListBean.statesList}" var="stateBean">        
				<c:if test="${selectedStateId != null && selectedStateId == stateBean.value}">
			      <option selected value="${stateBean.value}">${stateBean.label}</option>
			      <c:set var="selectedState" value="${stateBean.label}"/>
			    </c:if>
				<c:if test="${selectedStateId == null || selectedStateId != stateBean.value}">
			      <option value="${stateBean.value}">${stateBean.label}</option>
			    </c:if>		      
	          </c:forEach>
	        </select>  
	    </td>
	  </tr>
	  <tr>    
		<td width="${col1}%"></td>
	    <td valign="top" width="${col2}%">
			<b><fmt:message key="city"/></b>
		</td>
	    <td valign="top" width="${col3}%">
	    	<table>
	    	<tr>
		    	<td valign="center">
			        <select name="cityId" size="10" multiple>
			          <c:forEach items="${birdListBean.currentStateCities}" var="city">
						<c:if test="${selectedCityId != null && selectedCityId == city.id}">
					      <option selected value="${city.id}">${city.name}</option>
					    </c:if>
						<c:if test="${selectedCityId == null || selectedCityId != city.name}">
					      <option value="${city.id}">${city.name}</option>
					    </c:if>		      
			          </c:forEach>
			        </select>  
			    </td>
		    	<td valign="center">
		    		<c:set var="functionName" value="addElement('${selectedState}', document.birdlistCreation.cityId, document.birdlistCreation.selectedCities)"/>
					<input type="button" name="add" value="&nbsp;&nbsp;&nbsp; -&gt; " style="width: 100px;" onClick="${functionName}">
				    <p>
		    		<c:set var="functionName" value="removeElement('${selectedState}', document.birdlistCreation.selectedCities, document.birdlistCreation.cityId)"/>
				    <input type="button" name="remove" value=" &lt;- &nbsp;&nbsp;&nbsp;" style="width: 100px;" onClick="${functionName}"><br>
			    </td>
		    	<td valign="center">
			        <select name="selectedCities" size="10" multiple>
			          <c:forEach items="${birdListBean.selectedCities}" var="selectedCity">
					      <option value="${selectedCity.value}">${selectedCity.label}</option>
			          </c:forEach>
			        </select>  
			    </td>
			</tr>
			</table>
		</td>
	  </tr>
	  <tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="birdlist.list.comment.label"/></b>
	    </td>
	    <td width="${col3}%">
	        <textarea rows="4" cols="60" name="comment">${birdListBean.comment}</textarea>
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
  </td>
  <td width="15%" valign="top" align="center">
	<script type="text/javascript"><!--
	google_ad_client = "pub-3030374396324619";
	google_ad_width = 120;
	google_ad_height = 240;
	google_ad_format = "120x240_as";
	google_ad_type = "text_image";
	google_ad_channel = "";
	//--></script>
	<script type="text/javascript"
	  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
	
	<script type="text/javascript"><!--
	google_ad_client = "pub-3030374396324619";
	google_ad_width = 120;
	google_ad_height = 600;
	google_ad_format = "120x600_as";
	google_ad_type = "image";
	google_ad_channel = "";
	//--></script>
	<script type="text/javascript"
	  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
  </td>
</tr>
</table>	