pipeline {
    agent none

    // GitHub Webhooks触发条件
    triggers {
        githubPush()
    }

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.6'
                    args '-v /Users/huangminzhi/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Deploy') {
            agent any
            steps {
                sh '''cd /var/jenkins_home/workspace/marathon
                      docker build -t marathon .
                      docker run --rm --name marathon_test -d -p 8088:8088 marathon
                                    '''
            }
        }
    }
}
