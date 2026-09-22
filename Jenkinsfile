pipeline {
    agent any

    triggers {
        githubPush()
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    environment {
        APP_IMAGE = 'testdb-api'
        CI_DB_CONTAINER = 'testdb_api_ci_mysql'
        SPRING_DATASOURCE_URL = 'jdbc:mysql://ci-mysql:3306/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
        SPRING_DATASOURCE_USERNAME = 'root'
        SPRING_DATASOURCE_PASSWORD = 'root'
        SPRING_JPA_HIBERNATE_DDL_AUTO = 'update'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start CI database') {
            steps {
                sh '''
                    docker rm -f ${CI_DB_CONTAINER} >/dev/null 2>&1 || true
                    docker run -d --name ${CI_DB_CONTAINER} \
                        --network testdb_api_default \
                        --network-alias ci-mysql \
                        -e MYSQL_DATABASE=testdb \
                        -e MYSQL_ROOT_PASSWORD=root \
                        mysql:8.0

                    until docker exec ${CI_DB_CONTAINER} mysqladmin ping -h localhost -proot --silent; do
                        sleep 2
                    done
                '''
            }
        }

        stage('Build and test') {
            steps {
                sh 'mvn -B clean verify'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.jar'
                }
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    env.GIT_SHORT_SHA = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                }
                sh 'docker build -t ${APP_IMAGE}:${GIT_SHORT_SHA} -t ${APP_IMAGE}:latest .'
            }
        }
    }

    post {
        always {
            sh 'docker rm -f ${CI_DB_CONTAINER} >/dev/null 2>&1 || true'
        }
    }
}
