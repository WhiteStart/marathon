pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Deploy') {
            agent any
            steps {
                sh '''cd /var/jenkins_home/workspace/marathon/target
                                    cp ../Dockerfile ./
                                    docker build -t test .'''
            }
        }
//         stage('Deliver') {
//             steps {
//                 sh './jenkins/scripts/deliver.sh'
//             }
//         }
    }
}
