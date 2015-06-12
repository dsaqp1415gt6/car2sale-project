var URL = 'http://localhost:8080/car2sale-api/users';
var lastFilename;
var username = getCookie('username');

$('form').submit(function(e){
	e.preventDefault();
	$('progress').toggle();

	var formData = new FormData($('form')[0]);
	$.ajax({
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
		$('<div class="alert alert-danger"> <strong></strong> Registro ok!!  </div>').appendTo($("#result_registro"));
		$('progress').toggle();
		$('form')[0].reset();
	})
    .fail(function (jqXHR, textStatus) {
    	alert("Lo sentimos, Usuario ya registrado");
		console.log(textStatus);
	});
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
