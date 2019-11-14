FROM maven:3-jdk-8 as builder

ADD . /work
WORKDIR /work

RUN mvn clean package

RUN mkdir -vp /app\
 && chmod -v a+rwx /app

RUN cp -v /work/target/static-resource-*-*.jar /app/static-resource.jar\
 && chmod -v +x /app/static-resource.jar # +x to run with jre-alpine in the future

FROM openjdk:8

COPY --from=builder /app /app/
WORKDIR /app

VOLUME [ "/app/.webroot" ]
CMD [ "java", "-jar", "./static-resource.jar" ]
