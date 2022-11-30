### Get started with Keycloak on Docker 

From a terminal start Keycloak with the following command:

```
docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:20.0.1 start-dev
```

- create new realm
- use resource file realm-config/realmy.json
- to get client secret in keycloak: go to clients -> choose spring cloud client -> credentials

Authorization in Postman

- In Authorization tab choose Authorization type OAuth 2.0

    
Configuration options:
 
- token name: token
- Grant type: Client credentials
- Access token URL: example http://localhost:8181/realms/sbm-realm/protocol/openid-connect/token
- Client ID: spring-cloud-client
- Client Secret: from keycloak
- Scope: openid offline_access
- Client Authentication: Send as Basic Auth header
- Get New Access token -> Use token
