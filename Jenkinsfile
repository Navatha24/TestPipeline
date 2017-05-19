node ('master') { 

		def mvnHome = tool 'mvn'
		
		stage('Integration Tests'){
			checkout scm	
			
			try {
     			notifyBuild('STARTED')
     			sh "${mvnHome}/bin/mvn clean -P dev test"
     			
     			
 		   	} catch (e) {
     			currentBuild.result = "FAILED"
	     		throw e
   		    } finally {
     			notifyBuild(currentBuild.result)
     			//runSonarAnalysis()
     			
   		    }
   
		 }

		stage('Acceptance Test Report'){
				 
		}
	
}

void archiveTestResults() {
    step([$class: 'JUnitResultArchiver', testResults: '**/target/**/index.html', allowEmptyResults: true])
}

void runSonarAnalysis() {
    //println 'Sonar analysis temporarily disabled';
    println 'Running Sonar analysis';
    sh "mvn -B -V -U -e org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar -Dsonar.host.url='${Constants.SONAR_URL}'"
}

def notifyBuild(String buildStatus) {
  				buildStatus =  buildStatus ?: 'SUCCESSFUL'
  				def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
   				def summary = "${subject} (${env.BUILD_URL})"
   				
   				if (buildStatus == 'STARTED') {
     					color = 'YELLOW'
     			} else if (buildStatus == 'SUCCESSFUL') {
     					color = 'GREEN'
  				} else {
     					color = 'RED'
   				}

  				hipchatSend (color: color, notify: true, message: summary)
 }