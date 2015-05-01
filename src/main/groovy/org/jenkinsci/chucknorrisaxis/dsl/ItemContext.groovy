package org.jenkinsci.chucknorrisaxis.dsl

import javaposse.jobdsl.dsl.Context
import jenkins.model.Jenkins
import org.jenkinsci.chucknorrisaxis.ChuckNorrisAxis
import org.jenkinsci.chucknorrisaxis.ChuckNorrisItem

/**
 * Created by jeremymarshall on 1/01/2015.
 */

class ItemContext implements Context{

    def desc = Jenkins.instance.getDescriptor( ChuckNorrisAxis )
    def items = []

    public void auto() {

        def defs = desc.loadDefaultItems()

        defs.each{
            items << it
        }
    }

    public void manual( List<String> quotes) {
       quotes.each{
            items << new ChuckNorrisItem(it)
        }
    }

    public void manual(String... quotes) {
        manual(Arrays.asList(quotes))
    }

}
