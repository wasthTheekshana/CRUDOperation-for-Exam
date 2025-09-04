pipeline {
    agent any

    tools {
        maven 'Maven' // Must match your Global Tool Configuration
    }

    environment {
        DOCKER_IMAGE = 'wasaththeekshana/student-api:latest'
        DOCKER_CREDENTIALS = 'jenkeins-docker-access-token' // Docker Hub credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone your GitHub repo
                git branch: 'main',
                    url: 'https://github.com/wasthTheekshana/CRUDOperation-for-Exam.git'
            }
        }

        stage('Build JAR') {
            steps {
                // Windows Maven build
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def image = env.DOCKER_IMAGE

                    // Build and tag Docker image
                    bat "docker build -t student-api ."
                    bat "docker tag student-api ${image}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def image = env.DOCKER_IMAGE

                    withCredentials([usernamePassword(
                        credentialsId: env.DOCKER_CREDENTIALS,
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD'
                    )]) {
                        // Docker login and push
                        bat "echo %PASSWORD% | docker login -u %USERNAME% --password-stdin docker.io"
                        bat "docker push ${image}"
                    }
                }
            }
        }
    }

    post {
        always {
            // Logout from Docker Hub
            bat 'docker logout'
        }
    }
}
