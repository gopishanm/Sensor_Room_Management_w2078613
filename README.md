Sensor & Room Management API (JAX-RS + Jetty)

The project is a RESTful API built using JAX-RS (Jersey) and Jetty. It handles:
- Rooms
- Sensors assigned to rooms
- Historical sensor readings

The API is designed using REST concepts, with appropriate HTTP methods, response codes, and JSON format in replies. More advanced options like error handling, filtering, and logging are integrated for better functionality and monitoring capabilities.

How to build instructions:

1. Duplicate/clone the repository

2. Clean and build

3. Run the server 

4. Open: http://localhost:8092/api/v1

Curl Commands:

1.Create a room:

curl -X POST http://localhost:8092/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"name": "Room A"}'

2. Get all rooms
curl http://localhost:8092/api/v1/rooms

3. Create a Sensor (linked to roomId = 1)
curl -X POST http://localhost:8092/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"type": "temperature", "roomId": 1}'

4. Add a Reading to Sensor 1
curl -X POST http://localhost:8092/api/v1/sensors/1/readings \
-H "Content-Type: application/json" \
-d '{"value": 23.5}'

5. Get Sensor Readings
curl http://localhost:8092/api/v1/sensors/1/readings

Report:

1.JAX-RS resources are by default associated with the per-request lifecycle policy, which means that a new instance is created per each HTTP request and cannot be treated as a singleton. As a result, any instance variable declared within the class is a specific attribute of the certain request and cannot be utilized across other requests. Consequently, the instance variable is thread-safe since it is inaccessible to two threads at once.
However, if it is required to store some data received from one HTTP request to the other, there should be introduced some storage where such data could be stored between different requests. They include static lists and maps. However, static fields are accessible to several threads, which might result in possible data inconsistency problems.

2."Hypermedia” is an essential component of RESTful architecture because, with its help, the server is able to attach links within response data, which allow the client to interact with other resources and take certain actions.
As opposed to conventional documentation, such an approach provides developers with a lot more flexibility because changes on the server do not necessarily require any changes at the client side; everything needed can be obtained from response data itself.

3.Restricting the output to return only room IDs will result in reduced output size and therefore an efficient and bandwidth-friendly network, particularly where large amounts of data are involved. The downside of this is that the client will have to make additional requests to retrieve details about each room.

4.The DELETE request is idempotent because the first request will delete the room, whereas any further requests will not change anything. The reason is that after one-time deletion of the room, there will be no sense in deleting it once again.

5.The @Consumes(MediaType.APPLICATION_JSON) annotation restricts the POST function to accept only JSON requests. Should the client provide the request in other formats, such as plain text or XML, there would be no match between the request from the client and the POST function. In this regard, the server will respond to the client request with the HTTP 415 code. The HTTP 415 code implies that the media type being requested is not supported on the server side.

6.It is advised to use @QueryParam, as it considers that this parameter is optional within the collection URI. In contrast, the usage of the path method implies that there is another resource. The use of query parameters implies that the client queries the collection with some modifications.

7.Sub Resource Locator is a way to optimize API architecture by moving all responsibilities that involve dealing with nested resources from a huge controller into other classes responsible for their specific functionality. As a result, such a solution helps developers have better modularity, legibility, and manageability in code. Each resource class implements only its logic (for example, working with sensors), which makes the code simpler and not as complicated as it could be when implemented in one big class. In addition, the approach provides good scalability, making it possible to add sub-resources without changing anything else in the existing code.

8.HTTP 422 unprocessable entity is more appropriate semantically since the syntax is correct and the resource is available; however, the content of the request is wrong. The reason for the failure is that there is a JSON body that refers to something (for example, roomId) which is not valid. HTTP 404 Not Found should be used when there is no such URL that was requested, whereas using 422 makes it evident that there was some problem with the semantics of the provided information.

9.The presentation of stack traces within the Java application involves many security issues, as this action may provide valuable internal data regarding the implementation of the Java application. To begin with, the stack trace provides a lot of information, including the names of classes, packages, files, used frameworks and libraries, among others, which could be exploited by the attacker to identify any weaknesses in the targeted system. Moreover, presenting the stack trace enables identification of the methods invoked, thereby allowing the attacker to understand how the application operates.

10.Application of JAX-RS filters for cross-cutting concerns, such as logging, is efficient since the logic for the concern will be consolidated into a single location rather than duplicating the logging logic in multiple resource classes. This approach is advantageous from the perspective of maintenance since there is no requirement to modify the logging functionality across several locations. Additionally, consistency can be guaranteed since logging will be done consistently for each request and response.
