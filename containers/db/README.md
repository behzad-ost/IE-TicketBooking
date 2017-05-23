# docker-hsqldb
This is a simple Docker file to create an instance of HyperSQL in server mode.

Modify the init.sql file to initialize your data model and eventually insert some data in your DB.

To connect to a running instance with JDBC, simply use the driver `org.hsqldb.jdbc.JDBCDriver` with following an URL like `jdbc:hsqldb:hsql://172.17.0.2/xdb` where `172.17.0.2` needs to be replaced with the actual IP address of your running container (you can use `docker inspect` to find it out).
