pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                bat 'mvn package'
            }
        }
        stage('build docker image') {
            steps {
                bat 'docker build -t sm:test-1.0 .'
            }
        }
        stage('deploy') {
            steps {
                bat 'docker-compose up -d'
            }
        }
    }
    post {
        always {
            junit 'target/*reports/*.xml'
            emailext attachLog: true, body: 'test success', subject: 'test notification', to: 'hablous2@gmail.com'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
