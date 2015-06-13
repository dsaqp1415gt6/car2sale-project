var URL = 'http://www.tgrupo6.dsa:8080/car2sale-api/anuncios';
var lastFilename;
var username = getCookie('username');
var password = getCookie('password');
$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(username+':'+password) }
})
$(document).ready(function(){
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
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
	   document.getElementById("m7").innerHTML = 'Scenic';
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
});


$('form').submit(function(e){

   
	var marca = document.getElementById("marca");
	var selmarca = marca.options[marca.selectedIndex].text;
	var modelo = document.getElementById("modelo");
	var selmodelo = modelo.options[modelo.selectedIndex].text;
	var provincia = document.getElementById("provincia");
	var selprovincia = provincia.options[provincia.selectedIndex].text;
	
	if((selmarca == 'Marcas') || (selmodelo=='Modelos') || (selprovincia=='provincias')){
		
		if (selmarca == 'Marcas'){
		alert("Debes seleccionar la Marca del vehiculo");
		}
		else if (selmodelo== 'Modelos'){
		alert("Debes seleccionar el Modelo del vehiculo");
		}
		else if (selprovincia='provincias'){
		alert("Debes seleccionar una Provincia");
		}		
	}
	else if (isNaN($('#km').val()) || isNaN($('#precio').val()) ){
        if (isNaN($('#km').val())){
		alert("El campo Kilometros debe ser un entero");
		}
		else if (isNaN($('#precio').val())){
		alert("El campo Precio debe ser un entero");
		}	
	}
	else{
	e.preventDefault();
	$('progress').toggle();

	var formData = new FormData($('form')[0]);
	$.ajax({
		headers : {
			'Authorization' : "Basic " + btoa(username + ':' + password)
		},
		url: URL,
		type: 'POST',
		xhr: function() {  
	    	var myXhr = $.ajaxSettings.xhr();
	        if(myXhr.upload){ 
	            myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
	        }
	        return myXhr;
        },
		crossDomain : true,
		data: formData,
		cache: false,
		contentType: false,
        processData: false
	})
	.done(function (data, status, jqxhr) {
		var response = $.parseJSON(jqxhr.responseText);
		lastFilename = response.filename;
		$('<div class="alert alert-danger"> <strong></strong> Registro del Anuncio OK!! </div>').appendTo($("#result_registro_anuncio"));
		$('progress').toggle();
		$('form')[0].reset();
	})
    .fail(function (jqXHR, textStatus) {
    	alert("Error en el Registro del Anuncio");
		console.log(textStatus);
	});
	}
});

function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
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