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
var interest = new Array();
var intereststring;
var layer_marker1;
var layer_marker2;
function init() {																// Sobald das HTML Dokument geladen wird, wird eine Karte erstellt //
	 mymap = L.map('mapid').setView([50, 8.26], 14);
	
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: 'Map by Leaflet; Ein Studentenprojekt der Hochschule Mainz: <a href=information.html>Information</a>'
	}).addTo(mymap);
	
	var popup = L.popup();
	
	function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent('<div class="popup">You clicked the map at ' + e.latlng.toString() + '</div>')
        .openOn(mymap);
		if (i == 0) {
		if (layer_marker1 != null) {
			layer_marker1.remove();
		} 
		layer_marker1 = new L.marker(e.latlng).addTo(mymap);
		startclicklat = e.latlng.lat;
		startclicklng = e.latlng.lng;
		$('#startpunkt_button').val(startclicklat + ", " + startclicklng);
		i = i + 1;
		}
		else {
			if (layer_marker2 != null) {
			layer_marker2.remove();
		} 
		layer_marker2 = new L.marker(e.latlng).addTo(mymap);
		endclicklat = e.latlng.lat;
		endclicklng = e.latlng.lng;
		$('#endpunkt_button').val(endclicklat + ", " + endclicklng);
		i = 0; }
	}

	mymap.on('click', onMapClick);
	
	$('#notification').hide();
	$('#Routing').hide();
	$('#poiauswahl').hide();
}

function getCoords(feature, layer) {
	layer.bindPopup('<div class="popup">' + feature.properties.name + '</div>');
	layer.on('click', function(e) {
		if (i == 0) {
		startclicklat = e.latlng.lat;
		startclicklng = e.latlng.lng;
		$('#startpunkt_button').val(startclicklat + ", " + startclicklng);
		i = i + 1;
		$('#notification').html("<b>Bitte wählen Sie jetzt von der Karte einen Endpunkt aus!</b>");
		}
		else {
		endclicklat = e.latlng.lat;
		endclicklng = e.latlng.lng;
		$('#endpunkt_button').val(endclicklat + ", " + endclicklng);
		i = 0;
		console.log(e);
		}
	});
}

clearall = function() {
		console.log("clear");
		if(GEOJSON != null) {
		GEOJSON.remove();
		polyline.remove(); }
}

selectall = function() {
	for (c = 0; c < document.forms[0].length; c++) {
		document.forms[0].elements[c].checked = true;
	}
}

deselectall = function() {
	for (c = 0; c < document.forms[0].length; c++) {
		document.forms[0].elements[c].checked = false;
	}
}

/*togglesidebar = function() {
	$('#sidebar').toggle();
}*/


		

jQuery.support.cors = true;
	
geocoder = function() {
	if (layer_marker1 != null) {
			layer_marker1.remove();
		} 
	if (layer_marker2 != null) {
			layer_marker2.remove();
		} 
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
	data: 'queryString=' + place + '&locale=de',				/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: 
	function (data) {
	console.log(data);
	GEOJSON = L.geoJSON(data, {
		onEachFeature: getCoords
	}).addTo(mymap);
	mymap.fitBounds(GEOJSON.getBounds());
		$('#notification').html("<b>Bitte wählen Sie jetzt von der Karte einen Start- und Endpunkt aus!</b>").slideDown();
		$('#Routing').show();
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
	if (polyline != null) {
		polyline.remove();
	}
	var Startpunkt = document.getElementById("startpunkt_button").value;
	var Endpunkt = document.getElementById("endpunkt_button").value;
	console.log(Startpunkt);
	console.log(Endpunkt);
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
		console.log(polyline.getBounds());
		$('#poi').show();
		$('#poiauswahl').show();
		$('#poi_bbx').show();
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


POI_BBX = function() {
	if (layer_marker1 != null) {
			layer_marker1.remove();
		} 
	if (layer_marker2 != null) {
			layer_marker2.remove();
		} 
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	for (c = 0; c < document.forms[0].length; c++) {
		
		if (document.forms[0].elements[c].checked) {
			interest[c] = document.getElementById('interest' + (c+1)).value;
		}
	}
	$.ajax({														
    type: 'GET',
    dataType: 'jsonp',
    url: api_poi,
	data: 'criteria={"interests":["' + interest[0] + '","' + interest[1] + '","' + interest[2] + '","' + interest[3] + '","' + interest[4] + '","' + interest[5] + '"],"coordinates":[{"lat":' + polyline.getBounds().getSouthWest().lat + ',"lng":' + polyline.getBounds().getSouthWest().lng + '},{"lat":' + polyline.getBounds().getNorthEast().lat + ',"lng":' + polyline.getBounds().getNorthEast().lng + '}]}&searchtype=bbox',
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function (data) {
		console.log(data);
		GEOJSON = L.geoJSON(data, {
		onEachFeature: function (feature, layer) {
        layer.bindPopup('<div class="popup">' + feature.properties.interest + '<br>' + feature.properties.name + '<p>' + feature.properties.openingHours + '<p></div>');
		},
		pointToLayer: function(feature, latlng) {
			if (feature.properties.interest == 'Bar') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/beer.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Restaurant') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/restaurant.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'ATM') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/atm.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Nachtclub') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/club.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Cafe') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/cafe.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			}
		}).addTo(mymap);
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

POI = function() {
	if (layer_marker1 != null) {
			layer_marker1.remove();
		} 
	if (layer_marker2 != null) {
			layer_marker2.remove();
		} 
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	for (c = 0; c < document.forms[0].length; c++) {
		
		if (document.forms[0].elements[c].checked) {
			interest[c] = document.getElementById('interest' + (c+1)).value;
		}
	}
	$.ajax({														
    type: 'GET',
    dataType: 'jsonp',
    url: api_poi,
	data: 'criteria={"interests":["' + interest[0] + '","' + interest[1] + '","' + interest[2] + '","' + interest[3] + '","' + interest[4] + '","' + interest[5] + '"],"coordinates":[{"lat":' + startclicklat + ',"lng":' + startclicklng + '},{"lat":' + endclicklat + ',"lng":' + endclicklng + '}]}',
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function (data) {
		console.log(data);
		GEOJSON = L.geoJSON(data,
		{onEachFeature: function (feature, layer) {
			layer.bindPopup('<div class="popup">'  + feature.properties.interest + '<br>' + feature.properties.name + '<p>' + feature.properties.openingHours + '<p></div>');
			},
		pointToLayer: function(feature, latlng) {
			if (feature.properties.interest == 'Bar') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/beer.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Restaurant') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/restaurant.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'ATM') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/atm.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Nachtclub') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/club.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'Cafe') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/cafe.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			}
		}).addTo(mymap);
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

