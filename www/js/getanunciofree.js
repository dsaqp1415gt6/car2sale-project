var API_BASE_URL = 'http://localhost:8080/car2sale-api/anuncios';
var lastFilename;

$(document).ready(function(){
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