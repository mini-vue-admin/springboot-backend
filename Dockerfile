FROM openjdk:21-slim
WORKDIR /app
COPY target/SkyProbe-0.0.1.jar /app/app.jar
ENV TZ=Asia/Shanghai
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]