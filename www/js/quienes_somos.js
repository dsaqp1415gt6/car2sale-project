var username = getCookie('username');

$(document).ready(function(){
	$('<FONT FACE="impact" align="right" SIZE=4 COLOR="#FBEFEF">Bienvenido, '+username+' Elige Categoria</FONT>').appendTo($('#logeo_result'));
	
});

function getCookie(cname) {

    var name = cname + "=";

    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
		console.log (ca[i]);
        while (c.charAt(0)==' ') c = c.substring(1);{
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);{}}
    }
    return "";
}