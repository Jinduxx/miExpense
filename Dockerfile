FROM openjdk:19
ADD target/miExpense.jar miExpense.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "miExpense.jar"]