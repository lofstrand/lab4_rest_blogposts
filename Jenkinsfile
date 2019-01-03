pipeline {
    agent any
    tools {
        maven 'myMaven'
        jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            steps {
                echo 'build'
                //sh 'mvn -Dmaven.test.failure.ignore=true install'
                sh 'mvn -Dmaven.test.failure.ignore clean package'
            }
            post {
                success {
                echo 'success'
                    archiveArtifacts 'target/*.war'
                }
            }
        }
        stage ('Docker') {
            steps {
                echo 'Building docker container'
                sh 'docker-compose down'
                sh 'docker-compose up --no-deps --build -d'
            }

        }
    }
}