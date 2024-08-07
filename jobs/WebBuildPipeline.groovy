package jobs

import javaposse.jobdsl.dsl.DslFactory
import utilities.Git

class WebBuildPipeline {
    public WebBuildPipeline (
        DslFactory factory,
        String gitCredentialsId,
        String buildIdentifier,
        String jenkinsfile,
        String repository,
        String nodeLabel
    ) {
        def pipelineScript = "./jenkinsfile/$jenkinsfile"
        def jobName = "jenkins-demo-$buildIdentifier-build"
        def job = factory.pipelineJob("web/build/${jobName}") {
            logRotator(-1, 50)
            properties {
                disableConcurrentBuilds()
            }
            environmentVariables {
                keepBuildVariables(true)
                keepSystemVariables(true)
                envs([
                    BUILD_IDENTIFIER: buildIdentifier,
                    GIT_CREDENTIALS_ID: gitCredentialsId,
                ])
            }
            triggers{
                scm('* * * * *')
            }
            parameters {
                extensibleChoiceParameterDefinition {
                    name('BRANCH')
                    choiceListProvider {
                        systemGroovyChoiceListProvider {
                            groovyScript {
                                script(Git.getListRepositoryBranchesScript(
                                        repository,
                                        gitCredentialsId
                                    ))
                                sandbox(false)
                            }
                            usePredefinedVariables(false)
                            defaultChoice('master')
                        }
                        editable(true)
                        description('')
                    }
                }
                labelParam('NODE_LABEL') {
                    defaultValue(nodeLabel)
                    description('Select nodes')
                }
            }
            definition {
                cps {
                    sandbox(true)
                    script(factory.readFileFromWorkspace(pipelineScript))
                }
            }
        }
    }
}