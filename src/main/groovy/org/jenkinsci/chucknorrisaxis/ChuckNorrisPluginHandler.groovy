package org.jenkinsci.chucknorrisaxis

import hudson.plugins.chucknorris.FactGenerator
import jenkins.model.Jenkins

/**
 * Created by jeremy on 10/07/2014.
 */
class ChuckNorrisPluginHandler implements IFact {

    FactGenerator factGenerator

    ChuckNorrisPluginHandler() {
        if (Jenkins.instance.getPlugin('chucknorris') != null) {
            factGenerator =  new FactGenerator()
        }
    }

    boolean installed() {
         factGenerator != null
    }

    final String getFact() {
        factGenerator.random()
    }
}
