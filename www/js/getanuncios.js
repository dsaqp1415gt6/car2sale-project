var API_BASE_URL = 'http://localhost:8080/car2sale-api/anuncios';
var API_BASE_URL_PRECIOS = 'http://localhost:8080/car2sale-api/anuncios/precios'
var API_BASE_URL_PROVINCIAS = 'http://localhost:8080/car2sale-api/anuncios/provincias'
var lastFilename;
var username = getCookie('username');

$(document).ready(function(){
	
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	var url = API_BASE_URL;
	getAnuncios(url);
	
});
//1. GET LISTA TODOS ANUNCIOS



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
			html = html.concat('<FONT color="#F78A0E"><strong> Precio: </strong> </FONT>' + anuncio.precio + '€<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Provincia: </strong> </FONT>' + anuncio.provincia + '<br>');
			var fecha = new Date(anuncio.creation_timestamp).toGMTString();
			html = html.concat('<FONT color="#F78A0E"> Publicado: <strong></strong> </FONT>' + fecha + '<br>');
			html = html.concat('<br>');
			html = html.concat('<a class="boton verde" onclick="getAnuncio('+idanuncio+')" id="anuncio" align=right>Ver anuncio</a><br>');
			html = html.concat('<br>');
			html = html.concat('<br>');
		});
		
		html = html.concat(' <br> ');

      /*  var prev = this.getLink('after');
		
		
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
			//html = html.concat ('<pre style=display:inline>&#09</pre>');
			html = html.concat('<a class="boton azul" onClick="getAnuncios(\'' + next.href + '\')" id="next" align=right>Cargar Siguientes</a>');
			}
		}
		

 		return html;	
	}
}
function getPrincipio(){
window.location= "logeado.html";
	pasarVariables ('logeado.html');
	$('logeado.html').ready(function(){
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

function getAnuncios(url){

	$("#result_anuncios").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var response = data.anuncios;
				var anuncios= data;
				var respuesta = $.parseJSON(jqxhr.responseText);
				var anuncioCollection = new AnuncioCollection(response, respuesta, anuncios);
				//var linkHeader = jqxhr.getResponseHeader('links');
				//console.log(linkHeader);
				//anuncioCollection.buildLinks(linkHeader);
				var html =anuncioCollection.toHTML();
				$("#result_anuncios").html(html);
				
	}).fail(function(jqXHR, textStatus) {
		
	});
}
$("#marca").click(function(e){
	var marca = document.getElementById("marca");
	var selmarca = marca.options[marca.selectedIndex].text;
		
	
	if (selmarca== 'BMW'){
	   document.getElementById("m1").innerHTML = 'i3';
	   document.getElementById("m2").innerHTML = 'M3';
	   document.getElementById("m3").innerHTML = 'M4';
	   document.getElementById("m4").innerHTML = 'M5';
	   document.getElementById("m5").innerHTML = 'M6';
	   document.getElementById("m6").innerHTML = 'X1';
	   document.getElementById("m7").innerHTML = 'X3';
	   document.getElementById("m8").innerHTML = 'X4';
	   document.getElementById("m9").innerHTML = 'X5';
	   document.getElementById("m10").innerHTML = 'X6';
	   document.getElementById("m11").innerHTML = 'i8';
	  
		}
	if (selmarca== 'Ferrari'){
	   document.getElementById("m1").innerHTML = 'California';
	   document.getElementById("m2").innerHTML = '458 Italia';
	   document.getElementById("m3").innerHTML = '458 Spider';
	   document.getElementById("m4").innerHTML = '458 Speciale';
	   document.getElementById("m5").innerHTML = '488 GTB';
	   document.getElementById("m6").innerHTML = 'Sergio';
	   document.getElementById("m7").innerHTML = '360 Modena';
	   document.getElementById("m8").innerHTML = '612 Scaglietti';
	   document.getElementById("m9").innerHTML = '575M Maranello';
	   document.getElementById("m10").innerHTML = 'F430	Scuderia';
	   document.getElementById("m11").innerHTML = 'F430	Coupe';	
		}
		if (selmarca== 'Ford'){
	   document.getElementById("m1").innerHTML = 'Ka';
	   document.getElementById("m2").innerHTML = 'Fiesta';
	   document.getElementById("m3").innerHTML = 'Focus';
	   document.getElementById("m4").innerHTML = 'B-MAX';
	   document.getElementById("m5").innerHTML = 'C-MAX';
	   document.getElementById("m6").innerHTML = 'Grand C-MAX';
	   document.getElementById("m7").innerHTML = 'Mondeo';
	   document.getElementById("m8").innerHTML = 'S-MAX';
	   document.getElementById("m9").innerHTML = 'Mustang';
	   document.getElementById("m10").innerHTML = 'Galaxy';
	   document.getElementById("m11").innerHTML = 'Kuga';
	  
	   
		}
	   if (selmarca== 'Mazda'){
	   document.getElementById("m1").innerHTML = '2';
	   document.getElementById("m2").innerHTML = '3';
	   document.getElementById("m3").innerHTML = '5';
	   document.getElementById("m4").innerHTML = '6';
	   document.getElementById("m5").innerHTML = 'MX-5';
	   document.getElementById("m6").innerHTML = 'CX-3';
	   document.getElementById("m7").innerHTML = 'CX-5';
	   document.getElementById("m8").innerHTML = 'CX-9';
	   document.getElementById("m9").innerHTML = 'RX-8';
	   document.getElementById("m10").innerHTML = 'CX-7';
	   document.getElementById("m11").innerHTML = 'Premacy';
	  
	   
	   }
	   if (selmarca== 'Nissan'){
	   document.getElementById("m1").innerHTML = 'Micra';
	   document.getElementById("m2").innerHTML = 'Note';
	   document.getElementById("m3").innerHTML = 'Juke';
	   document.getElementById("m4").innerHTML = 'Pulsar';
	   document.getElementById("m5").innerHTML = 'LEAF';
	   document.getElementById("m6").innerHTML = '370Z';
	   document.getElementById("m7").innerHTML = 'GT-R';
	   document.getElementById("m8").innerHTML = 'Qashqai';
	   document.getElementById("m9").innerHTML = 'X-Trail';
	   document.getElementById("m10").innerHTML = 'Pathfinder';
	   document.getElementById("m11").innerHTML = 'Murano';
	   }
	   if (selmarca== 'Opel'){
	   document.getElementById("m1").innerHTML = 'Corsa';
	   document.getElementById("m2").innerHTML = 'Karl';
	   document.getElementById("m3").innerHTML = 'ADAM';
	   document.getElementById("m4").innerHTML = 'Astra';
	   document.getElementById("m5").innerHTML = 'Meriva';
	   document.getElementById("m6").innerHTML = 'Zafira';
	   document.getElementById("m7").innerHTML = 'Vectra';
	   document.getElementById("m8").innerHTML = 'Insignia';
	   document.getElementById("m9").innerHTML = 'Ampera';
	   document.getElementById("m10").innerHTML = 'Mokka';
	   document.getElementById("m11").innerHTML = 'Antara';
	   }
	   if (selmarca== 'Peugeot'){
	   document.getElementById("m1").innerHTML = '108';
	   document.getElementById("m2").innerHTML = '207';
	   document.getElementById("m3").innerHTML = '208';
	   document.getElementById("m4").innerHTML = '308';
	   document.getElementById("m5").innerHTML = 'RCZ';
	   document.getElementById("m6").innerHTML = '206';
	   document.getElementById("m7").innerHTML = '107';
	   document.getElementById("m8").innerHTML = '306';
	   document.getElementById("m9").innerHTML = '307';
	   document.getElementById("m10").innerHTML = '406';
	   document.getElementById("m11").innerHTML = 'Antara';
	   }
	   if (selmarca== 'Renault'){
	   document.getElementById("m1").innerHTML = 'Twizy Z.E.';
	   document.getElementById("m2").innerHTML = 'Twingo';
	   document.getElementById("m3").innerHTML = 'Zoe';
	   document.getElementById("m4").innerHTML = 'Clio';
	   document.getElementById("m5").innerHTML = 'Megane';
	   document.getElementById("m6").innerHTML = 'Fluence';
	   document.getElementById("m7").innerHTML = 'Scénic';
	   document.getElementById("m8").innerHTML = 'Laguna';
	   document.getElementById("m9").innerHTML = 'Espace';
	   document.getElementById("m10").innerHTML = 'Captur';
	   document.getElementById("m11").innerHTML = 'Kangoo';
	   }
	   if (selmarca== 'Seat'){
	   document.getElementById("m1").innerHTML = 'Mii';
	   document.getElementById("m2").innerHTML = 'Ibiza';
	   document.getElementById("m3").innerHTML = 'Toledo';
	   document.getElementById("m4").innerHTML = 'Leon';
	   document.getElementById("m5").innerHTML = 'Altea';
	   document.getElementById("m6").innerHTML = 'Alhambra';
	   document.getElementById("m7").innerHTML = 'Arosa';
	   document.getElementById("m8").innerHTML = 'Cordoba';
	   document.getElementById("m9").innerHTML = 'Exeo';
	   document.getElementById("m10").innerHTML = 'Panda';
	   document.getElementById("m11").innerHTML = '600';
	   }
	   if (selmarca== 'Porsche'){
	   document.getElementById("m1").innerHTML = 'Panamera';
	   document.getElementById("m2").innerHTML = 'Boxster';
	   document.getElementById("m3").innerHTML = 'Cayman';
	   document.getElementById("m4").innerHTML = '911';
	   document.getElementById("m5").innerHTML = 'Macan';
	   document.getElementById("m6").innerHTML = 'Cayenne';
	   document.getElementById("m7").innerHTML = '918';
	   document.getElementById("m8").innerHTML = 'Boxster';
	   document.getElementById("m9").innerHTML = 'Carrera GT';
	   document.getElementById("m10").innerHTML = 'Cayman';
	   document.getElementById("m11").innerHTML = 'GT2';
	   }
	   if (selmarca== 'Volkswagen'){
	   document.getElementById("m1").innerHTML = 'Polo';
	   document.getElementById("m2").innerHTML = 'Golf';
	   document.getElementById("m3").innerHTML = 'Touran';
	   document.getElementById("m4").innerHTML = 'Jetta';
	   document.getElementById("m5").innerHTML = 'Beetle';
	   document.getElementById("m6").innerHTML = 'Passat';
	   document.getElementById("m7").innerHTML = 'Sharan';
	   document.getElementById("m8").innerHTML = 'Phaeton';
	   document.getElementById("m9").innerHTML = 'Scirocco';
	   document.getElementById("m10").innerHTML = 'Tiguan';
	   document.getElementById("m11").innerHTML = 'Touareg';
	   }
	   if (selmarca== 'Lamborghini'){
	   document.getElementById("m1").innerHTML = 'Huracan';
	   document.getElementById("m2").innerHTML = 'Aventador';
	   document.getElementById("m3").innerHTML = 'Veneno';
	   document.getElementById("m4").innerHTML = 'Diablo';
	   document.getElementById("m5").innerHTML = 'Gallardo';
	   document.getElementById("m6").innerHTML = 'Murcielago';
	   document.getElementById("m7").innerHTML = 'Reventon';
	   document.getElementById("m8").innerHTML = 'Veneno Roadster';
	   document.getElementById("m9").innerHTML = 'Aventador J';
	   document.getElementById("m10").innerHTML = 'Superleggera';
	   document.getElementById("m11").innerHTML = 'Reventon Roadster';
	   }
});

function getAnuncio(i) {
	
	window.location= "getanuncio.html";
	pasarVariables ('getanuncio.html',i);
	$('getanuncio.html').ready(function(){
	});
}

function pasarVariables(pagina, i) {
	pagina +="?";
	pagina += i + "=" + escape(eval(i))+"&";
	pagina = pagina.substring(0,pagina.length-1);
	location.href=pagina;
}

$("#buscar").click(function(e) {
	e.preventDefault();
	
	var marca = document.getElementById("marca");
	var selmarca = marca.options[marca.selectedIndex].text;
	var modelo = document.getElementById("modelo");
	var selmodelo = modelo.options[modelo.selectedIndex].text;
	var precio = document.getElementById("precio");
	var selprecio = precio.options[precio.selectedIndex].text;
	var km = document.getElementById("km");
	var selkm = km.options[km.selectedIndex].text;
	precio = selprecio.split("€");
	if (precio[0]=="+de 70000"){
		precio[0]= "70000";
	}
	var provincia = document.getElementById("provincia");
	var selprovincia = provincia.options[provincia.selectedIndex].text;
	
	
	if((selmarca == 'Marcas') || (selmodelo=='Modelos')){
		$('<div class="alert alert-danger"> <strong></strong> Debes Seleccionar una Marca de coche y un Modelo </div>').appendTo($("#result_buscar"));
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selprecio!='precio hasta')&& (selkm!='km hasta')&& (selprovincia!='provincias')){
		var url = API_BASE_URL+ '/' +selmarca+ '/' +selmodelo+ '/' +selkm+ '/' +precio[0]+ '/' +selprovincia;
		getAnuncios(url);
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selprecio!='precio hasta')&& (selkm!='km hasta')){
		var url = API_BASE_URL+ '/' +selmarca+ '/' +selmodelo+ '/' +precio[0]+ '/' +selkm;
		getAnunciosCollection(url);
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selprecio!='precio hasta') && (selprovincia!='provincias')){
		var url = API_BASE_URL_PRECIOS+ '/' +selmarca+ '/' +selmodelo+ '/' +precio[0]+ '/' +selprovincia;
		getAnuncios(url)
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selkm!='km hasta') && (selprovincia!='provincias')){
		var url = API_BASE_URL_PROVINCIAS+ '/' +selmarca+ '/' +selmodelo+ '/' +selkm+ '/' +selprovincia;
		getAnuncios(url)
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selprecio!='precio hasta')){
		var url = API_BASE_URL_PRECIOS+ '/' +selmarca+ '/' +selmodelo+ '/' +precio[0];
		getAnunciosCollection(url)
		
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selkm!='km hasta')){
		var url = API_BASE_URL+ '/' +selmarca+ '/' +selmodelo+ '/' +selkm;
		getAnunciosCollection(url);
	
	}else if ((selmarca != 'Marcas') && (selmodelo!='Modelos') && (selprovincia!='provincias')){
		var url = API_BASE_URL_PROVINCIAS+ '/' +selmarca+ '/' +selmodelo+ '/' +selprovincia;
		getAnuncios(url);
	}else{
		var url = API_BASE_URL+ '/' +selmarca+ '/' +selmodelo;
		getAnunciosCollection(url);
	}
	
	
	
})

function Anuncios(anuncioCollection, respuesta, anuncios){
	this.anuncios = anuncioCollection;	
	var instance = this;

	
	this.toHTML = function(){
		var html = '';
		
		if ((respuesta.newestTimestamp=='0') || (respuesta.oldestTimestamp=='0')){
			html = html.concat('<strong> Ops!! No hay resultados en la busqueda</strong>');
			html = html.concat('<br>');
			html = html.concat('<br>');
			html = html.concat('<a class="boton azul" onClick="getPrincipio()" id="next" align=right>Volver al principio</a>');
		}
		else{
		$.each(this.anuncios, function(i, v) {
			var anuncio = v;
			var idanuncio = anuncio.idanuncio;
			html = html.concat('<div class="item active"><img class="imgcenter"  width="180" height="110" align=left src="'+respuesta.anuncios[i].imageURL+'"');
			html = html.concat('<strong> </strong>' + anuncio.titulo + '<br>' );
		//	html = html.concat('<FONT color="#F78A0E"><strong> Descripcion: </strong> </FONT>'  + anuncio.descripcion + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Marca: </strong>  </FONT>' + anuncio.marca + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Modelo: </strong> </FONT>' + anuncio.modelo + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Kilometros: </strong> </FONT>' + anuncio.km + '<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Precio: </strong> </FONT>' + anuncio.precio + '€<br>');
			html = html.concat('<FONT color="#F78A0E"><strong> Provincia: </strong> </FONT>' + anuncio.provincia + '<br>');
			var fecha = new Date(anuncio.creation_timestamp).toGMTString();
			html = html.concat('<FONT color="#F78A0E"> Publicado: <strong></strong> </FONT>' + fecha + '<br>');
			html = html.concat('<br>');
			html = html.concat('<a class="boton verde" onclick="getAnuncio('+idanuncio+')" id="anuncio" align=right>Ver anuncio</a><br>');
			html = html.concat('<br>');
			html = html.concat('<br>');
		});
		}

 		return html;	
	}
}



function getAnunciosCollection(url){

	$("#result_anuncios").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var response = data.anuncios;
				var anuncios= data;
				var respuesta = $.parseJSON(jqxhr.responseText);
				var anuncioCollection = new Anuncios(response, respuesta, anuncios);
				//var linkHeader = jqxhr.getResponseHeader('links');
				//console.log(linkHeader);
				//anuncioCollection.buildLinks(linkHeader);
				var html =anuncioCollection.toHTML();
				$("#result_anuncios").html(html);
				
	}).fail(function(jqXHR, textStatus) {
		
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


