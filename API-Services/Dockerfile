# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim


# Set Tomcat version and installation directory
ENV TOMCAT_VERSION=10.1.29
ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

# Install curl, download and install Tomcat, clean up
RUN apt-get update && apt-get install -y curl && \
    curl -fSL https://dlcdn.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -o /tmp/tomcat.tar.gz && \
    mkdir -p $CATALINA_HOME && \
    tar -xzf /tmp/tomcat.tar.gz -C $CATALINA_HOME --strip-components=1 && \
    rm /tmp/tomcat.tar.gz && \
    apt-get remove -y curl && apt-get autoremove -y && apt-get clean

# Remove default webapps to avoid conflicts
RUN rm -rf $CATALINA_HOME/webapps/*

# Copy your WAR file into Tomcat's webapps directory as ROOT.war
COPY target/API-Services-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
