[![Technologie](https://img.shields.io/badge/2.6.6-Spring_Boot-blue)](https://spring.io/projects/spring-boot)
[![Technologie](https://img.shields.io/badge/Maven-brown)](https://maven.apache.org/)
[![Technologie](https://img.shields.io/badge/redis-red)](https://redis.io/)
[![Technologie](https://img.shields.io/badge/RabbitMQ-brightgreen)](https://www.rabbitmq.com/)
[![Technologie](https://img.shields.io/badge/-Digital_Ocean-lightblue)](https://www.digitalocean.com/)
[![Technologie](https://img.shields.io/badge/-Nginx-orange)](https://www.nginx.com/)
[![Technologie](https://img.shields.io/badge/13-PostgreSQL-lightgrey)](https://www.postgresql.org/)
[![Technologie](https://img.shields.io/badge/2.0-Docker-red)](https://react-icons.github.io/react-icons)

## KBE - Microservices

### description

http://161.35.80.237/swagger-ui/index.html

This **SpringBoot** + **Maven** Project consists of a Restful API and a Postgresql-DB<br/>
It uses **Java 17** and **Postgresql 3**<br/>

#### running the project

You can build and start the project with Docker by running  `docker-compose up --build` in rootdir or use the Intellij
Configuration `dockerCompose` <br/>

#### deploying the project

A heroku dyno is configured and all you have to do to deploy to prod is this: `git push heroku master` ( if youre logged
in to Heroku )

heroku domain: **kbe-warehouse.herokuapp.com**

#### API docs

![alt text](./readMeResources/swagger.png)

https://kbe-warehouse.herokuapp.com/swagger