# CallNotificationSystem

This project is an example usage of **RestAPI** & **Websockets** in **Java Spring Framework**.

**Technology Stack**

 - Java Spring Framework 
 - Swagger UI
 - WebSocket
 - SockJS/StompJS (Client Side)
 - JPA Hybernate
 - Lombok
 - H2 In-Memory Database
 - JUnit
 - Mockito
 - Spring Web Security
 

# **@Abstract**
The main function of the system is to provide a notification infrastructure that handles the notification processes  of missed calls. User registration, call and delivery history creation requests are given by an imaginary telecommunication company via provided **RestAPI**. The notification messages are delivered to user via **WebSocket** clients which are auhanticated with **phone numbers** and **passwords**. 

# **@Usage**
The project can be used with docker image which is provided in [DockerHub](https://hub.docker.com/r/sahiniscimen/callnotificationsystem). 
The source code is provided in [GitHub](https://github.com/sahiniscimen/CallNotificationSystem). It can also be re-builded from the code.

First pull the docker image,

    $ docker pull sahiniscimen/callnotificationsystem:0.0.1-SNAPSHOT
(To re-build the code in the main directory of the source code,

    $ mwn spring-boot:build-image
the image is going to be created in docker.io/library/callnotificationsystem:0.0.1-SNAPSHOT and the image id is going to be logged on the command line.)

then run the docker image with,

    $ docker run -p 8080:8080 callnotificationsystem:0.0.1-SNAPSHOT
now the system is ready to use with internet browser ( or any API client like [Postman](https://www.postman.com/downloads/) can be used for RestAPI calls)

### Notification API

 - To access swagger-ui [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
 - To use APIs with **Postman**  [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/4b5f02ba61de2dc88560)
	
**Authantication Scheme:**  [**Basic authentication**](https://en.wikipedia.org/wiki/Basic_access_authentication)
		**Username:** user
		**Password:** password

### WebSocketClient

 - To use embedded client: [http://localhost:8080/](http://localhost:8080/)
 - Authenticated with in database **phone number** and **password** registered via **NotificationAPI**

**WebSecurity Login Authentication**
		**Username:** user
		**Password:** password
		
# @How it works?

**Best Case Scenario**
<div class="mermaid">
sequenceDiagram
Company      		 -->> NotificationSystem: createUser(calledPhoneNumber,password)
NotificationSystem   ->> Company: HTTP.200(OK)
Company      		 -->> NotificationSystem: createCallHistory(called,caller)
NotificationSystem	 ->> Company: HTTP.200(OK)
CalledUser 			 -->> NotificationSystem: subscribe(calledPhoneNumber, password)
NotificationSystem	 ->> CalledUser: Authenticated
NotificationSystem	 ->> CalledUser: NotificationMessages(Call)
Company      		 -->> NotificationSystem: createUser(callerPhoneNumber,password)
NotificationSystem   ->> Company: HTTP.200(OK)
Company      		 -->> NotificationSystem: createDeliveryHistory(caller,called)
NotificationSystem   ->> Company: HTTP.200(OK)
CallerUser 			 -->> NotificationSystem: subscribe(callerPhoneNumber, password)
NotificationSystem	 ->> CallerUser: Authenticated
NotificationSystem	 ->> CallerUser: NotificationMessages(Delivery)
</div>

The system can be consumed in the best case scenario for a quick start.

The system has multiple scenarios on the different layers. One can follow the scenarios chronologically with the following sequence diagrams. In the diagrams dotted line means that the response of the process has more than one possibility. Solid line means that the response is absolute.

**Company to NoticationAPI in CreateUser**
<div class="mermaid">
sequenceDiagram
Company      	-->> NoticationAPI: createUser(phoneNumber,password)
NoticationAPI	-x Company: HTTP.400(Bad Request)
NoticationAPI	-->> ServiceLayer:  createUser(phoneNumber,password)
ServiceLayer 	-->> RepositoryLayer: findUser(phoneNumber)
RepositoryLayer ->> ServiceLayer : NotFound
ServiceLayer    ->> NoticationAPI: OK
NoticationAPI   ->> Company: HTTP.200(OK)
RepositoryLayer ->> ServiceLayer : Found
ServiceLayer	-x NoticationAPI: AlreadyExist
NoticationAPI	-x Company: HTTP.400(Bad Request)
</div>

**Company to NoticationAPI in CreateCallHistory**

<div class="mermaid">
sequenceDiagram
Company			-->> NoticationAPI: createCallHistory(called,caller)
NoticationAPI	-x Company: HTTP.400(Bad Request)
NoticationAPI	-->> ServiceLayer: createCallHistory(called,caller)
ServiceLayer 	-->> RepositoryLayer: find(callHistory)
RepositoryLayer ->> ServiceLayer : NotFound
NoticationAPI	-->> ServiceLayer: save(callHistory)
RepositoryLayer ->> ServiceLayer : OK
RepositoryLayer ->> ServiceLayer : found
NoticationAPI	-->> ServiceLayer: update(callHistory)
RepositoryLayer ->> ServiceLayer : OK
ServiceLayer 	->> NoticationAPI: OK
NoticationAPI	->> Company: HTTP.200(OK)
</div>

**Company to NoticationAPI in CreateDeliveryHistory**
<div class="mermaid">
sequenceDiagram
Company			-->> NoticationAPI: createDeliveryHistory(caller,called)
NoticationAPI	-x Company: HTTP.400(Bad Request)
NoticationAPI	-->> ServiceLayer: createDeliveryHistory(caller,called)
ServiceLayer 	-->> RepositoryLayer: find(callHistory)
RepositoryLayer ->> ServiceLayer : NotFound
ServiceLayer	-x NoticationAPI: NoSuchCallHistory
NoticationAPI	-x Company: HTTP.400(Bad Request)

RepositoryLayer ->> ServiceLayer : found
NoticationAPI	-->> ServiceLayer: save(deliveryHistory)
RepositoryLayer ->> ServiceLayer : OK
NoticationAPI	-->> ServiceLayer: delete(callHistory)
RepositoryLayer ->> ServiceLayer : OK
ServiceLayer 	->> NoticationAPI: OK
NoticationAPI	->> Company: HTTP.200(OK)
</div>

**NoticationSocket to PhoneUser**

<div class="mermaid">
sequenceDiagram
PhoneUser-->> NoticationSocket : connect/subscribe(phoneNumber,password)
NoticationSocket -->> ServiceLayer: findUser(phoneNumber)
ServiceLayer 	 -->> RepositoryLayer: findUser(phoneNumber)
RepositoryLayer  ->> ServiceLayer : NotFound
ServiceLayer	 -x NoticationSocket : Empty
NoticationSocket -x PhoneUser: Bad credientals X
RepositoryLayer  ->> ServiceLayer : User(phoneNumber, password)
ServiceLayer	 ->> NoticationSocket : User(phoneNumber, password)
NoticationSocket -->> Validator: isValid(password)
Validator		 -x NoticationSocket : NoValid
NoticationSocket -x PhoneUser: Bad credientals X
Validator		 ->> NoticationSocket : Valid
NoticationSocket ->> PhoneUser: Authenticated OK
NoticationSocket -->> ServiceLayer: findNotifications(phoneNumber)
ServiceLayer	 -->> RepositoryLayer: findNotifications(phoneNumber)
RepositoryLayer  ->> ServiceLayer : NotFound
ServiceLayer	 ->> NoticationSocket : Empty

RepositoryLayer  ->> ServiceLayer : Found
ServiceLayer	 ->> NoticationSocket : NotificationMessage
NoticationSocket ->> PhoneUser: NotificationMessage
NoticationSocket ->> ServiceLayer : deleteCalHistory(phoneNumber)
ServiceLayer	 ->> RepositoryLayer: deleteCalHistory(phoneNumber)
RepositoryLayer  ->> ServiceLayer : OK
ServiceLayer	 ->> NoticationSocket : OK
</div>

# Notes

 - The langugae of the notification messages can be chaned by modifying application.language in application.properties. (it must be "tur" for Turkish or "eng" for English , default value "eng")
 - WebSocket embedded client must be improved, when the phoneUser can not be authenticated, it must notify the user. It can only be checked from the console of the browser for now.
 - Integration tests for controllers and security must be created.
 - Security should be enhanced.
