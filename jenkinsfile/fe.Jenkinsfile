pipeline {
    agent {
        label "$NODE_LABEL"
    }
    environment {
        // Set npm cache directory to a writable location
        npm_config_cache = "${WORKSPACE}/.npm-cache"
        CI = 'false'

        PROJECT = "big-lab-422102"
        REPO_NAME = "jenkins-demo"
        REPO_LOCATION = "us"
        ARTIFACTS_CREDENTIALS_ID = "artifacts-registry-sa"
        IMAGE_1_NAME = "${REPO_LOCATION}-docker.pkg.dev/${PROJECT}/${REPO_NAME}/frontend"
        IMAGE_1_TAG = "${env.BUILD_ID}"
        IMAGE_2_NAME = "${REPO_LOCATION}-docker.pkg.dev/${PROJECT}/${REPO_NAME}/backend"
        IMAGE_2_TAG = "${env.BUILD_ID}"  
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
        stage('Login to GCP') {
            steps {
                echo 'Login to gcp'
                withCredentials([file(credentialsId: "${ARTIFACTS_CREDENTIALS_ID}", variable: 'GC_KEY')]) {
                    sh("gcloud auth activate-service-account --key-file=${GC_KEY}")
                }
            }
        }
        stage('Build Frontend') {
            when {
                changeset "**/frontend/**"
                beforeAgent true
            }
            steps {
                dir('frontend') {
                    echo 'Build frontend docker image Start'
                    sh 'pwd'
                    sh 'docker build -t ${IMAGE_1_NAME}:${IMAGE_1_TAG} .'
                    // withCredentials([file(credentialsId: "${ARTIFACTS_CREDENTIALS_ID}", variable: 'GCR_CRED')]){
                    //     sh 'cat ${GCR_CRED} | docker login -u _json_key_base64 --password-stdin https://${REPO_LOCATION}-docker.pkg.dev'
                    sh 'docker push ${IMAGE_1_NAME}:${IMAGE_1_TAG}'
                    // sh 'docker logout https://${REPO_LOCATION}-docker.pkg.dev'
                    // }
                    sh 'docker rmi ${IMAGE_1_NAME}:${IMAGE_1_TAG}'
                    echo 'Build frontend docker image Finish'
                }
            }
        }

        stage('Build Backend') {
            when {
                changeset "**/backend/**"
                beforeAgent true
            }
            steps {
                dir('backend') {
                    echo 'Build backend docker image Start'
                    sh 'pwd'
                    sh 'docker build -t ${IMAGE_2_NAME}:${IMAGE_2_TAG} .'
                    // withCredentials([file(credentialsId: "${ARTIFACTS_CREDENTIALS_ID}", variable: 'GCR_CRED')]){
                    //     sh 'cat ${GCR_CRED} | docker login -u _json_key_base64 --password-stdin https://${REPO_LOCATION}-docker.pkg.dev'
                    sh 'docker push ${IMAGE_2_NAME}:${IMAGE_2_TAG}'
                    // sh 'docker logout https://${REPO_LOCATION}-docker.pkg.dev'
                    // }
                    sh 'docker rmi ${IMAGE_2_NAME}:${IMAGE_2_TAG}'
                    echo 'Build backend docker image Finish'
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
