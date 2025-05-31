FROM openjdk:21-jdk-slim

ENV TOMCAT_VERSION=10.1.29
ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

RUN apt-get update && apt-get install -y curl && \
    curl -fSL https://dlcdn.apache.org/tomcat/tomcat-10.1/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -o /tmp/tomcat.tar.gz && \
    mkdir -p $CATALINA_HOME && \
    tar -xzf /tmp/tomcat.tar.gz -C $CATALINA_HOME --strip-components=1 && \
    rm /tmp/tomcat.tar.gz && \
    apt-get remove -y curl && apt-get autoremove -y && apt-get clean

RUN rm -rf $CATALINA_HOME/webapps/*

COPY target/API-Services-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
