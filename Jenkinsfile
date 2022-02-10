node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
//       powershell "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
        powershell "mvn clean verify sonar:sonar -Dsonar.projectKey=esm-test -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d95a802496574a7d8eb56804cc7129610de48419"
    }
  }
}