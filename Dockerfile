FROM adoptopenjdk/openjdk11
ADD target/miExpense-0.0.1-SNAPSHOT.jar miexpense.jar
ENTRYPOINT ["java", "-jar", "miexpense.jar"]