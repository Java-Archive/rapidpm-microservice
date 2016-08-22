# rapidpm-microservice

[![Join the chat at https://gitter.im/RapidPM/rapidpm-microservice](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/RapidPM/rapidpm-microservice?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![](https://build.rapidpm.org/app/rest/builds/buildType:id:RapidPM_Develop_Microservice_Snapshot/statusIcon)](https://build.rapidpm.org/viewType.html?buildTypeId=RapidPM_Develop_Microservice_Snapshot&guest=1)
A base implementation for a microservice.

The Core Service will listen on IP 0.0.0.0
The base configuration will start Servlets at port 7080 and REST-Endpoints at 7081.

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


