package utilities

class Git {
    static String getListRepositoryBranchesScript(
        String repository,
        String credentialsId
    ) {
        return """
import com.cloudbees.plugins.credentials.CredentialsMatchers
import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.common.StandardUsernameCredentials
import com.cloudbees.plugins.credentials.domains.DomainRequirement
import jenkins.model.Jenkins
import hudson.security.ACL

def jenkins = Jenkins.get()
def lookupSystemCredentials = { credentialsId ->
return CredentialsMatchers.firstOrNull(
    CredentialsProvider
    .lookupCredentials(StandardUsernameCredentials.class, jenkins, ACL.SYSTEM,
    Collections.<DomainRequirement>emptyList()),
    CredentialsMatchers.withId(credentialsId)
)
}

def credential = lookupSystemCredentials('$credentialsId')
def user = credential.getUsername()
def accessToken = credential.getPassword().getPlainText()
def repository = '$repository'

def gitUrl = "https://oauth2:\${accessToken}@github.com/\${user}/\${repository}"
def gitBranches = "git ls-remote --heads \${gitUrl}".execute().text.readLines().collect { it.split()[1].replaceAll("refs/heads/", "") }.sort().reverse()

return gitBranches
"""
    }
}