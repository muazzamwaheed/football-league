pipeline {

    agent { label 'master' }
    environment {
            REGISTRY_CRED_USR = "muazzamwaheed"
            CONTAINER_REGISTRY = "football.product"
            IMAGE_ID = "$CONTAINER_REGISTRY/football-app"
            PROFILE = "dev"
    }
    stages {
        stage ('Initialise') {
            steps {
                script {
                    sh 'git rev-parse HEAD > commit'
                    env.GIT_COMMIT = readFile('commit').trim()

                    sh 'git rev-parse --short HEAD > short_commit'
                    env.GIT_COMMIT_SHORT = readFile('short_commit').trim().toLowerCase()

                    sh '''
                        echo ${GIT_BRANCH}|sed 's#feature/##' > branch_name
                      '''
                    env.BRANCH_NAME_CLEAN = readFile('branch_name').trim().toLowerCase()
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                       '''
                }
            }
        }

        stage ('Run UT/IT test') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw test'
            }
        }

        stage ('Packaging for conainer') {
            steps {
                sh './mvnw -Dmaven.test.failure.ignore=false clean install'
            }
        }

        stage ('Build container') {
            steps {
                sh '''
                    pwd
                    echo "Spring profile" ${PROFILE}
                    make build -e "NAME=$IMAGE_ID" "VERSION=${GIT_COMMIT}" "PROFILE=$PROFILE"
                   '''
            }
        }
        stage ('Push to registry') {
            steps {
                withCredentials([string(credentialsId: 'docker-user', variable: 'dockerPassword')]) {
                  sh '''
                      docker login -u  $REGISTRY_CRED_USR -p ${dockerPassword}
                      docker push "$REGISTRY_CRED_USR/$IMAGE_ID:${GIT_COMMIT}"
                  '''
                }
            }
        }

        stage ('Deploy into AKS') {
            when { branch 'master' }
            agent { label 'dockerprod' }
            environment { DEPLOY_TYPE = "" }

            steps {
              fnDeploy()
            }
        }
    }
}

void fnDeploy() {
                sh '''
                    echo "Process started for branch" ${GIT_BRANCH}

                        cd ${WORKSPACE}
                        ls -l $(pwd)/aks/
                        kubectl apply -f $(pwd)/aks/app.namespace.yaml || true
                        cat aks/app.deployment.yaml | sed -e 's|__COMMIT_ID__|'${GIT_COMMIT}'|g' | kubectl -n football-league apply -f -
                        cat aks/app.ingress.yaml | sed 's/\${DEPLOY_TYPE}/'"$DEPLOY_TYPE/g" | kubectl -n football-league apply -f -

                    '''
}
