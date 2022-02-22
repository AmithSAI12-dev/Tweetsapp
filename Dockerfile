FROM openjdk:17-alpine
MAINTAINER AmithSai
COPY build/libs/tweetapp-0.0.1-SNAPSHOT.jar tweetapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tweetapp-0.0.1-SNAPSHOT.jar"]