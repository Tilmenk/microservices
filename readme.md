[![Technologie](https://img.shields.io/badge/2.6.6-Spring_Boot-blue)](https://spring.io/projects/spring-boot)
[![Technologie](https://img.shields.io/badge/Maven-brown)](https://maven.apache.org/)
[![Technologie](https://img.shields.io/badge/17-Java-pink)](https://openjdk.java.net/)

[![Technologie](https://img.shields.io/badge/redis-red)](https://redis.io/)
[![Technologie](https://img.shields.io/badge/RabbitMQ-brightgreen)](https://www.rabbitmq.com/)
[![Technologie](https://img.shields.io/badge/13-PostgreSQL-lightgrey)](https://www.postgresql.org/)

[![Technologie](https://img.shields.io/badge/2.0-Docker-red)](https://www.docker.com/)
[![Technologie](https://img.shields.io/badge/-Digital_Ocean-lightblue)](https://www.digitalocean.com/)
[![Technologie](https://img.shields.io/badge/-Nginx-orange)](https://www.nginx.com/)

## KBE - Microservices

### description

This **SpringBoot** + **Maven** Project consists of a 5 different Microservices.<br/>
It uses **Java 17**.<br/>

### Microservices

1. **team-service**
    1. This service loads **Pokemon** and default **teams** from the **warehouse**
    2. It also saves **teams** created by **users** and fetches **Pokemon** Image urls from
       a [third party API](https://pokeapi.co/)
    3. This service also organize **costs** and **currency** assembling for responses to the **api-gateway**
    4. This service has an own instance of a _PostgreSQL_ db to save **Pokemon** and **Teams** ( **costs** and **
       currencies** aren't
       persisted )
    5. Is connected to **api-gateway** through _RabbitMQ_
2. **costs-service**
    1. Calculates costs for **Pokemon** and **Teams** of **Pokemon**
    2. Is connected to **team-service** through _RabbitMQ_
3. **currency-service**
    1. Calculates given **costs** into 3 different **currencies** ( Euro, Dollar, Bitcoin )
    2. Is connected to **team-service** through _RabbitMQ_
4. **identity-service**
    1. This service has an own _PostgreSQL_ instance to save **Users** and **Roles** for the Backend
    2. It's connected to the **api-gateway** through _HTTP_
5. **api-gateway**
    1. This is the (only) entrance point for the **Webclient** to talk to the backend
    2. Is connected to the **team-service** through _RabbitMQ_
    3. Documented through [Swagger - click to see exposed HTTP paths](http://161.35.80.237/swagger-ui/index.html)

### Building the project

1. You can build and start the project with docker-compose by running  `docker-compose up` in the rootdir. <br/>
    1. Every sub-repo has its own Dockefile so you can also run every maven module on its own
       with ```docker build ./service```
2. For developing you can also use the intellij run-configurations as long as you're hosting a postgresqlDB with a
   db called **team-service**
   and **identity-service**.</br>
   You also need to have redis running on your local host as well as rabbitMQ.

### Deploying the project

For hosting we're using a Digital Ocean droplet running Ubuntu 20.04 LTS. You have to ssh into the Machine run git pull
and build the changed components via docker-compose. </br>
The Droplet is accessible under this address:  **_128.199.35.167_**

### API docs

[Swagger - click to see exposed HTTP paths](http://128.199.35.167/swagger-ui/index.html)


### Postgressql access 
[Pgdata](http://161.35.80.237/pgdata)
