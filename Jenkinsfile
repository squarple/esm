node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
//       powershell "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
        bat "mvn clean verify sonar:sonar -Dsonar.projectKey=esm -Dsonar.host.url=http://localhost:9000 -Dsonar.login=136ed652229d7d1bd3e4cc2f668c6c5c6e5c9842"
    }
  }
}