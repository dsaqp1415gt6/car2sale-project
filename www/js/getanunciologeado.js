var API_BASE_URL = 'http://localhost:8080/car2sale-api/anuncios';
var lastFilename;
var username = getCookie('username');
$(document).ready(function(){
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	cadVariables = location.search.substring(1,location.search.length);
	arrVariables = cadVariables.split("=");
	var id = arrVariables[0];
	getAnuncio(id);
});

function getAnuncio(id){

	var url = API_BASE_URL + '/' + id;
		$("#result_anuncio").text('');
		$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
		}).done(function(data, status, jqxhr) {
				
				var anuncio = data;
				var respuesta = $.parseJSON(jqxhr.responseText);
				var fecha = new Date(anuncio.creation_timestamp).toGMTString();
				$('<strong>Anuncio publicado desde el</strong> ' + fecha + '<br>').appendTo($('#result_anuncio'));	
				$('<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));				
				$('<div class="item active"><img class="imgcenter" width="325" height="216" align=left src="'+data.imageURL+'">').appendTo($('#result_anuncio'));
				$('<strong></strong> ' + anuncio.titulo + '<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<FONT color="#F78A0E"><strong> Marca: </strong></FONT> ' + anuncio.marca + '<br>').appendTo($('#result_anuncio'));
				$('<FONT color="#F78A0E"><strong> Modelo: </strong></FONT> ' + anuncio.modelo + '<br>').appendTo($('#result_anuncio'));
				$('<FONT color="#F78A0E"><strong> Kilometros: </strong></FONT> ' + anuncio.km + '<br>').appendTo($('#result_anuncio'));
				$('<FONT color="#F78A0E"><strong> Precio: </strong> </FONT>' + anuncio.precio + ' euros<br>').appendTo($('#result_anuncio'));
				$('<FONT color="#F78A0E"><strong> Provincia: </strong></FONT> ' + anuncio.provincia + '<br>').appendTo($('#result_anuncio'));
				$('<p><H6><strong></strong> ' + anuncio.descripcion + '<br></H6></p>').appendTo($('#result_descripcion'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<strong><font face=arial size=4> Numero de visitas</font></strong> ' + anuncio.contador + '<br>').appendTo($('#result_contador'));
				$('<br>').appendTo($('#result_anuncio'));
				$('<br>').appendTo($('#result_anuncio'));
				
				
		}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Anuncio no encontrado </div>').appendTo($("#result_anuncio"));
			
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
