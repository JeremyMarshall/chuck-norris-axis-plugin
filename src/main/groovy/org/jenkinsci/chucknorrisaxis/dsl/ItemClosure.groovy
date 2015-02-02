package org.jenkinsci.chucknorrisaxis.dsl

import hudson.Extension
import jenkins.model.Jenkins
import org.jenkinsci.chucknorrisaxis.ChuckNorrisAxis
import org.jenkinsci.chucknorrisaxis.ChuckNorrisItem
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter

/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension
class ItemClosure extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure{

    def desc = Jenkins.instance.getDescriptor( ChuckNorrisAxis.class )
    def items = []

    @Override
    public String getName(){
        return "chuckNorrisItemClosure";
    }

    @Override
    public String getDescription(){
        return "ChuckNorris Item closure";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };

    @Method(description="Populate from Chuck Norris Plugin (if installed)")
    public void auto() {

        def defs = desc.loadDefaultItems()

        defs.each{
            items << it
        }
    }

    @Method(description="Populate with (List) manual entries")
    public void manual(@Parameter(description="The quotes to add as individual items") List<String> quotes) {
       quotes.each{
            items << new ChuckNorrisItem(it)
        }
    }

    @Method(description="Populate with (vaargs) manual entries")
    public void manual(@Parameter(description="The quotes to add as individual axis") String... quotes) {
        manual(Arrays.asList(quotes))
    }

}
