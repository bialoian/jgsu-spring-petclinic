pipeline {
    agent any
    triggers { pollSCM('* * * * *') }

    //tools {
    //    // Install the Maven version configured as "M3" and add it to the path.
    //    maven "M3"
    //}

    stages {
        stage('Checkout'){
            steps{
                git url: 'https://github.com/bialoian/jgsu-spring-petclinic.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                bat './mvnw clean package'
                //withMaven(maven : 'apache-maven-3.8.5') {
                //    bat './mvnw clean package'
                //}
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
