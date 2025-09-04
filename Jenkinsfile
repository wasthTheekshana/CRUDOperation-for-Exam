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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t student-api .'
                sh 'docker tag student-api $DOCKER_IMAGE'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS}",
                    passwordVariable: 'DOCKER_PASSWORD',
                    usernameVariable: 'DOCKER_USERNAME'
                )]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin docker.io'
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}
