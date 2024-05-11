FROM openjdk:17

EXPOSE 8088
ADD target/springboot-aws-deploy-communication-service.jar springboot-aws-deploy-communication-service.jar
ENTRYPOINT ["java","-jar","/springboot-aws-deploy-communication-service.jar"]