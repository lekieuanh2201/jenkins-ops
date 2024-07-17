pipeline {
    agent any
    environment {
        // Set npm cache directory to a writable location
        npm_config_cache = "${WORKSPACE}/.npm-cache"
        CI = 'false'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: "$BRANCH", 
                    credentialsId: GIT_CREDENTIALS_ID, 
                    url: "https://github.com/lekieuanh2201/jenkins-demo-web.git"
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'node:18-alpine'
                    reuseNode true
                }
            }
            steps {
                sh '''
                    ls -la
                    cd frontend
                    ls -la
                    node --version
                    npm --version
                    npm ci
                    npm run build --no-warnings
                    ls -la
                '''
            }
        }

    }
    post {
        always {
            cleanWs()
        }
    }
}
