var API_BASE_URL = 'http://localhost:8080/car2sale-api/mensajes';
var API_BASE_URL_USERS = 'http://localhost:8080/car2sale-api/users';
var URL = 'http://localhost:8080/car2sale-api/anuncios';
var lastFilename;
var username = getCookie('username');
var password = getCookie('password');
$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(username+':'+password) }
});
$(document).ready(function(){
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	var url = API_BASE_URL;
	getMensajes(url);
	
});
//LISTA PAGINABLE DE LOS ANUNCIOS DE UN USUARIO

function MensajeCollection(mensajeCollection, respuesta, mensajes){
	this.mensajes = mensajeCollection;	
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
	
	this.links = buildLinks(mensajes.links);
	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
	
	this.toHTML = function(){
		var html = '';
		$.each(this.mensajes, function(i, v) {
			var mensaje = v;
			var idanuncio = mensaje.anuncio;
			var usuarioenvia = mensaje.usuarioenvia;
			var fecha = new Date(mensaje.creation_timestamp).toGMTString();
			var mensaje= mensaje.mensaje;
			getUser(fecha, usuarioenvia, mensaje, idanuncio);				
		});
		
		//html = html.concat(' <br> ');


        var prev = this.getLink('after');
		
		
		if (prev != null) {
			url = prev.href.split("?");
			final= url[1].split("=");
			if (final[1]=='0'){
					
			}
			else{
			$('<a class="boton verde" onClick="getMensajes(\'' + prev.href + '\')" id="prev" align=right>Anterior</a>').appendTo($('#result_paginable'));
			//html = html.concat('<a class="boton azul" onClick="getAnuncios(\'' + prev.href + '\')" id="prev" align=right>Anterior</a>');
			}
		}
        var next = this.getLink('before');
			
		if (next != null) {
			url = next.href.split("?");
			final= url[1].split("=");
			if (final[1]=='0'){
					//$('<pre style=display:inline>&#09</pre>').appendTo($('#result_paginable'));
					$('<a class="boton azul" onClick="getPrincipio()" id="next" align=right>Volver al principio</a>').appendTo($('#result_paginable'));
			
			}
			else{
			$('<pre style=display:inline>&#09</pre>').appendTo($('#result_favoritos'));
			$('<a class="boton verde" onClick="getMensajes(\'' + next.href + '\')" id="next" align=right>Siguiente</a>').appendTo($('#result_paginable'));
			$('<br>').appendTo($('#result_favoritos'));
			//html = html.concat ();
			//html = html.concat();
			}
		}
		

 		return html;	
	}
}
function getPrincipio(){
window.location= "getmensajes.html";
	pasarVariables ('getmensajes.html');
	$('getmensajes.html').ready(function(){
	});
}
function getMensajes(url){

	$("#result_mensajes").text('');
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
				var response = data.mensajes;
				var mensajes= data;
				var respuesta = $.parseJSON(jqxhr.responseText);				
				var mensajeCollection = new MensajeCollection(response, respuesta, mensajes);
				//var linkHeader = jqxhr.getResponseHeader('Link');
                //mensajeCollection.buildLinks(linkHeader);
				var html =mensajeCollection.toHTML();
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

function getUser(fecha, user, mensaje, idanuncio){
	var url = API_BASE_URL_USERS + '/' + user;

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				$('<class="item active"><img class="imgcenter" width="80" height="80" align=left src="'+data.imageURL+'">').appendTo($('#result_mensajes'));
				$('<strong></strong> Message received ' + fecha + '<br>').appendTo($('#result_mensajes'));
				$('<FONT color="#F78A0E"><strong> Escrito por, </strong></FONT>' + user + '<br>').appendTo($('#result_mensajes'));
				$('<a class="boton azul" onclick="getAnuncio('+idanuncio+')" id="anuncio" align=right>Ver Anuncio</a>').appendTo($('#result_mensajes'));
				$('<br>').appendTo($('#result_mensajes'));
				$('<br>').appendTo($('#result_mensajes'));
				$('<FONT color="#FFFFFF"></FONT>' + mensaje + '<br>').appendTo($('#result_mensajes'));
				$('<br>').appendTo($('#result_mensajes'));
				$('<br>').appendTo($('#result_mensajes'));
				$('<br>').appendTo($('#result_mensajes'));
		
				
	}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Error!</strong> Image not found </div>').appendTo($("#result_mensajes"));
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