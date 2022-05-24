FROM openjdk:11

COPY target/classes/TechnodomSlotApplication.class/ /tmp
WORKDIR /tmp
RUN javac TechnodomSlotApplication.java

CMD java TechnodomSlotApplication