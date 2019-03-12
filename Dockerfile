FROM gradle:4.10-jdk-slim AS build-env
ADD . /builder
WORKDIR /builder
USER root
RUN gradle shadowJar --stacktrace

FROM gcr.io/distroless/java:8
WORKDIR /app
EXPOSE 8000
COPY --from=build-env /builder/build/libs/service-news.jar .
CMD ["service-news.jar"]
