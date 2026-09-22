FROM jenkins/jenkins:lts-jdk17

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends docker.io git maven \
    && rm -rf /var/lib/apt/lists/*

USER jenkins
COPY jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

COPY jenkins/casc.yaml /usr/share/jenkins/ref/casc.yaml
COPY jenkins/jobs.groovy /usr/share/jenkins/ref/jobs.groovy
