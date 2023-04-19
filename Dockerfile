FROM openjdk:11

LABEL maintainer="Fabiano Nogueira Rapkiewicz"

WORKDIR /
ADD build/libs/atm-challenge-1.0.0.jar atm-challenge.jar

CMD ["java", "-jar", "atm-challenge.jar"]
