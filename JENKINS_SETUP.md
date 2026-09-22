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

This Jenkins container runs Maven builds only. It does not mount the Docker socket.

## GitHub Push Build

The Jenkins job is created automatically as a pipeline named `testdb_api`.
It reads this repository's `Jenkinsfile` and builds with:

```text
mvn -B clean verify
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
