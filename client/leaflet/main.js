var mymap;
var GEOJSON;
var api_geocoder = "http://143.93.114.139/geocoder";	/* URL für den Microservice: Geocoder  */
var api_routing = "http://143.93.114.139/routing";	/* URL für den Microservice: Routing  */
var api_poi = "localhost:8000/pubapp/poi";		/* URL für den Microservice: Points of Interest  */
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
var tmpStart;
var tmpEnde;
var myJsonString;
var postPOIstring = '';

function init() {																// Sobald das HTML Dokument geladen wird, wird eine Karte erstellt //
	 mymap = L.map('mapid').setView([50, 8.26], 14);
	
	/*L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: 'Map by Leaflet; Ein Studentenprojekt der Hochschule Mainz: <a href=information.html>Information</a>'
	}).addTo(mymap);*/
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>, Powered by Google and Overpass',
            maxZoom: 18,
            id: 'mapbox.light',
            accessToken: 'pk.eyJ1IjoibG9yb2RvbiIsImEiOiJjaXo5a25uMzEwMDFlMzNvMHRrN3Rpd251In0.rbyeEiTl7Rzr7R-YlxwzSQ'
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
		tmpStart = startclicklat + ", " + startclicklng;
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
		tmpEnde = endclicklat + ", " + endclicklng;
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
				tmpStart = startclicklat + ", " + startclicklng;
		i = i + 1;
		$('#notification').html("<b>Bitte wählen Sie jetzt von der Karte einen Endpunkt aus!</b>");
		}
		else {
		endclicklat = e.latlng.lat;
		endclicklng = e.latlng.lng;
		$('#endpunkt_button').val(endclicklat + ", " + endclicklng);
				tmpEnde = endclicklat + ", " + endclicklng;
		i = 0;
		console.log(e);
		}
	});
}

clearall = function() {
		mymap.remove();
		mymap = L.map('mapid').setView([50, 8.26], 14);
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>, Powered by Google and Overpass',
            maxZoom: 18,
            id: 'mapbox.light',
            accessToken: 'pk.eyJ1IjoibG9yb2RvbiIsImEiOiJjaXo5a25uMzEwMDFlMzNvMHRrN3Rpd251In0.rbyeEiTl7Rzr7R-YlxwzSQ'
        }).addTo(mymap);
	$('#endpunkt_button').val(null);
	$('#startpunkt_button').val(null);
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
	tmpStart = data.features[0].geometry.coordinates[1] + ',' + data.features[0].geometry.coordinates[0];
	startclicklat = data.features[0].geometry.coordinates[1];
	startclicklng = data.features[0].geometry.coordinates[0];
	document.getElementById("startpunkt_button").value = data.features[0].geometry.coordinates[1] + ',' + data.features[0].geometry.coordinates[0];
	i = i + 1;
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
	console.log(tmpStart);
	tmpEnde = document.getElementById("endpunkt_button").value;
	console.log(tmpEnde);
	$.ajax({														/* Zweite Ajax-Abfrage mit GET */
    type: 'GET',
    dataType: 'jsonp',
    url: api_routing,
	data: 'startPoint=' + tmpStart + '&endPoint=' + tmpEnde,	/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: 
	function (data) {
		console.log(data.features[0].geometry.coordinates[0][0]);
		for (a = 0; a < data.features[0].geometry.coordinates.length; a++) {
			for (b = 0; b <= 1; b++) {
				console.log(b);
				if (b == 0) {
				var lngPOI = '"lng":' + JSON.stringify(data.features[0].geometry.coordinates[a][b]);
				}
				else {
				var latPOI = '"lat":' + JSON.stringify(data.features[0].geometry.coordinates[a][b]);
				}
			}
			postPOIstring = postPOIstring + '{' + latPOI + ',' + lngPOI + '},';
		}
		postPOIstring = postPOIstring.slice(0, -1);
		console.log(postPOIstring);
		polyline = L.geoJSON(data, {color: 'red'}).addTo(mymap);
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
			interest.push(document.getElementById('interest' + (c+1)).value);
		}
	}
	console.log(interest);
	myJsonString = JSON.stringify(interest);
	$.ajax({														
    type: 'POST',
    dataType: 'json',
    url: api_poi,
	data: 'criteria={"interests":' + myJsonString + ',"coordinates":[{"lat":' + polyline.getBounds().getSouthWest().lat + ',"lng":' + polyline.getBounds().getSouthWest().lng + '},{"lat":' + polyline.getBounds().getNorthEast().lat + ',"lng":' + polyline.getBounds().getNorthEast().lng + '}]}&searchtype=bbox',
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function (data) {
		console.log(data);
		GEOJSON = L.geoJSON(data, {
		onEachFeature: function (feature, layer) {
        layer.bindPopup('<div class="popup">' + feature.properties.interest + '<br>' + feature.properties.name + '<p>' + feature.properties.openingHours + '<p></div>');
		},
		pointToLayer: function(feature, latlng) {
			if (feature.properties.interest == 'bar') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/beer.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'restaurant') {
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
			interest.push(document.getElementById('interest' + (c+1)).value);
		}
	}
	myJsonString = JSON.stringify(interest);
	$.ajax({
	type: 'POST',
    dataType: 'json',
    url: api_poi,
	data: 'criteria={"interests":' + myJsonString + ',"coordinates":[' + postPOIstring + ']}&api=overpass&searchtype=radius',
	success: function (data) {
		GEOJSON = L.geoJSON(data,
		{onEachFeature: function (feature, layer) {
			layer.bindPopup('<div class="popup">'  + feature.properties.interest + '<br>' + feature.properties.name + '<p>' + feature.properties.openingHours + '<p></div>');
			},
		pointToLayer: function(feature, latlng) {
			if (feature.properties.interest == 'bar') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/beer.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'restaurant') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/restaurant.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'atm') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/atm.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'night_club') {
				var barIcon = L.icon({
					iconUrl: './leaflet/png/club.png',
					iconAnchor: [13, 27],
					popupAnchor:  [-3, -76]
				});
				return L.marker(latlng, {icon: barIcon});
				}
			if (feature.properties.interest == 'cafe') {
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
