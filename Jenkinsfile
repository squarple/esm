node {
    stage('SCM') {
        checkout scm
    }
    stage('SonarQube Analysis') {
        withSonarQubeEnv(credentialsId: 'sonarqube') {
            bat "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins-esm"
        }
    }
    stage('Tomcat Deploying') {
        deploy adapters: [tomcat9(credentialsId: 'tomcat9', path: '', url: 'http://localhost:80')], contextPath: 'esm', war: '**/*.war'
    }
    stage('Chuck Norris') {
        chuckNorris()
        step([$class: 'CordellWalkerRecorder'])
    }
}