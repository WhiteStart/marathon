pipeline {
    agent {
        docker {
            image 'maven:3.8.6'
            args '-v /Users/huangminzhi/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
//         stage('Test') {
//             steps {
//                 sh 'mvn test'
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml'
//                 }
//             }
//         }
        stage('Deploy') {
            steps {
                // 在此处添加部署到目标环境的步骤
                sh 'docker build -t marathon-1.0 .' // 替换为你的部署脚本或命令
                sh 'docker run --name marathon -d -p 8088:8088 --privileged=true marathon-1.0'
            }
        }
//         stage('Deliver') {
//             steps {
//                 sh './jenkins/scripts/deliver.sh'
//             }
//         }
    }
}
