FROM openjdk:17
ADD target/book-issuing-system.jar book-issuing-system.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "book-issuing-system.jar"]