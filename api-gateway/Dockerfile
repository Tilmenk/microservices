#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build

ENV CHILD=/usr/parent/child
ENV PARENT=/usr/parent
RUN mkdir -p CHILD
WORKDIR $CHILD
ADD ./api-gateway/pom.xml $CHILD
ADD pom.xml $PARENT
RUN mvn verify --fail-never
ADD . $CHILD
RUN mvn -f $CHILD/api-gateway/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/parent/child/api-gateway/target/api-gateway.jar /usr/local/lib/api-gateway.jar
EXPOSE 9040
ENTRYPOINT ["java","-jar","/usr/local/lib/api-gateway.jar"]