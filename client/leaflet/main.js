var mymap;
var GEOJSON;
var api_geocoder = "http://localhost:8080/pubapp/geocoder";	/* URL für den Microservice: Geocoder  */
var api_routing = "http://localhost:8090/pubapp/routing";	/* URL für den Microservice: Routing  */
var api_poi = "http://localhost:8000/pubapp/poi";		/* URL für den Microservice: Points of Interest  */
var routing_arr;
var geocoder_json;
var lat;
var longitude;
var polyline;
var i = 0;
var Startpunkt;
var Endpunkt;
var startclicklat;
var startclicklng;
var endclicklat;
var endclicklng;
var place;
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
	
	$('#Startpunkt').hide();	
	$('#Endpunkt').hide();
	$('#notification').hide();
	$('#routing').hide();
	$('#poi').hide();
}

function onEachFeature(feature, layer) {
	layer.on('click', function(e) {
		console.log(i);
		if (i == 0) {
		startclicklat = e.latlng.lat;
		startclicklng = e.latlng.lng;
		$('#startpunkt_button').val(startclicklat + ", " + startclicklng);
		i = i + 1;
		console.log(i);	}
		else {
		endclicklat = e.latlng.lat;
		endclicklng = e.latlng.lng;
		$('#endpunkt_button').val(endclicklat + ", " + endclicklng);
		i = 0; }
		console.log(e);
	});
}		

jQuery.support.cors = true;
	
geocoder = function() {
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	
	if($('#search_button').val() == "") {
		alert("Sie müssen einen Startpunkt angeben!");
	}
	else {
	place = document.getElementById("search_button").value;	
	document.getElementById("startpunkt_button").value = null;				/* Setz den Wert der Textfelder auf null */
	document.getElementById("endpunkt_button").value = null;				/*	 Setz den Wert der Textfelder auf null */
	
	$.post({														/* Erste Ajax-Abfrage mit GET */
    dataType: 'jsonp',
    url: api_geocoder,
	data: 'queryString=' + place + '&locale=de',	/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: 
	function (data) {
	console.log(data);
	GEOJSON = L.geoJSON(data, {
		onEachFeature: onEachFeature
	}).addTo(mymap);
		$('#Startpunkt').show();
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

/*var test_GJSON = {"type":"FeatureCollection","features":[{"type":"Feature","properties":{"name":"REWE"},"geometry":{"type":"Point","coordinates":[8.3146131,49.9643788]}},{"type":"Feature","properties":{"name":"Nahkauf"},"geometry":{"type":"Point","coordinates":[8.32054,49.96446]}},{"type":"Feature","properties":{"name":"ADM Mainz GmbH"},"geometry":{"type":"Point","coordinates":[8.315769999999999,49.97296]}},{"type":"Feature","properties":{"name":"DHL Packstation"},"geometry":{"type":"Point","coordinates":[8.31864,49.9606]}}]};
POI = function() {
	GEOJSON = L.geoJSON(test_GJSON).addTo(mymap);
}*/

clearall = function() {
		console.log("clear");
		if(GEOJSON != null) {
		GEOJSON.remove();
		polyline.remove(); }
}

POI = function() {
	if (GEOJSON != null) {
		GEOJSON.remove();
	}				
	
	$.ajax({														
    type: 'GET',
    dataType: 'jsonp',
    url: api_poi,
	data: 'interest=bar' + '&startLat=' + startclicklat + '&startLng=' + startclicklng + '&endLat=' + endclicklat + '&endLng=' + endclicklng,
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
}