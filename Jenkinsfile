node {

    def mvn = tool (name: 'Maven3', type: 'maven') + '/bin/mvn'

        stage('Mvn Package'){
            sh "${mvn} clean package install"
        }

        stage('Remove Container on Dev Server'){
             sh 'docker stop -t 10 football-app'
             sh 'docker rm -v football-app'
         }

        stage('Build Docker Image'){
                sh 'docker build -t muazzamwaheed/football-app:0.0.1 .'
        }

        stage('Push Docker Image'){
            withCredentials([string(credentialsId: 'docker-user', variable: 'dockerPassword')]) {
              sh "docker login -u muazzamwaheed -p ${dockerPassword}"
            }
           sh 'docker push muazzamwaheed/football-app:0.0.1'
        }

        stage('Run Container on Dev Server'){
           sh 'docker run -p 9091:9091 -d --name football-app muazzamwaheed/football-app:0.0.1'
        }
}