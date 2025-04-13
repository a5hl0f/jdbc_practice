FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy source files
COPY ../../../../.. .

# Compile Java source files
RUN javac src/main/java/com/example/*.java

# Run the Java program
CMD ["java", "Main"]