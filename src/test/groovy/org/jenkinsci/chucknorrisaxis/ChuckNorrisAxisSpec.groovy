package org.jenkinsci.chucknorrisaxis

import hudson.matrix.AxisList
import hudson.matrix.MatrixProject
import org.jvnet.hudson.test.recipes.WithPlugin
import spock.lang.Shared
import spock.lang.Specification
import jenkins.model.Jenkins
import org.junit.Rule
import org.jvnet.hudson.test.JenkinsRule

class ChuckNorrisAxisSpec extends Specification {

    @Rule
    JenkinsRule rule = new JenkinsRule()

    @Shared
    chuckNorrisAxisDescriptor

    @WithPlugin("matrix-project.hpi")
    MatrixProject configure(chuckNorrisPluginHandler, factCount) {

        if (chuckNorrisAxisDescriptor == null) {
            chuckNorrisAxisDescriptor = Jenkins.instance.getDescriptorOrDie(ChuckNorrisAxis)
        }

        chuckNorrisAxisDescriptor.chuckNorrisPluginHandler = chuckNorrisPluginHandler
        chuckNorrisAxisDescriptor.count = factCount

        def matrixProject = rule.createMatrixProject()

        def axis = new ChuckNorrisAxis('TEST', chuckNorrisAxisDescriptor.loadDefaultItems())
        def axl = new AxisList()

        axl.add(axis)
        matrixProject.setAxes(axl)

        matrixProject
    }

    @WithPlugin("matrix-project.hpi")
    MatrixProject configureManual() {

        def matrixProject = rule.createMatrixProject()

        def cni = new ChuckNorrisItem()
        def cni2 = new ChuckNorrisItem('Chuck Norris invented logic')
        def cnal = []
        cnal.add(cni)
        cnal.add(cni2)

        def axis = new ChuckNorrisAxis('TEST', cnal)

        def axl = new AxisList()

        axl.add(axis)
        matrixProject.setAxes(axl)

        matrixProject
    }

    @WithPlugin("matrix-project.hpi")
    def 'Dynamic'() {

        given:
        def chuckNorrisPluginHandler = Mock(IFact)
        chuckNorrisPluginHandler.installed() >> true

        chuckNorrisPluginHandler.fact >>> [
                'Chuck Norris is never Mocked 1 (config)',
                'Chuck Norris is never Mocked 2 (config)',
                'Chuck Norris is never Mocked 3 (config)',
                'Chuck Norris is never Mocked 1 (build)',
                'Chuck Norris is never Mocked 2 (build)',
                'Chuck Norris is never Mocked 3 (build)']

        def matrixProject = configure(chuckNorrisPluginHandler, 3)

        when:
        def build = matrixProject.scheduleBuild2(0).get()

        then:

        build.logFile.text.contains('SUCCESS')
        build.runs.every { it.logFile.text.contains('SUCCESS') }
        build.runs.every { it.logFile.text.contains('(build)') }
        build.runs.size() == 3
    }

    @WithPlugin("matrix-project.hpi")
    def 'Dynamic Plugin Gone Away'() {

        given:
        def chuckNorrisPluginHandler = Mock(IFact)
        chuckNorrisPluginHandler.installed() >>> [true, false]

        chuckNorrisPluginHandler.fact >>> [
                'Chuck Norris is never Mocked 1 (config)',
                'Chuck Norris is never Mocked 2 (config)',
                'Chuck Norris is never Mocked 3 (config)']

        def matrixProject = configure(chuckNorrisPluginHandler, 3)

        when:
        def build = matrixProject.scheduleBuild2(0).get()

        then:

        build.logFile.text.contains('FATAL: No Chuck Norris Plugin Found')
        build.runs.size() == 0
    }

    @WithPlugin("matrix-project.hpi")
    def 'Dynamic No Plugin'() {
        given:
        def chuckNorrisPluginHandler = Mock(IFact)
        chuckNorrisPluginHandler.installed() >> false

        def matrixProject = configure(chuckNorrisPluginHandler, 2)

        when:
        def build = matrixProject.scheduleBuild2(0).get()

        then:
        build.logFile.text.contains('SUCCESS')
        build.runs.size() == 0
    }

    @WithPlugin("matrix-project.hpi")
    def 'Empty Axis'() {
        given:
        def matrixProject = rule.createMatrixProject()

        AxisList axl = new AxisList()
        def axis = new ChuckNorrisAxis('TEST', null)

        axl.add(axis)
        matrixProject.setAxes(axl)
        def build = matrixProject.scheduleBuild2(0).get()

        expect:
        build.logFile.text.contains('SUCCESS')
        build.runs.size() == 0
    }

    @WithPlugin("matrix-project.hpi")
    def 'Manual'() {
        given:
        def matrixProject = configureManual()

        when:
        def build = matrixProject.scheduleBuild2(0).get()

        then:
        build.logFile.text.contains('SUCCESS')
        build.runs.every { it.logFile.text.contains('SUCCESS') }
        build.runs.size() == 2

    }
}
