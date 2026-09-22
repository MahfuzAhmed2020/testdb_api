def repoUrl = System.getenv('JENKINS_GITHUB_REPO_URL') ?: 'https://github.com/MahfuzAhmed2020/testdb_api.git'

pipelineJob('testdb_api') {
    displayName('testdb_api')
    triggers {
        githubPush()
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(repoUrl)
                    }
                    branches('*/master')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
}
