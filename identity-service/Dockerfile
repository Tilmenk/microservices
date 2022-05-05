#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build

ENV CHILD=/usr/parent/child
ENV PARENT=/usr/parent
RUN mkdir -p CHILD
WORKDIR $CHILD
ADD ./identity-service/pom.xml $CHILD
ADD pom.xml $PARENT
RUN mvn verify --fail-never
ADD . $CHILD
RUN mvn -f $CHILD/identity-service/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/parent/child/identity-service/target/identity-service.jar /usr/local/lib/identity-service.jar
EXPOSE 9010
ENTRYPOINT ["java","-jar","/usr/local/lib/identity-service.jar"]