# Pubapp - The Pub Crawl App

Our take on a Pub-Crawl-App developed as part of the lecture software engineering at the [university of applied sciences Mainz](https://www.hs-mainz.de/) 

## Getting Started

The project sourcecode can be downloaded for [https://github.com/ArnoHeid/PubApp](https://github.com/ArnoHeid/PubApp) . See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Get your own API-Keys for [Graphhopper](https://graphhopper.com/#pricing) and [google](https://developers.google.com/places/web-service/?hl=de)

### Installing

```
Install Java JDK 1.8 or higher.
Download Sourcecode form github.
Import Sourecode in your development environment
```

##How to start the microservices

###Geocoder

The geocoder accepts one parameter to pass a custom properties-File

```bash
java -jar geocoder.jar config.properties
```

Sending a request to the geocoder service.

**Overview**

| Parameter | Default | Description
| --- | --- | --- |
| `queryString` | None | `queryString` is required. Main search parameter |
| `locale` | `de` | `locale` is optional. Intended for specifying the language. Values other than `de`, `en`, `fr`, `it` are rejected. |
| `api` | `nominatim` | `api` is optional. `nominatim` or `graphhopper` can be selected as the geocoding api |


Calling the geocoder microservice using a webbrowser.

```
http://localhost:8080/pubapp/geocoder?queryString=Mainz&locale=de
```

###POI
The poi service accepts one parameter specifying a config file to override the default config.properties File.

```bash
java -jar poi.jar config.properties
```

Poi service can be requested in a POST request containg the following data

**Overview**

| Parameter | Default | Description
| --- | --- | --- |
| `interests` | None | `interests` are offered in client. It is possible  to selecet diffrent interests. Each API (Google Places and overpassApi) allow more possible interests to search for|
| `coordinates` | None |`coordinates` is a list of at least two coordinates is required in order to search. The client is able to pass all nodes given from routing via POST reequest|
| `searchType` | `bbox`| `searchType` is a parameter defining what kind of search should be fulfilled. A `radius` or `bbox` search is possible. Radius means serach for each given coordinate within a certain radius. And bbox means doing a bounding box search for given bounds.|
| `api` | `overpass` | `overpass` or `google` can be selected. Please note: Google Places is not able to do a bbox search. |

**POST example with jQuery**.


```
$.ajax({														
    type: 'POST',
    url: api_poi,
    async: true,
    headers: {	
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
	dataType: 'json',
	data: {
	"interests": ["bar", "atm”],
	"coordinates": [{
		"lat": 49.9987,
		"lng": 8.2691
	}, {
		"lat": 50.0093,
		"lng": 8.2564
	}],
	"api": "overpass",
	"searchtype": "radius"
}
```

### Routing

The routing service accepts one parameter specifying a config file to override the default config.

```bash
java -jar routing.jar config.properties
```

Sending a request to the routing service.

**Overview**

| Parameter | Default | Description
| --- | --- | --- |
| `startPoint` | None | `startPoint` is required. Start of the route. E.g. `50.938056,6.956944` |
| `endPoint` | None | `endPoint` is required. End of the route. E.g. `50,8.271111` |
| `callback` | None | `callback` is optional. Wraps the JSON in the provided string and brackets. For JSONP usage. E.g. `pleaseCallMeBackBaby` |
| `vehicle` | `foot` | `vehicle` is optional. Definies the method of transport. Values other than `foot`, `car` are rejected. |
| `locale` | `de` | `locale` is optional. Intended for specifying the language. Values other than `de`, `en`, `fr`, `it` are rejected. |

**Example 1**, minimal parameters.

```
http://localhost:8090/pubapp/routing?startPoint=50.938056,6.956944&endPoint=50,8.271111
```

**Example 2**, common parameters.

```
http://localhost:8090/pubapp/routing?callback=pleaseCallMeBackBaby&startPoint=50.938056,6.956944&endPoint=50,8.271111&locale=de&vehicle=car
```

## Deployment

Adjust main.js to fit your server addresses.

```
var api_geocoder = "your geocoder adress";
var api_routing = "your routing adress";
var api_poi = "your poi adress";
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Jersey](https://jersey.java.net/) - RESTful Web Services

###APIs

* [Graphhopper](https://graphhopper.com/)
* [Nominatim](http://nominatim.openstreetmap.org/)
* [google](http://www.google.de/)



## Authors

* **Arno** - *geocoder* - [ArnoHeid](https://github.com/ArnoHeid)
* **Carolin** - *poi* - [crottm](https://github.com/crottm)
* **Lorenz** - *client* - [Lorenzgrauf](https://github.com/Lorenzgrauf)
* **Sarah** - *routing* - [SarahKoziol](https://github.com/SarahKoziol)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Prof. Böhm 
* Herr Neis


