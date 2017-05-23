node ('master') { 
		

		def mvnHome = tool 'mvn'
	
		stage('SCM Checkout'){
				checkout scm
		}
		
		stage('Unit Tests'){
				
			try {
     			notifyBuild('STARTED')
     			sh "${mvnHome}/bin/mvn clean -P dev test"
 		   	} catch (e) {
     			currentBuild.result = "FAILED"
	     		throw e
   		    } finally {
     			notifyBuild(currentBuild.result)
     			step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
     			step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
     			 publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'coverage',
                    reportFiles: 'index.html',
                    reportName: "Junit Report"
            ])
   		    }
   
		 }

		stage('Integration Test'){
		
			try {
     			notifyBuild('STARTED')
     			sh "${mvnHome}/bin/mvn clean -P integration-test verify"
     			
     			
 		   	} catch (e) {
     			currentBuild.result = "FAILED"
	     		throw e
   		    } finally {
     			notifyBuild(currentBuild.result)
     			step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
     			step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
     			
   		    }
   
		}
		
		stage('Deploy'){
		
			try {
     			notifyBuild('STARTED')
     			sh "${mvnHome}/bin/mvn deploy:deploy-file -Dfile=../springboot-crud-demo-master/target/spring-boot-web-0.0.3-SNAPSHOT.jar -Durl=http://localhost:8081/artifactory/libs-snapshot-local -DgroupId=com.enstat -DartifactId=spring-boot-web -Dversion=0.0.3-SNAPSHOT -e"
     			
  		   	} catch (e) {
     			currentBuild.result = "FAILED"
	     		throw e
   		    } finally {
     			notifyBuild(currentBuild.result)
     			
   		    }
   
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