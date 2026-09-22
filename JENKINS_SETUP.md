# Jenkins CI Setup

This repository includes a local Jenkins controller configured with Docker Compose.

## Start Jenkins
JENKINS_ADMIN_ID=admin
JENKINS_ADMIN_PASSWORD=root

Create a local `.env` file first:

```powershell
Copy-Item .env.example .env
```

Then edit `.env` and set a strong `JENKINS_ADMIN_PASSWORD`.

```powershell
docker compose up --build -d jenkins
```

Jenkins URL:

```text
http://localhost:8081
```

Default local login:

```text
JENKINS_ADMIN_ID=admin
JENKINS_ADMIN_PASSWORD=root
Username: value of JENKINS_ADMIN_ID in .env
Password: value of JENKINS_ADMIN_PASSWORD in .env
```

This Jenkins container mounts the Docker socket so it can build application images.
Only run it on a machine where you trust the Jenkins jobs and repository code.

## GitHub Push Build

The Jenkins job is created automatically as a pipeline named `testdb_api`.
It reads this repository's `Jenkinsfile` and builds with:

```text
temporary MySQL 8 container for CI tests
mvn -B clean verify
docker build -t testdb-api:<commit> -t testdb-api:latest .
```

To trigger builds when code is pushed to GitHub, add a GitHub repository webhook:

```text
Payload URL: http://<your-public-jenkins-host>/github-webhook/
Content type: application/json
Events: Just the push event
Active: checked
```

For local testing from GitHub, expose `http://localhost:8081` with a tunnel such as ngrok
and use the generated HTTPS URL as the webhook host.
