$(document).ready(function() {
	$('div.atributosProfesor').hide();
	$('#rol').change(function() {
		if($(this).val()=="Alumno"){
			$('div.atributosAlumno').show();
			$('div.atributosProfesor').hide();
		}
		else{
			$('div.atributosAlumno').hide();
			$('div.atributosProfesor').show();
		}
	});
});