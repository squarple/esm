node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      powershell "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
    }
  }
}