function init() {
	var mymap = L.map('mapid').setView([50, 8.26], 14);
	
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png').addTo(mymap);
	
	var popup = L.popup();
	
	function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(mymap);
	}

	mymap.on('click', onMapClick);
}

/* $(document).ready(function() {
	$.ajax({
		type: 'POST',
		url: 'microservice.java',
		data: &string = $('#startpunkt').value + $('#endpunkt').value,
		datatype: 'json',
		
		
	})

}); */
	
function button() {
	var Startpunkt = document.getElementById("startpunkt").value;
	var Endpunkt = document.getElementById("endpunkt").value;
		alert("Startpunkt: " + Startpunkt  + "Endpunkt: " + Endpunkt);
	document.getElementById("startpunkt").value = "";
	document.getElementById("endpunkt").value = "";
}