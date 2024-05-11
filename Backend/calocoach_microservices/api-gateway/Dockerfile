FROM openjdk:17

EXPOSE 9090
ADD target/springboot-aws-deploy-api-gateway.jar springboot-aws-deploy-api-gateway.jar
ENTRYPOINT ["java","-jar","/springboot-aws-deploy-api-gateway.jar"]