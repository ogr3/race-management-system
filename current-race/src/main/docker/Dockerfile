FROM java:8
VOLUME /tmp
ADD cag-current-race.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 10080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.data.mongodb.uri=mongodb://mongo/currentracedb"]
