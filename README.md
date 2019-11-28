# Parivahan 

This is Car, bike pooling application. Where user can offer or request a ride.
Project also allows shuttle service management by adding shuttle schedule and booking of shuttle.

## Technology Stack:
- Java 8
- Maven
- Git
- Postgres
- MSG91 For Sms

## Installation Steps:
  - Java8 :   **sudo apt-get install default-jdk**
  - Maven: **sudo apt-get install maven**
  - Git: **sudo apt install git**
  - Postgres: **sudo apt install postgresql postgresql-contrib**
    - Change Password : \password [Add password when prom prompted]
    - Create database parivahan: \c parivahan postgres
    - Create database: using db script in parivahan service     [src/main/resources/config/scripts/dbscripts.sql]resses = '*'listen_addresses = '*'

## Install Backend Parivahan Service
  ```
   cd /home/ubuntu
   Git Clone for new installation:   **git clone <>**
   cd /home/ubuntu/parivahan-service/
   mvn clean package
 ```
## Install Backend Notification Service
```
   cd /home/ubuntu
   Git Clone for new installation:** git clone <>**
   cd /home/ubuntu/parivahan-notification-service
   mvn clean package
```
## Sample Properties file for Parivahan Service
```
springfox.documentation.swagger.v2.path=/api-docs
server.contextPath=
server.port=443
spring.jackson.date-format=com.gslab.parivahan.RFC3339DateFormat
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false

parivahan.support.email= <Enter Support Email Id]>
parivahan.notification.url=<Enter URL for Notification Service>
parivahan.ride.configurable.tooffer=30
parivahan.support.email.invitation.path= /opt/
parivahan.ride.search.threshold.0To10km=1000
parivahan.ride.search.threshold.10To50km=5000
parivahan.ride.search.threshold.50To100km=10000
parivahan.ride.search.threshold.100andabove=50000
parivahan.ride.map.key= <Add google Map Key>
-- search will start from this default location. To reduce the points in search api of deistance matrix api.
set this field to nearest location like state name, District name etc.
parivahan.ride.search.default.country=IN
parivahan.ride.search.default.location=Maharashtra
# Minimum ride charge. This is minimum charge when calculation goes below this level. 
# if calculation for ride is 3 Rs. Then in that case it is rounded of to minimum charge set. That is 5Rs.
parivahan.ride.minimum.charge=5
# Base charge per km. 
parivahan.ride.baserate.per.km=5

parivahan.allowed.email.suffix=@gmail.com
# Spring Mail Configurations
spring.mail=
spring.mail.host= smtp.gmail.com
spring.mail.port= 465
spring.mail.protocol= smtps
spring.mail.username= <Enter Email Id for sending emails>
spring.mail.password= <Enter Password>
spring.mail.properties.mail.transport.protocol= smtps
spring.mail.properties.mail.smtps.auth= true
spring.mail.properties.mail.smtps.starttls.enable= true
spring.mail.properties.mail.smtps.timeout= 8000
spring.mail.properties.mail.scheduler.persistence.enabled= false
spring.mail.properties.mail.scheduler.persistence.redis.embedded= false
spring.mail.properties.mail.scheduler.persistence.redis.enabled= false
#spring.jackson.serialization.fail-on-empty-beans=false

# Spring DataSource Configurations for Postgresql
spring.datasource.url=jdbc:postgresql://localhost:5432/parivahan
spring.datasource.username=postgres
spring.datasource.password= <Password for Postgres>
spring.datasource.driver-class-name= org.postgresql.Driver
spring.datasource.platform= POSTGRESQL
spring.jpa.generate-ddl=false


#ldap configuration
parivahan.auth.ldap.enabled=false # true if Configuring LDAP authentication
parivahan.auth.ldap.urls=ldap://<LDAP URL>
parivahan.auth.ldap.basedn=<Enter Base Domain>
parivahan.auth.ldap.username=<Ldap Manager's Username>
parivahan.auth.ldap.password=<Ldap Manager's password>
parivahan.auth.ldap.userdnpattern=<User pattern to search User>

#show sql statement
logging.level.org.hibernate.SQL=debug
#show sql values
logging.level.org.hibernate.type.descriptor.sql=trace

# generated with :  keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
server.ssl.key-store: keystore.p12
server.ssl.key-store-password: test1234
server.ssl.keyStoreType: PKCS12
server.ssl.keyAlias: tomcat

parivahan.security.jwt.tokenExpirationTime: 720 
parivahan.security.jwt.anonymousTokenExpirationTime: 5
parivahan.security.jwt.refreshTokenExpTime: 1440 
parivahan.security.jwt.tokenIssuer: http://www.gslab.com
parivahan.security.jwt.tokenSigningKey: <Add random token signing key>
parivahan.security.jwt.autoGeneratedTokenSigningKey: 


logging.level.org.springframework.web = DEBUG
endpoints.beans.id=springbeans
endpoints.beans.sensitive=false
endpoints.beans.enabled=true

```

## Sample Properties file for Notification Service
``` 
server.port=8092
gslab.support.email= <Enter Support Email Id>
gslab.sms.authkey= <MSG91 Auth Key>
gslab.sms.senderid= PRIVHN
gslab.sms.route= 4
gslab.email.enable = <True/False>
gslab.sms.enable = <True/False
spring.mail= <Enter Sender Email-ID>
spring.mail.host= smtp.gmail.com
spring.mail.port= 465
spring.mail.protocol= smtps
spring.mail.username= <Username>
spring.mail.password= <Password>
spring.mail.properties.mail.transport.protocol= smtps
spring.mail.properties.mail.smtps.auth= true
spring.mail.properties.mail.smtps.starttls.enable= true
spring.mail.properties.mail.smtps.timeout= 8000
spring.mail.properties.mail.scheduler.persistence.enabled= false
spring.mail.properties.mail.scheduler.persistence.redis.embedded= false
spring.mail.properties.mail.scheduler.persistence.redis.enabled= false
```

## Create Parivahan as Service

Create parivahan.service file in path:  /etc/systemd/system with the following contents
```
[Unit]
Description= Parivahan
After=syslog.target

[Service]
ExecStart=/usr/bin/java -jar /home/ubuntu/Parivahan-1.0.0.jar

[Install]
WantedBy=multi-user.target
```
## Create Parivahan-Notification Service

```
[Unit]
Description= Parivahan-Notification
After=syslog.target

[Service]
ExecStart=/usr/bin/java -jar /home/ubuntu/Parivahan-Notification-0.0.1-SNAPSHOT.jar

[Install]
WantedBy=multi-user.target
```

# Start Parivahan and Parivahan-Notification Service

   - **sudo service parivahan start**
   - **sudo systemctl start notification.service**

# Swagger Access for API Documentation:
  **https://localhost/swagger-ui.html#!/**
  
  
# Parivahan Features
-   Ride search is performed in a way that it also matches the rides that fall in the way of other rides.
    
-   User is shown the distance to walk for joining the ride.
    
-   Google maps integration for selecting a particular route during offering a ride.
    
-   SMS and Email notifications for any status updates like ride request accepted or someone requested a ride.
    
-   Configurable dynamic radius for ride searching ,Smaller radius for short rides and bigger radius for longer rides.
    
-   Shuttle service is also supported. Bulk shuttle schedules can be added at a time. Also upcoming shuttles are displayed on the homepage.
    
-   Recently offered rides are shown on the home page for quick booking.
    
-   Accepts User feedback for Booking/offer/overall experience.
    
-   Google calendar invites are sent for each booking so that it can be added to the riders schedule.


