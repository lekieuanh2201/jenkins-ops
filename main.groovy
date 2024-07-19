import folders.*

import jobs.WebBuildPipeline

new WebFolder(this)
new WebBuildPipeline(
    this,
    "github",
    "web",
    "fe.Jenkinsfile",
    "jenkins-demo-web",
    "jenkins-agent"
)