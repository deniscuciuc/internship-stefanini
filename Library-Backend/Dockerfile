# FROM maven:3.5-jdk-11 as buildstage
FROM maven:latest as build
EXPOSE 8080
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn

# Copy only pom.xml of your projects and download dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

# Copy the project source
COPY src src
#
# # Package the application
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# #### Stage 2: A minimal docker image with command to run the app
FROM openjdk:11
ARG DEPENDENCY=/app/target/dependency


# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.stefanini.librarybackend.LibraryBackendApplication"]