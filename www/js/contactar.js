var API_BASE_URL = 'http://localhost:8080/car2sale-api/mensajes';
var lastFilename;
var username = getCookie('username');
var password = getCookie('password');
var datos = getCookie('datos');
$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(username+':'+password) }
})

$(document).ready(function(){
	
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	
});
$("#enviar").click(function(e){
	
	if($('#mensaje').val() == ""){
		$('<div class="alert alert-info">Mensaje en blanco, debes escribir un mensaje </div>').appendTo($("#mensaje_result"));
	}
	else{
	d= datos.split(",");
	idanuncio= d[0];
	usuariorecibe=d[1];
	
	var mensaje = $('#mensaje').val();
		Mensaje = {
			"usuariorecibe" : usuariorecibe,
			"anuncio" : idanuncio,
			"mensaje" : mensaje,
			
		}
		createMensaje(Mensaje);
		}
	
});

function createMensaje(mensaje) {
	var url = API_BASE_URL;
	var data = JSON.stringify(mensaje);
	
	$("#mensaje_result").text('');

	$.ajax({
		headers : {
			'Authorization' : "Basic " + btoa(username + ':' + password)
		},
		contentType: 'application/vnd.mensajes.api.mensaje+json',
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong></strong>Mensaje Enviado OK!!</div>').appendTo($("#mensaje_result"));		
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong></strong> Error al enviar el mensaje!!</div>').appendTo($("#mensaje_result"));
	});

}

function getCookie(cname) {

    var name = cname + "=";

    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);{
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);{}}
    }
    return "";
}
$("#cerrar").click(function(e) {
    e.preventDefault();
	  if($.removeCookie('password')) {
			if($.removeCookie('username')) {
			
			$('#logout').html('<FONT color="#F5F920"><strong>La sesion se ha cerrdo con exito! Actualizando pagina principal ......</strong></FONT>');
			window.setTimeout('window.location.replace("index.html")', 2000); // refresh after 2 sec
			}
	  }
 });