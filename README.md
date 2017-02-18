# Pubapp - The Pub Crawl App

Our take on a Pub-Crawl-App developed as part of the lecture software engineering at the [university of applied sciences Mainz](https://www.hs-mainz.de/) 

## Getting Started

The project sourcecode can be downloaded for [https://github.com/ArnoHeid/PubApp](https://github.com/ArnoHeid/PubApp) . See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Get your own API-Keys for [Graphhopper](https://graphhopper.com/#pricing) and [google](...)

### Installing

```
Install Java JDK 1.8 or higher.
Download Sourcecode form github.
Import Sourecode in your development environment
```

##How to start the microservices

###Geocoder

The geocoder accepts one parameter to pass a custom properties-File

```
java -jar geocoder.jar custom.properties
```

Calling the geocoder microservice using a webbrowser.

```
http://localhost:8080/pubapp/geocoder?queryString=Mainz&locale=de
```

###POI


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
* **Carolin** - *poi* - [carolinrottmann](https://github.com/carolinrottmann)
* **Lorenz** - *client* - [Lorenzgrauf](https://github.com/Lorenzgrauf)
* **Sarah** - *routing* - [SarahKoziol](https://github.com/SarahKoziol)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Prof. Böhm 
* Herr Neis


