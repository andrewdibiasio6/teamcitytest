import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    vcsRoot(HttpsGithubComAndrewdibiasio6awsCdkDemosRefsHeadsMain)

    buildType(Build)
    buildType(Deploy)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComAndrewdibiasio6awsCdkDemosRefsHeadsMain)
    }

    steps {
        nodeJS {
            workingDir = "trino-app"
            shellScript = "npm install"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object Deploy : BuildType({
    name = "Deploy"

    vcs {
        root(HttpsGithubComAndrewdibiasio6awsCdkDemosRefsHeadsMain)
    }

    steps {
        nodeJS {
            name = "Deploy"
            workingDir = "trino-app"
            shellScript = """
                cat "Test"
                npm install
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComAndrewdibiasio6awsCdkDemosRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/andrewdibiasio6/aws-cdk-demos#refs/heads/main"
    url = "https://github.com/andrewdibiasio6/aws-cdk-demos"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "andrewdibiasio6"
        password = "credentialsJSON:d952efd6-a815-454c-807c-5ccd31f825ab"
    }
    param("oauthProviderId", "tc-cloud-github-connection")
})
