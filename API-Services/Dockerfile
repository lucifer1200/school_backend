# Use official Tomcat base image
FROM tomcat:10.0-jdk21

# Remove default webapps to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into Tomcat's webapps directory
COPY target/API-Services-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose port (optional, useful for local testing)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
