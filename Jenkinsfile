node {

    def mvn = tool (name: 'Maven3', type: 'maven') + 'bin/mvn'

        stage('SCM Checkout'){
            git branch: 'master',
            url: 'https://github.com/muazzamwaheed/football-league'
        }

        stage('Mvn Package'){
            sh "${mvn} clean package install"
        }

        stage('Remove Container on Dev Server'){
             sh 'docker top football-app && docker stop -t 10 football-app'
             sh 'docker top football-app && docker rm -v football-app'
         }

        stage('Build Docker Image'){
                sh 'docker build -t muazzamwaheed/football-app:latest .'
        }

        stage('Push Docker Image'){
            withCredentials([string(credentialsId: 'docker-user', variable: 'dockerPassword')]) {
              sh "docker login -u muazzamwaheed -p ${dockerPassword}"
            }
           sh 'docker push muazzamwaheed/football-app:latest'
        }

        stage('Run Container on Dev Server'){
           sh 'docker run -p 9091:9091 -d --name football-app muazzamwaheed/football-app:latest'
        }
}