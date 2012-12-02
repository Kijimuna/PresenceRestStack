# Presence Sensor Rest Stack Example #

This example uses a very simple REST Stack to manage some presence sensor 
(PIRI1 to PIRI5 and CAM1 to CAM5).

Supported operations are
* list all sensors
* attach a sensor (use it to detect presence)
* detach a sensor (don't use it anymore)
* show attach date and last detected presence
** for a single sensor
** for all sensors


## Example ##

	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/presence/
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{}
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/sensors
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	["PIRI1","PIRI2","PIRI3","PIRI4","PIRI5","CAM1","CAM2","CAM3","CAM4","CAM5"]
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X PUT http://127.0.0.1:8080/rest/sensors/attach -d '"PIRI3"'
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{"PIRI3":{"attachDate":1354455810968,"lastPresenceDate":null}}
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X PUT http://127.0.0.1:8080/rest/sensors/attach -d '"CAM5"'
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{"PIRI3":{"attachDate":1354455810968,"lastPresenceDate":null},"CAM5":{"attachDate":1354455819359,"lastPresenceDate":null}}
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X PUT http://127.0.0.1:8080/rest/sensors/attach -d '"CAM6"'
	HTTP/1.1 400 Bad Request
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	"CAM6 is no valid sensor!"
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/presence/
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{"PIRI3":{"attachDate":1354455810968,"lastPresenceDate":1354455840000},"CAM5":{"attachDate":1354455819359,"lastPresenceDate":1354455840000}}
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/presence/sensor -d '"CAM5"'
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{"attachDate":1354455819359,"lastPresenceDate":1354455840000}
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/presence/sensor -d '"CAM3"'
	HTTP/1.1 204 No Content
	Content-Type: application/json
	Server: Jetty(7.6.7.v20120910)
	
	$ curl -i -H "Content-Type: application/json; charset=UTF-8" -X GET http://127.0.0.1:8080/rest/presence/
	HTTP/1.1 200 OK
	Content-Type: application/json
	Transfer-Encoding: chunked
	Server: Jetty(7.6.7.v20120910)
	{"PIRI3":{"attachDate":1354455810968,"lastPresenceDate":1354455840000},"CAM5":{"attachDate":1354455819359,"lastPresenceDate":1354455840000}}

