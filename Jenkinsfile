pipeline {
    agent any
    stages {
        stage("SCM") {
            steps {
                checkout scm
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(credentialsId: 'sonarqube') {
                    bat "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
                }
            }
        }
        stage('Tomcat Deploying') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat9', path: '', url: 'http://localhost:80')], contextPath: 'esm', war: '**/*.war'
            }
        }
        stage('Chuck Norris') {
            steps {
                chuckNorris()
                step([$class: 'CordellWalkerRecorder'])
            }
        }
    }
}