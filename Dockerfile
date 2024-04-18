FROM maven:3.6.3-jdk-11 AS maven
COPY ./ ./hutchison-spring
# build .jar "-pProd"
WORKDIR /hutchison-spring
RUN mvn clean package -Dmaven.test.skip=true
RUN ls /hutchison-spring/target/*.jar;

FROM adoptopenjdk/openjdk11:alpine-jre
# VOLUME /tmp
COPY --from=maven /hutchison-spring/target/*.jar app.jar
# creates batch-files directory for .dat files
RUN mkdir batch-files
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
