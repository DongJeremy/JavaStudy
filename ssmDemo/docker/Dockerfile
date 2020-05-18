FROM 192.168.1.200:5000/redhat-openjdk11
MAINTAINER OPENJDK Docker Maintainers "d05660@163.com"

ENV     JAVA_HOME="/opt/java-11-openjdk-11.0.4.11-5.static.jdk.openjdkportable.x86_64"
ENV     CATALINA_HOME="/opt/apache-tomcat-9.0.26"
ENV     PATH="$JAVA_HOME/bin:$PATH"
USER root

RUN  rm -rf                 ${CATALINA_HOME}/webapps/*
COPY OWX-SSM.war            ${CATALINA_HOME}/webapps/OWX-SSM.war
COPY application.properties ${CATALINA_HOME}/conf/application.properties
COPY context.xml            ${CATALINA_HOME}/conf/context.xml

USER    root
WORKDIR ${CATALINA_HOME}
EXPOSE  8080
CMD     ${CATALINA_HOME}/bin/catalina.sh run