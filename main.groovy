import folders.*

import jobs.WebBuildPipeline

new WebFolder(this)
new WebBuildPipeline(
    this,
    "github",
    "frontend",
    "fe.Jenkinsfile",
    "jenkins-demo-web"
)