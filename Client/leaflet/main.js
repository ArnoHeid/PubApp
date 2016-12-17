var mymap;
var GEOJSON;

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
	
	 GEOJSON = L.geoJSON();	/*Erstellt einen Layer für die Darstellung von GEOJSON Daten*/		
	
}
	
	
	
	
JSONTest = function() {
	
	var api_geocoder = "http://localhost:8080/pubapp/geocoder";	/* URL für den Microservice: Geocoder  */
	/*var api2 = "http://localhost:8080/pubapp/routing";	/* URL für den Microservice: Routing  */
	/*var api3 = "http://localhost:8080/pubapp/poi";		/* URL für den Microservice: Points of Interest  */
	
	var Startpunkt = document.getElementById("startpunkt").value;	/* Speichert die Eingabe aus dem Feld "Startpunkt" */
	var Endpunkt = document.getElementById("endpunkt").value;		/* Speichert die Eingabe aus dem Feld "Startpunkt" */
	/* alert("Startpunkt: " + Startpunkt + "Endpunkt: " + Endpunkt); */
	document.getElementById("startpunkt").value = null;				/* Setz den Wert der Textfelder auf null */
	document.getElementById("endpunkt").value = null;				/*	 Setz den Wert der Textfelder auf null */
	
	$.ajax({														/* Erste Ajax-Abfrage mit GET */
    type: 'GET',
    dataType: 'jsonp',
    url: api_geocoder,
	data: 'queryString=Wiesbaden&locale=de',	/* Schickt die Eingaben als jQuery an den Microservice */
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function (data) {
		L.geoJSON(data, {
		style: function (feature) {
        return {color: feature.properties.color};
		}
		}).bindPopup(function (layer) {
			return layer.feature.properties.description;
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

	/*$.ajax({														 Zweite Ajax-Abfrage mit GET 
    type: "GET",
    dataType: 'jsonp',
    url: api2,
	data: 'queryText={"queryString" : "Wiesbaden","locale": "de"}',
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function(data) {
		console.log(data);
	}
	})
    .done(function( data ) {
        console.log("done");
    })
    .fail( function(xhr, textStatus, errorThrown) {
        console.log(xhr);
        alert(textStatus);
    });

	$.ajax({														/* Dritte Ajax-Abfrage mit GET 
    type: "GET",
    dataType: 'jsonp',
    url: api3,
	data: 'queryText={"queryString" : "Wiesbaden","locale": "de"}',
    crossDomain : true,
    xhrFields: { withCredentials: true },
	success: function(data) {
		console.log(data);
	}
	})
    .done(function( data ) {
        console.log("done");
    })
    .fail( function(xhr, textStatus, errorThrown) {
        console.log(xhr);
        alert(textStatus);
    }); */
	
}