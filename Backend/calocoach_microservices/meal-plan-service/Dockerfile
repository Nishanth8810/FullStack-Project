FROM openjdk:17

EXPOSE 8087
ADD target/aws-deploy-meal-plan-service.jar aws-deploy-meal-plan-service.jar
ENTRYPOINT ["java","-jar","/aws-deploy-meal-plan-service.jar"]