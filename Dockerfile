FROM tomee:latest
EXPOSE 8080
RUN rm -fr /usr/local/tomee/webapps/rest
COPY target/rest.war $CATALINA_HOME/webapps/