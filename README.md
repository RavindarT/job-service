# Job Service

A standalone Spring Boot REST API for managing job roles. It runs on localhost at port 8082 and stores its records in a local H2 database under data/. The API is intended for local development and has no authentication.

## Build and run

Use JDK 21 or later and Maven 3.6.3 or later.

    mvn clean package
    java -jar target/job-service-0.0.1-SNAPSHOT.jar

## API

| Method | Path | Success |
| --- | --- | --- |
| POST | /api/jobs | 201 Created |
| GET | /api/jobs | 200 OK |
| GET | /api/jobs/{id} | 200 OK |
| PUT | /api/jobs/{id} | 200 OK |
| DELETE | /api/jobs/{id} | 204 No Content |

POST and PUT accept JSON, for example:

    {"title":"Software Engineer","description":"Develop and maintain internal services."}

The title is required and the description is optional. PUT replaces both fields. Invalid requests return HTTP 400, and requests for unknown job IDs return HTTP 404.

## Upload to GitHub

Extract the ZIP and upload its contents, including .gitignore, to the repository root.
