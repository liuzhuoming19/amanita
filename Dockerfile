FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER liuzhuoming23@live.com

RUN mkdir /amanita

WORKDIR /amanita

EXPOSE 8091

ADD ./target/amanita.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=prod"]
