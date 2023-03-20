FROM openjdk:11

WORKDIR /app
ADD /target/*.jar tpAchatProject.jar

EXPOSE 8089
ENTRYPOINT ["java","-jar","/app/tpAchatProject.jar"]
