FROM       		        ubuntu:latest

ENV DEBIAN_FRONTEND 	noninteractive
ENV JAVA_HOME		    /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH		        $PATH:$JAVA_HOME/bin

RUN apt-get -y update && apt-get install -y apt-utils lsb-release
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
RUN echo "deb http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.0.list
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get -y update
RUN apt-get install software-properties-common -y
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get -y update && apt-get install -y mongodb-org
RUN mkdir -p /data/db

EXPOSE			        8080 27017

WORKDIR 		        /app

VOLUME			        /app

ENTRYPOINT		        java -jar cag-current-race.jar

ADD	current-race/target/cag-current-race.jar			            /app/cag-current-race.jar
ADD	client-api/target/client-api-0.0.1-SNAPSHOT.jar			        /app/client-api.jar
ADD	leaderboard/target/leaderboard-0.0.1-SNAPSHOT.jar		        /app/leaderboard.jar
ADD	race-administrator/target/race-administrator-0.0.1-SNAPSHOT.jar	/app/race-administrator.jar
ADD	user-manager/target/user-manager-0.0.1-SNAPSHOT.jar		        /app/user-manager.jar

CMD    ["/bin/sh", "-c", "java -jar /app/cag-current-race.jar", \
     	"/bin/sh", "-c", "java -jar /app/client-api.jar", \
	    "/bin/sh", "-c", "java -jar /app/user-manager.jar", \
	    "/bin/sh", "-c", "java -jar /app/race-administrator.jar", \
	    "/bin/sh", "-c", "java -jar /app/leaderboard.jar"]
