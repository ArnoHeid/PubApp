var mymap;
var GEOJSON;
var api_geocoder = "http://localhost:8080/pubapp/geocoder";	/* URL für den Microservice: Geocoder  */
var api_routing = "http://localhost:8090/pubapp/routing";	/* URL für den Microservice: Routing  */
var api_poi = "http://localhost:8000/pupapp/poi";		/* URL für den Microservice: Points of Interest  */
var routing_arr;
var geocoder_json;
var lat;
var longitude;
var polyline;
var i = 0;
function init() {																// Sobald das HTML Dokument geladen wird, wird eine Karte erstellt //
	 mymap = L.map('mapid').setView([50, 8.26], 14);
	
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png').addTo(mymap);
	
	var popup = L.popup();
	
	function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(mymap);	
	}

	mymap.on('click', onMapClick);
		
	$('#Endpunkt').hide();
	$('#notification').hide();
	$('#routing').hide();
	$('#poi').hide();
}

function onEachFeature(feature, layer) {
	layer.on('click', function(e) {
		console.log(i);
		var lat = e.latlng.lat;
		var lng = e.latlng.lng;
		if (i == 0) {
		$('#startpunkt_button').val(lat + ", " + lng);
		i = i + 1;
		console.log(i);	}
		else {
		$('#endpunkt_button').val(lat + ", " + lng);
		i = 0; }
		console.log(e);
	});
}		

jQuery.support.cors = true;
	
JSONTest = function() {
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	
	if($('#startpunkt').val() == "") {
		alert("Sie müssen einen Startpunkt angeben!");
	}
	else {
	var Startpunkt = document.getElementById("startpunkt_button").value;	/* Speichert die Eingabe aus dem Feld "Startpunkt" */
	var Endpunkt = document.getElementById("endpunkt_button").value;		/* Speichert die Eingabe aus dem Feld "Startpunkt" */
	document.getElementById("startpunkt_button").value = null;				/* Setz den Wert der Textfelder auf null */
	document.getElementById("endpunkt_button").value = null;				/*	 Setz den Wert der Textfelder auf null */
	
	$.post({														/* Erste Ajax-Abfrage mit GET */
    dataType: 'jsonp',
    url: api_geocoder,
	data: 'queryString=' + Startpunkt + '&locale=de',	/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: 
	function (data) {
	console.log(data);
	GEOJSON = L.geoJSON(data, {
		onEachFeature: onEachFeature
	}).addTo(mymap);
		$('#Endpunkt').show();
		$('#notification').html("<b>Bitte wählen Sie von der Karte einen Start- und Endpunkt aus!</b>").slideDown();
		$('#routing').show();
	$('#poi').show();
		}
	})
    .done(function( data ) {
        console.log("done");
    })
    .fail( function(xhr, textStatus, errorThrown) {
        console.log(xhr);
        alert(textStatus);
    });
	}
	console.log(GEOJSON);
}

routing = function() {
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	var Startpunkt = document.getElementById("startpunkt_button").value;
	var Endpunkt = document.getElementById("endpunkt_button").value;	
	$.ajax({														/* Zweite Ajax-Abfrage mit GET */
    type: 'GET',
    dataType: 'jsonp',
    url: api_routing,
	data: 'startPoint=' + Startpunkt + '&endPoint=' + Endpunkt,	/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: 
	function (data) {
		polyline = L.geoJSON(data, {color: 'red'}).addTo(mymap);
		}
	})
    .done(function( data ) {
        console.log("done");
    })
    .fail( function(xhr, textStatus, errorThrown) {
        console.log(xhr);
        alert(textStatus);
    });
}

var test_GJSON = {"type":"FeatureCollection","features":[{"type":"Feature","properties":{"name":"REWE"},"geometry":{"type":"Point","coordinates":[8.3146131,49.9643788]}},{"type":"Feature","properties":{"name":"Nahkauf"},"geometry":{"type":"Point","coordinates":[8.32054,49.96446]}},{"type":"Feature","properties":{"name":"ADM Mainz GmbH"},"geometry":{"type":"Point","coordinates":[8.315769999999999,49.97296]}},{"type":"Feature","properties":{"name":"DHL Packstation"},"geometry":{"type":"Point","coordinates":[8.31864,49.9606]}}]};
POI = function() {
	GEOJSON = L.geoJSON(test_GJSON).addTo(mymap);
}
/*POI = function() {
	if (GEOJSON != null) {
		GEOJSON.remove();
	}				
	
	$.ajax({														
    type: 'GET',
    dataType: 'jsonp',
    url: api_poi,
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function (data) {
		console.log(data);
		GEOJSON = L.geoJSON(data).addTo(mymap);
		}
	})
    .done(function( data ) {
        console.log("done");
    })
    .fail( function(xhr, textStatus, errorThrown) {
        console.log(xhr);
        alert(textStatus);
    });
}*/