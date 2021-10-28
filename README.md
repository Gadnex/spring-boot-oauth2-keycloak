# spring-boot-oauth2-keycloak
This repository contains code for an example project to demonstrate the integration between:
 - Keycloak as an OAuth2 authorization server.
 - A Spring Boot REST application as an OAuth2 resource server to secure the REST API.
 - A swagger-ui web application served via the resource server and containing a OAuth2 Javascript client to obtain user tokens to call the REST API.

## Software Requirements
The following software is required to run the demo:
 - Java 11 or later
 - Maven 3.8.1 or later
 - Docker desktop (Tested with 4.0.1)
 - Java IDE

## Setup instructions
The following sections describes the setup of each of the componets of the demo application.

### authorisation-server
To set up the Keycloak authorisation-server for development run the following command in the root of the code repository.
```
docker-compose up -d
```
This will start up a Keycloak server inside a Docker container. The file `./authorisation-server/demo-realm.json` will then be used to create a `demo` realm in Keycloak. The `demo` realm will also be configured with some default roles, users and OAuth clients required for the application.

If you make changes inside Keycloak that you need to export, you can do the following.
1) Make sure the Keycloak server is running.
2) Run the following command:
```
docker exec -it keycloak /opt/jboss/keycloak/bin/standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=demo -Dkeycloak.migration.usersExportStrategy=REALM_FILE -Dkeycloak.migration.file=/export/demo-realm.json
```
This command will start up a second Keycloak instance inside the same Docker container with a port offset of 100 to prevent port conflicts. The second server will export the demo realm to the JSON file inside the container that is mapped to the Docker host via a Docker volume. The file `./authorisation-server/demo-realm.json` will thus be updated.

If you need to log into the Keycloak server admin interface use the following details.

URL: http://localhost:9090/auth/admin/  
Username: keycloak  
Password: keycloak

### resource-server
Next the resource-server Spring Boot application needs to be started to expose the REST API and the Swagger UI.
1) Navigate to the resource-server folder: `cd resource-server`
2) Run the Spring Boot application: `mvn spring-boot:run`
3) Open the Swagger UI in your web browser `http://localhost:8080/swagger-ui`

If the Swagger UI page is displayed, the application is running. Alternatively the application can also be run from your IDE.

## Testing the application
In order to test the REST API via the Swagger UI the following login credentials can be used.

Username: user1  
Password: user1  
Roles: USER, ADMIN

Username: user2  
Password: user2  
Roles: USER