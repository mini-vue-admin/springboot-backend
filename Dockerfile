FROM openjdk:21-slim
WORKDIR /app
COPY target/SkyProbe-0.0.1.jar /app/app.jar
ENV TZ=Asia/Shanghai \
 JAVA_OPTS=-Xmx256M
EXPOSE 8080
CMD java $JAVA_OPTS -jar app.jar