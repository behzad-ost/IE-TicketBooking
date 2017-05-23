FROM tomcat:8-jre8

MAINTAINER Seyyed Ali Akhavani <sa.akhavani@gmail.com>

RUN apt-get install bash curl unzip

RUN curl http://iweb.dl.sourceforge.net/project/hsqldb/hsqldb/hsqldb_2_3/hsqldb-2.3.4.zip -o /root/hsqldb-2.3.4.zip \
    && unzip /root/hsqldb-2.3.4.zip -d /root \
    && ln -s /root/hsqldb-2.3.4/hsqldb /root/hsqldb \
    && rm -f /root/hsqldb-2.3.4.zip

COPY sqltool.rc /root/

RUN mkdir /root/sql

COPY *.sql /root/sql/

# PORTS
EXPOSE 9001

# Run Commands On Startup
CMD java -cp /root/hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:mydb --dbname.0 testdb \
    && java -jar /root/hsqldb/lib/sqltool.jar --autoCommit --continueOnErr=true testdb /root/*.sql