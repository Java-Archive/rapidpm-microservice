# rapidpm-microservice

[![Join the chat at https://gitter.im/RapidPM/rapidpm-microservice](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/RapidPM/rapidpm-microservice?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
A base implementation for a microservice.

The Core Service will listen on IP 0.0.0.0
The base configuration will start Servlets at port 7080 and REST-Endpoints at 7081.

build:
+ master:
[![Build Status](https://travis-ci.org/RapidPM/rapidpm-microservice.svg?branch=master)](https://travis-ci.org/RapidPM/rapidpm-microservice)
+ develop:
[![Build Status](https://travis-ci.org/RapidPM/rapidpm-microservice.svg?branch=develop)](https://travis-ci.org/RapidPM/rapidpm-microservice)




branch
master:
[![Dependency Status](https://www.versioneye.com/user/projects/55a3a45e3239390021000540/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55a3a45e3239390021000540)
[![Coverage Status](https://coveralls.io/repos/RapidPM/rapidpm-microservice/badge.svg?branch=master&service=github)](https://coveralls.io/github/RapidPM/rapidpm-microservice?branch=master)

develop:
[![Dependency Status](https://www.versioneye.com/user/projects/55a3a44f32393900180005b2/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55a3a44f32393900180005b2)
[![Coverage Status](https://coveralls.io/repos/RapidPM/rapidpm-microservice/badge.svg?branch=develop&service=github)](https://coveralls.io/github/RapidPM/rapidpm-microservice?branch=develop)

## SNAPSHOTS
If you are using maven you could add the following to your settings.xml to get the snapshots.

```
   <profile>
      <id>allow-snapshots</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>snapshots-repo</id>
          <url>https://oss.sonatype.org/content/repositories/snapshots</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </repository>
      </repositories>
    </profile>
```

## Examples

See [rapidpm-microservice-examples](https://github.com/RapidPM/rapidpm-microservice-examples) for more demos


