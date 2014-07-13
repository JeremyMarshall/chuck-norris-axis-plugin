package org.jenkinsci.chucknorrisaxis

import hudson.plugins.chucknorris.FactGenerator
import jenkins.model.Jenkins

/**
 * Created by jeremy on 10/07/2014.
 */
class ChuckNorrisPluginNotInstalled implements IFact {

    ChuckNorrisPluginNotInstalled() {
    }

    boolean installed() {
         false
    }

    final String getFact() {
        null
    }
}
