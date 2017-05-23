node ('master') { 

def mvnHome = tool 'mvn'
		
	try{
		stage('SCM Checkout'){
			checkout scm
			notifyBuild('STARTED')
		}
	
		stage('Unit Tests'){
			sh "${mvnHome}/bin/mvn clean -P dev test"
		}
		
		stage('Integration Tests'){
			sh "${mvnHome}/bin/mvn clean -P integration-test verify"
		}
		
		stage('Deploy'){
			sh "${mvnHome}/bin/mvn deploy:deploy-file -Dfile=../springboot-crud-demo-master/target/spring-boot-web-0.0.3-SNAPSHOT.jar -Durl=http://localhost:8081/artifactory/libs-snapshot-local -DgroupId=com.enstat -DartifactId=spring-boot-web -Dversion=0.0.3-SNAPSHOT -e"
		}		
	
	}catch (e) {
		currentBuild.result = "FAILURE:"+e
		throw e
	}finally{
		notifyBuild(currentBuild.result)
	}
}

def notifyBuild(String buildStatus) {

	buildStatus =  buildStatus ?: 'SUCCESSFUL'
	def subject = "${buildStatus}: Job: '${env.JOB_NAME} Build: [${env.BUILD_NUMBER}]'"
	def summary = "${subject}"
					
	if (buildStatus == 'STARTED') {
		color = 'YELLOW'
	} else if (buildStatus == 'SUCCESSFUL') {
		color = 'GREEN'
	} else {
		color = 'RED'
	}
	hipchatSend (color: color, notify: true, message: summary)
}