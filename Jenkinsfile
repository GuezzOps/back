pipeline {
    agent any
    tools {
        maven 'MAVEN3.8'
    }
    environment { 
        DOCKERHUB_CREDENTIALS=credentials('docker-credentials')
    }
    stages {
        stage('Git fetch') {
            steps {
                git branch: 'amine', 
                url: 'https://github.com/AchAmine/SpringProject',
                credentialsId: 'git-credentials'
            }
        }

        stage('BUILD') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                 sh 'mvn sonar:sonar'
                }
            }
        }
      
        
        stage('test ') {
             steps{
                sh'mvn test'
        }
         }
        
         stage("UploadArtifact"){
            steps{
                script{
                    def mavenPom = readMavenPom file: 'pom.xml'
                nexusArtifactUploader(
                  nexusVersion: 'nexus3',
                  protocol: 'http',
                  nexusUrl: '192.168.60.15:8081',
                  groupId: 'com.esprit.examen',
                  version: "${mavenPom.version}",
                  repository: 'tpachat-repo',
                  credentialsId: 'nexus-credentials',
                  artifacts: [
                    [artifactId: 'tpAchatProject',
                     classifier: '',
                     file: "target/tpAchatProject-${mavenPom.version}.jar",
                     type: 'jar']
                            ]
                )
            }
          }
        }

        stage('Build App Image') {
            steps {
				sh 'docker build -t guezzops/back .'
			}
        }
        stage('Login dockerhub') {

			steps {
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
			}
		}

       
        stage('Push to dockerhub') {

			steps {
				sh 'docker push guezzops/back'
			}
		}
		
	    stage('Docker compose') {
          steps{
            script {
              sh 'docker-compose down --rmi all'
              sh 'docker-compose up -d '
            }
          }
          post {
                success {
                    sh 'docker-compose ps'
                }
            }
        }  
 
    }
}