pipeline{
    agent any
    tools {
        jdk "JAVA"
        maven 'maven'
    }
    environment {
        REPO_URL= 'https://github.com/boulbeba115/devOpsProj.git'
        DOCKERHUB_CREDENTIALS=credentials('dockerHubCredential')
        //Nexus Config
            NEXUS_VERSION = 'nexus3'
            NEXUS_PROTOCOL = 'http'
            NEXUS_URL = 'host.docker.internal:8081'
            NEXUS_REPOSITORY = 'storemanegmentrepo'
            NEXUS_CREDENTIAL_ID = 'Nexus-credentials'
        //SonarCube
        SONARQUBE_URL = 'http://host.docker.internal'
        SONARQUBE_PORT = '9000'
    }
    stages {
        stage ('SCM'){
            steps{
            git credentialsId: 'GIT-CREDENTIALS',
            url: 'https://github.com/boulbeba115/devOpsProj.git'
            }
        }
        stage ('Maven Build'){
            steps{
            sh "mvn clean package -Dmaven.test.skip=true -X"
            }
        }
        stage ('Tests'){
            steps{
            sh "mvn test"
            }
        }
        stage('SonarQube') {
            steps {
            sh " mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL:$SONARQUBE_PORT"
            }
        }
        stage('Deploy Artifact To Nexus') {
            steps {
                script {
                // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                pom = readMavenPom file: "pom.xml";
                // Find built artifact under target folder
                filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                // Print some info from the artifact found
                echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                // Extract the path from the File found
                artifactPath = filesByGlob[0].path;
                // Assign to a boolean response verifying If the artifact name exists
                artifactExists = fileExists artifactPath;
                if (artifactExists) {
                nexusArtifactUploader(
                nexusVersion: NEXUS_VERSION,
                protocol: NEXUS_PROTOCOL,
                nexusUrl: NEXUS_URL,
                groupId: pom.groupId,
                version: pom.version,
                repository: NEXUS_REPOSITORY,
                credentialsId: NEXUS_CREDENTIAL_ID,
                artifacts: [
                    // Artifact generated such as .jar, .ear and .war files.
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: artifactPath,
                    type: pom.packaging
                    ],
                    // Lets upload the pom.xml file for additional information for Transitive dependencies
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: "pom.xml",
                    type: "pom"
                    ]
                ]
                )
                } else {
                error "*** File: ${artifactPath}, could not be found";
                }
                }
            }
        }
        stage ('deploy'){
            steps{
            sh '''
            cp target/*.jar .
            docker-compose down
            docker-compose build
            docker-compose up -d '''
            }
        }
        timeout(time: 600, unit: 'SECONDS') {
            stage('Check Availability') {
              steps {             
                  waitUntil {
                      retry(3) {
                          try {         
                              sh "curl -s --head  --request GET  http://localhost:8090/store-management/health | grep '200'"
                              return true
                          } catch (Exception e) {
                             sh '''
                            docker-compose down
                            docker-compose build
                            docker-compose up -d '''
                            }
                            return false
                      }
                    }
                  }
               }
           }
        }
        stage('Push') {

			steps {
			    withDockerRegistry([ credentialsId: "dockerHubCredential", url: "" ]) {
		        sh 'docker build -t storemanagement:latest .' 
                sh 'docker tag storemanagement:latest boulbeba115/storemanagement:$BUILD_NUMBER'
				sh 'docker push boulbeba115/storemanagement:$BUILD_NUMBER'
                }
			}
		}
    }
    post {
        always {
            junit 'target/*reports/*.xml'
            emailext body: 'The project Build success', subject: 'jenkins build storeManager', to: 'boulbeba115@gmail.com'
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
