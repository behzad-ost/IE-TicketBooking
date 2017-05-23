# Pull base image
FROM tomcat:8-jre8

# Maintainer
MAINTAINER Seyyed Ali Akhavani <sa.akhavani@gmail.com>

#Environment Variable
ENV CATALINA_HOME /usr/local/tomcat

# Copy to images tomcat path
ADD ali.war $CATALINA_HOME/webapps/

# Ports
EXPOSE 9002

CMD ["catalina.sh", "run"]