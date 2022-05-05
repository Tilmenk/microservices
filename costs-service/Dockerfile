#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build

ENV CHILD=/usr/parent/child
ENV PARENT=/usr/parent
RUN mkdir -p CHILD
WORKDIR $CHILD
ADD ./costs-service/pom.xml $CHILD
ADD pom.xml $PARENT
RUN mvn verify --fail-never
ADD . $CHILD
RUN mvn -f $CHILD/costs-service/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/parent/child/costs-service/target/costs-service.jar /usr/local/lib/costs-service.jar
EXPOSE 9030
ENTRYPOINT ["java","-jar","/usr/local/lib/costs-service.jar"]