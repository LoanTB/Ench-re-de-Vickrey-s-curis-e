FROM ubuntu:latest

RUN apt update && \
    apt install -y openjdk-17-jre openjdk-17-jdk

COPY ./CompiledApps/Manager.jar /home/Manager.jar

ENTRYPOINT exec java -jar /home/Manager.jar