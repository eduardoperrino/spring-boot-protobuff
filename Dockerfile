FROM openjdk:jre-alpine
MAINTAINER Eduardo Perrino <eduardo.perrino@gmail.com>

VOLUME /tmp

ARG JAR_FILE
ADD target/${JAR_FILE} /opt/protobuff.jar

RUN addgroup bootapp && \
    adduser -D -S -h /var/cache/bootapp -s /sbin/nologin -G bootapp bootapp

EXPOSE 8082

USER bootapp
ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/protobuff.jar"]