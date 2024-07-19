pipeline {
    agent any
    environment {
        // Set npm cache directory to a writable location
        npm_config_cache = "${WORKSPACE}/.npm-cache"
        CI = 'false'

        registry = "us-docker.pkg.dev/big-lab-422102/jenkins-demo"
        registryCredential = "gcp-sa"  
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
        stage('Build Frontend') {
            when {
                changeset "**/frontend/*.*"
                beforeAgent true
            }
            steps {
                dir('frontend') {
                    script {
                        dockerImage = docker.build registry + ":latest"
                        docker.withRegistry( '', registryCredential ) {
                            dockerImage.push()
                        }
                    }
                }
            }
        }

    }
    post {
        always {
            cleanWs()
        }
    }
}
