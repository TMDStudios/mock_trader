FROM openjdk:8
COPY /target/mocktrader-0.0.1-SNAPSHOT.war mocktrader.war
CMD ["java", "-jar", "/mocktrader.war"]