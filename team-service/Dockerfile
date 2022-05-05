#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build

ENV CHILD=/usr/parent/child
ENV PARENT=/usr/parent
RUN mkdir -p CHILD
WORKDIR $CHILD
ADD ./team-service/pom.xml $CHILD
ADD pom.xml $PARENT
RUN mvn verify --fail-never
ADD . $CHILD
RUN mvn -f $CHILD/team-service/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/parent/child/team-service/target/team-service.jar /usr/local/lib/team-service.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/usr/local/lib/team-service.jar"]