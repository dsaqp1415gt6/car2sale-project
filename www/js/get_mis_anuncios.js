var API_BASE_URL = 'http://www.tgrupo6.dsa:8080/car2sale-api/anuncios/misanuncios';
var URL = 'http://www.tgrupo6.dsa:8080/car2sale-api/anuncios';
var lastFilename;
var username = getCookie('username');
var password = getCookie('password');
$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(username+':'+password) }
});
$(document).ready(function(){
$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	var url = API_BASE_URL;
	getAnuncios(url);
	
});
//LISTA PAGINABLE DE LOS ANUNCIOS DE UN USUARIO

function AnuncioCollection(anuncioCollection, respuesta, anuncios){
	this.anuncios = anuncioCollection;	
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
	
	this.links = buildLinks(anuncios.links);
	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
	
	this.toHTML = function(){
		var html = '';
		$.each(this.anuncios, function(i, v) {
			var anuncio = v;
			var idanuncio = anuncio.idanuncio;
			html = html.concat('<div class="item active"><img class="imgcenter"  width="180" height="110" align=left src="'+respuesta.anuncios[i].imageURL+'"');
			html = html.concat('<strong> </strong>' + anuncio.titulo + '<br>' );
		//	html = html.concat('<FONT color="#F78A0E"><strong> Descripcion: </strong> </FONT>'  + anuncio.descripcion + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Marca: </strong>  </FONT>' + anuncio.marca + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Modelo: </strong> </FONT>' + anuncio.modelo + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Kilometros: </strong> </FONT>' + anuncio.km + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Precio: </strong> </FONT>' + anuncio.precio + '&nbspeuros<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Provincia: </strong> </FONT>' + anuncio.provincia + '<br>');
			var fecha = new Date(anuncio.creation_timestamp).toGMTString();
			html = html.concat('<FONT color="#F78A0E"> Publicado: <strong></strong> </FONT>' + fecha + '<br>');
			html = html.concat('<br>');
			html = html.concat('<a class="boton azul" onclick="GetAnuncio('+idanuncio+')" id="anuncio" align=right>Ver Anuncio</a>');
			html = html.concat('<pre style=display:inline>&#09</pre>');
			html = html.concat('<a class="boton rojo" onclick="DeleteAnuncio('+idanuncio+')" id="anuncio" align=right>Eliminar Anuncio</a>');
			html = html.concat('<pre style=display:inline>&#09</pre>');
			html = html.concat('<a class="boton verde" onclick="PutAnuncio('+idanuncio+')" id="anuncio">Actualizar Anuncio</a><br>');
			html = html.concat('<br>');
			
		});
		
		html = html.concat(' <br> ');
/*
        var prev = this.getLink('after');
		
		
		if (prev != null) {
			console.log (prev.href);
			html = html.concat('<a class="boton azul" onClick="getAnuncios(\'' + prev.href + '\')" id="prev" align=right>Anterior</a>');
			
		}*/
        var next = this.getLink('before');
			
		if (next != null) {
			url = next.href.split("?");
			final= url[1].split("=");
			if (final[1]=='0'){
				html = html.concat('<a class="boton azul" onClick="getPrincipio()" id="next" align=right>Volver al principio</a>');
			}
			else{
//			html = html.concat ('<pre style=display:inline>&#09</pre>');
			html = html.concat('<a class="boton azul" onClick="getAnuncios(\'' + next.href + '\')" id="next" align=right>Cargar Siguientes</a>');
			}
		}
		

 		return html;	
	}
}
function getPrincipio(){
window.location= "get_mis_anuncios.html";
	pasarVariables ('get_mis_anuncios.html');
	$('get_mis_anuncios.html').ready(function(){
	});
}
function getAnuncios(url){

	$("#result_anuncios").text('');
	
	$.ajax({
		headers : {
			'Authorization' : "Basic " + btoa(username + ':' + password)
		},
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var response = data.anuncios;
				var anuncios= data;
				var respuesta = $.parseJSON(jqxhr.responseText);				
				var anuncioCollection = new AnuncioCollection(response, respuesta, anuncios);
				//var linkHeader = jqxhr.getResponseHeader('Link');
                //anuncioCollection.buildLinks(linkHeader);
				var html =anuncioCollection.toHTML();
				$("#result_anuncios").html(html);
				
	}).fail(function(jqXHR, textStatus) {
		
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
function GetAnuncio(i) {
	
	window.location= "getanunciologeado.html";
	pasarVariables ('getanunciologeado.html',i);
	$('getanunciologeado.html').ready(function(){
	});
}
function PutAnuncio(i) {
	window.location= "putanuncio.html";
	pasarVariables ('putanuncio.html',i);
	$('putanuncio.html').ready(function(){
	});
}
function pasarVariables(pagina, i) {
	pagina +="?";
	pagina += i + "=" + escape(eval(i))+"&";
	pagina = pagina.substring(0,pagina.length-1);
	location.href=pagina;
}
function DeleteAnuncio(i) {
	var url = URL + '/' + i;

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
						alert ("Anuncio Eliminado OK!!")
						window.location= "get_mis_anuncios.html";
						$('get_mis_anuncios.html').ready(function(){
						});
					})
			.fail(
					function() {
						alert ("Error al Eliminar el Anuncio!!")
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
			
			$('#logout').html('<FONT color="#F5F920"><strong>La sesion se ha cerrado con exito! Actualizando pagina principal ......</strong></FONT>');
			window.setTimeout('window.location.replace("index.html")', 2000); // refresh after 2 sec
			}
	  }
 });