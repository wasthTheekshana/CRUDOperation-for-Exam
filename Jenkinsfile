pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'wasaththeekshana/student-api:latest'
        DOCKER_CREDENTIALS = 'jenkeins-docker-access-token' // Jenkins credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/wasthTheekshana/CRUDOperation-for-Exam.git'
            }
        }

        stage('Build JAR with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t student-api .'
                bat 'docker tag student-api $DOCKER_IMAGE'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS}",
                    passwordVariable: 'DOCKER_PASSWORD',
                    usernameVariable: 'DOCKER_USERNAME'
                )]) {
                    bat 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin docker.io'
                    bat 'docker push $DOCKER_IMAGE'
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}
