var API_BASE_URL_FAVORITOS = 'http://localhost:8080/car2sale-api/favoritos';
var API_BASE_URL = 'http://localhost:8080/car2sale-api/anuncios';
var lastFilename;
var username = getCookie('username');
var password = getCookie('password');
var id;
$(document).ready(function(){
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	var url = API_BASE_URL_FAVORITOS;
	getFavoritos(url);
	
});
function FavoritoCollection(favoritoCollection, respuesta, favoritos){
	this.favoritos = favoritoCollection;	
	var instance = this;

	/*	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}*/
	
	this.links = buildLinks(favoritos.links);
	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
	
	this.toHTML = function(){
		var html = '';
		$.each(this.favoritos, function(i, v) {
			var favorito = v;
			var idanuncio = favorito.idanuncio;
			getAnuncio(idanuncio);				
		});
		
		
        var prev = this.getLink('after');
		
		
		if (prev != null) {
			url = prev.href.split("?");
			final= url[1].split("=");
			if (final[1]=='0'){
					
			}
			else{
			$('<a class="boton verde" onClick="getFavoritos(\'' + prev.href + '\')" id="prev" align=right>Anterior</a>').appendTo($('#result_paginable'));
			//html = html.concat('<a class="boton azul" onClick="getAnuncios(\'' + prev.href + '\')" id="prev" align=right>Anterior</a>');
			}
		}
        var next = this.getLink('before');
			
		if (next != null) {
			url = next.href.split("?");
			final= url[1].split("=");
			if (final[1]=='0'){
					//$('<pre style=display:inline>&#09</pre>').appendTo($('#result_favoritos'));
					$('<a class="boton azul" onClick="getPrincipio()" id="next" align=right>Volver al principio</a>').appendTo($('#result_paginable'));
			}
			else{
			$('<pre style=display:inline>&#09</pre>').appendTo($('#result_favoritos'));
			$('<a class="boton verde" onClick="getFavoritos(\'' + next.href + '\')" id="next" align=right>Siguiente</a>').appendTo($('#result_paginable'));
			$('<br>').appendTo($('#result_favoritos'));
			//html = html.concat ();
			//html = html.concat();
			}
		}
		

 		return html;	
	}
}
function getPrincipio(){
window.location= "getfavoritos.html";
	pasarVariables ('getfavoritos.html');
	$('getfavoritos.html').ready(function(){
	});
}
function getFavoritos(url){

	$("#result_favoritos").text('');
	$("#result_paginable").text('');
	
	$.ajax({
		headers : {
			'Authorization' : "Basic " + btoa(username + ':' + password)
		},
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var response = data.favoritos;
				var favoritos= data;
				var respuesta = $.parseJSON(jqxhr.responseText);				
				var favoritoCollection = new FavoritoCollection(response, respuesta, favoritos);
				//var linkHeader = jqxhr.getResponseHeader('Link');
                //favoritoCollection.buildLinks(linkHeader);
				var html = favoritoCollection.toHTML();
				//$("#result_mensajes").html(html);
				
	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}
function Link(rel, linkheader){
	this.rel = rel;
	this.href = decodeURIComponent(linkheader.find(rel).template().template);
	
	this.type = linkheader.find(rel).attr('type');

	this.title = linkheader.find(rel).attr('title');
	
}

function buildLinks(linkheaders){
	var links = {};
	$.each(linkheaders, function(i,linkheader){
		var linkhdr = $.linkheaders(linkheader);
		var rels = linkhdr.find().attr('rel').split(' ');
		$.each(rels, function(key,rel){
			var link = new Link(rel, linkhdr);
			links[rel] = link;
		});
	});

	return links;
}
function getAnuncio(i) {
	
	window.location= "getanunciomensajes.html";
	pasarVariables ('getanunciomensajes.html',i);
	$('getanunciomensajes.html').ready(function(){
	});
}

function pasarVariables(pagina, i) {
	pagina +="?";
	pagina += i + "=" + escape(eval(i))+"&";
	pagina = pagina.substring(0,pagina.length-1);
	location.href=pagina;
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

function getAnuncio(idanuncio){
	var url = API_BASE_URL + '/' + idanuncio;
	

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var anuncio = data;
				
				var respuesta = $.parseJSON(jqxhr.responseText);				
				$('<div class="item active"><img class="imgcenter" width="180" height="110" align=left src="'+data.imageURL+'">').appendTo($('#result_favoritos'));
				$('<strong></strong> ' + anuncio.titulo + '<br>').appendTo($('#result_favoritos'));
				$('<br>').appendTo($('#result_favoritos'));
				$('<FONT color="#F78A0E"><strong> Marca: </strong></FONT> ' + anuncio.marca + '<br>').appendTo($('#result_favoritos'));
				$('<FONT color="#F78A0E"><strong> Modelo: </strong></FONT> ' + anuncio.modelo + '<br>').appendTo($('#result_favoritos'));
				$('<FONT color="#F78A0E"><strong> Kilometros: </strong></FONT> ' + anuncio.km + '<br>').appendTo($('#result_favoritos'));
				$('<FONT color="#F78A0E"><strong> Precio: </strong> </FONT>' + anuncio.precio + ' euros<br>').appendTo($('#result_favoritos'));
				$('<FONT color="#F78A0E"><strong> Provincia: </strong></FONT> ' + anuncio.provincia + '<br>').appendTo($('#result_favoritos'));
				$('<br>').appendTo($('#result_favoritos'));
				$('<a class="boton azul" onclick="GetAnuncio('+idanuncio+')" id="anuncio" align=right>Ver Anuncio</a>').appendTo($('#result_favoritos'));
				$('<pre style=display:inline>&#09</pre>').appendTo($('#result_favoritos'));
				$('<a class="boton rojo" onclick="DeleteFavorito('+idanuncio+')" id="favorito" align=right>Eliminar Favorito</a>').appendTo($('#result_favoritos'));
				$('<br>').appendTo($('#result_favoritos'));
				$('<br>').appendTo($('#result_favoritos'));
				
	}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Error!</strong> Favorito no encontrado </div>').appendTo($("#result_favoritos"));
	});
	
	
}

function GetAnuncio(i) {
	
	window.location= "getanunciologeado.html";
	pasarVariables ('getanunciologeado.html',i);
	$('getanunciologeado.html').ready(function(){
	});
}
function DeleteFavorito(id) {
	var url = API_BASE_URL_FAVORITOS+ '/' + id;

	$
			.ajax({
					
					headers : {
					'Authorization' : "Basic " + btoa(username + ':' + password)
					},
						
						url : url,
						type : 'DELETE',
						crossDomain : true,
						dataType : 'json',
					})
			.done(
					function(data, status, jqxhr) {
						alert ("Favorito Eliminado OK!!")
						window.location= "getfavoritos.html";
						$('getfavoritos.html').ready(function(){
						});
					})
			.fail(
					function() {
						alert ("Error al Eliminar el Anuncio!!")
					});
					
}
$("#cerrar").click(function(e) {
    e.preventDefault();
	  if($.removeCookie('password')) {
			if($.removeCookie('username')) {
			
			$('#logout').html('<FONT color="#F5F920"><strong>La sesion se ha cerrado con exito! Actualizando pagina principal ......</strong></FONT>');
			window.setTimeout('window.location.replace("index.html")', 2000); // refresh after 2 sec
			}
	  }
 });
