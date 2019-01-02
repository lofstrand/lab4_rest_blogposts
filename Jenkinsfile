pipeline {
    agent any
    tools {
        maven 'myMaven'
        jdk 'jdk8'
    }
    stages {
        stage ('Initialize') {
            steps {
                git 'https://github.com/lofstrand/lab4_rest_blogposts.git'
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                echo 'build'
                //sh 'mvn -Dmaven.test.failure.ignore=true install'
                sh 'mvn -Dmaven.test.failure.ignore clean package'
            }
            post {
                success {
                echo 'success'
                    //junit 'target/surefire-reports/**/*.xml'
                    archiveArtifacts 'target/*.war'
                }
            }
        }

        stage('Docker build') {
            //agent { dockerfile true }
            steps {
                sh '''
                    rm -fr /usr/local/tomee/webapps/rest
                    docker cp target/rest.war rest-blogposts://usr/local/tomee/webapps/rest.war
                '''
                //echo 'Hello jdk'
                //sh 'java -version'
            }
        }
    }
}