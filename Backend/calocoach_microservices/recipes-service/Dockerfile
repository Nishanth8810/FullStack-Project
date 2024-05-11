FROM openjdk:17

EXPOSE 8082
ADD target/springboot-aws-deploy-recipe-server.jar springboot-aws-deploy-recipe-server.jar
ENTRYPOINT ["java","-jar","/springboot-aws-deploy-recipe-server.jar"]