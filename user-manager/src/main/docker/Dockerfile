FROM java:8
VOLUME /tmp
ADD user-manager-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.data.mongodb.uri=mongodb://mongo/currentracedb"]
