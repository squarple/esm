node {
    stages {
        stage('SCM') {
            checkout scm
        }
        stage('SonarQube Analysis') {
            withSonarQubeEnv(credentialsId: 'sonarqube') {
                bat "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
            }
        }
        //Use JaCoCo for code coverage
        stage('Tomcat Deploying') {
            //deploy to tomcat
        }
    }
}