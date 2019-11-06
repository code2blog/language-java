<!DOCTYPE html>
<%@page import="code2blog.jsp_map_dropdown.SomeClassRepresentingYourAccessLayerToDB"%>
<html>
	<head>
		<meta charset="ISO-8859-1" />
		<script src="./js/vendor/jquery.min.js"></script>
		<script src="./js/utility.js"></script>
		<title>Insert title here</title>
	</head>

	<body>
		<select id="country" name="country"  onchange="funcSelectOnChange(this.value);">
			<option value='empty'>Select Country</option>
		</select>
		<select style="display:none" id="state" name="state">
		</select>
	</body>

	<script type="text/javascript">
		
		var countryMapStates = JSON.parse('<%=SomeClassRepresentingYourAccessLayerToDB.getJsonStringOfMap()%>');
		
		$(document).ready(function(){
			console.log('dom.ready');
			for (var country in countryMapStates) {
			  if (countryMapStates.hasOwnProperty(country)) {
				  $('#country').append("<option value='{0}'>{0}</option>".formatUnicorn(country));
			  }
			}
		});

		funcSelectOnChange = function(country){
			console.log('funcSelectOnChange ' + country);
			$('#state').html('');
			$('#state').attr('style','');
			var states = countryMapStates[country];
			for(var i=0; i<states.length; i++){
				var state = states[i];
				console.log(state);
				$('#state').append("<option value='{0}'>{0}</option>".formatUnicorn(state));
			}
		};

	</script>

</html>