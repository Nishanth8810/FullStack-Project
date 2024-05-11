FROM openjdk:17

EXPOSE 8085
ADD target/springboot-aws-deploy-notification-service.jar springboot-aws-deploy-notification-service.jar
ENTRYPOINT ["java","-jar","/springboot-aws-deploy-notification-service.jar"]