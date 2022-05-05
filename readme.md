docker-compose down && docker rm -f $(docker ps -a -q)  && docker volume rm $(docker volume ls -q) && docker-compose up

COMPOSE_DOCKER_CLI_BUILD=0 docker-compose --context remote up -d
