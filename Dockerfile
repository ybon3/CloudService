# [START cloudrun_helloworld_dockerfile]
# [START run_helloworld_dockerfile]

# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8-jdk-11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk11:alpine-jre

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/ventilator-*.jar /ventilator.jar

# download ttf
RUN apk add --no-cache fontconfig ttf-dejavu

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ventilator.jar"]

# [END run_helloworld_dockerfile]
# [END cloudrun_helloworld_dockerfile]