var api_geocoder = "http://143.93.114.139/geocoder";	/* URL für den Microservice: Geocoder  */
var api_routing = "http://143.93.114.139/routing";	/* URL für den Microservice: Routing  */
var api_poi = "http://localhost:8000/pubapp/poi/";		/* URL für den Microservice: Points of Interest  */
var mymap;
var GEOJSON;
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
var vehicle = 'foot';

function init() {																// Creates the map when the website is opened //
	 mymap = L.map('mapid').setView([50, 8.26], 14);
	
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>, Powered by Google and Overpass',
            maxZoom: 18,
            id: 'mapbox.streets',
            accessToken: 'pk.eyJ1IjoibG9yb2RvbiIsImEiOiJjaXo5a25uMzEwMDFlMzNvMHRrN3Rpd251In0.rbyeEiTl7Rzr7R-YlxwzSQ'
        }).addTo(mymap);														//Loads the Design from Mapbox and adds the layer to the map //
	

	var popup = L.popup();
	
	function onMapClick(e) {																			//Each click on the map saves the coordinates of this point, shows it in a popup
    popup																								//and gets displayed in the textfield.
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

clearall = function() {																//Reloads the complete map
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
	document.getElementById("startpunkt_button").value = null;				/* Sets the textvalue to null */
	document.getElementById("endpunkt_button").value = null;				/*	 Sets the textvalue to null */
	$.post({																/* First Ajaxquery with GET */
    dataType: 'jsonp',
    url: api_geocoder,
	data: 'queryString=' + place + '&locale=de',							/* Sends the data to the Microservice */
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
}

routing = function() {
	if (document.getElementById('routingswitch').checked == true) {
		vehicle = 'car';
	}
	if (GEOJSON != null) {
		GEOJSON.remove();
	}
	if (polyline != null) {
		polyline.remove();
	}
	tmpEnde = document.getElementById("endpunkt_button").value;		
	$.ajax({																			 /* Second Ajaxquery with GET */
    type: 'GET',                                                                        
    dataType: 'jsonp',                                                                 
    url: api_routing,                                                                   
	data: 'startPoint=' + tmpStart + '&endPoint=' + tmpEnde + '&vehicle=' + vehicle,	/* Sends the data to the Microservice */
    crossDomain : true,                                                                 
    xhrFields: { withCredentials: true },
	success: 
	function (data) {																	/* Creates a GEOJSON from the resulting Data for the later use in the POI Function */
		for (a = 0; a < data.features[0].geometry.coordinates.length; a++) {			
			for (b = 0; b <= 1; b++) {
				if (b == 0) {
				var lngPOI = '"lng":' + JSON.stringify(data.features[0].geometry.coordinates[a][b]);
				}
				else {
				var latPOI = '"lat":' + JSON.stringify(data.features[0].geometry.coordinates[a][b]);
				}
			}
			postPOIstring = postPOIstring + '{' + latPOI + ',' + lngPOI + '}' + ',';
		}
		//postPOIstring = postPOIstring.slice(0, -1);
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
	interest.splice(0, interest.length);
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
    url: api_poi,
    async: true,
    headers: {	
				'Accept': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
			},
	dataType: 'json',
	contentType: 'application/x-www-form-urlencoded',
	crossDomain : true,     
	xhrFields: {
        withCredentials: true
    },
	data: 'criteria={"interests":' + myJsonString + ',"coordinates":[{"lat":' + polyline.getBounds().getSouthWest().lat + ',"lng":' + polyline.getBounds().getSouthWest().lng + '},{"lat":' + polyline.getBounds().getNorthEast().lat + ',"lng":' + polyline.getBounds().getNorthEast().lng + '}]}&searchtype=bbox',
	success: function (data) {
		console.log(data);
		GEOJSON = L.geoJSON(data, {
		onEachFeature: function (feature, layer) {
        layer.bindPopup('<div class="popup">' + feature.properties.interest + '<br>' + feature.properties.name + '<br>' + feature.properties.isOpen + '<br>' + feature.properties.openingHours + '</div>');
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

POI = function() {
	interest.splice(0, interest.length);
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
	crossDomain : true,
    xhrFields: { withCredentials: true },
	data: 'criteria={"interests":' + myJsonString + ',"coordinates":[' + postPOIstring + ']}&api=overpass&searchtype=radius',
	success: function (data) {
		GEOJSON = L.geoJSON(data,
		{onEachFeature: function (feature, layer) {
			layer.bindPopup('<div class="popup">'  + feature.properties.interest + '<br>' + feature.properties.name + '<br>' + feature.properties.isOpen + '<br>' + feature.properties.openingHours + '</div>');
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
