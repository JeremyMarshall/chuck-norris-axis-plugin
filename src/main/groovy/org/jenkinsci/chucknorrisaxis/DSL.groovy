package org.jenkinsci.chucknorrisaxis

import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import hudson.Extension
/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension
class DSL extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis{

    @Override
    public String getName(){
        return "chuckNorrisAxis";
    }

    @Override
    public String getDescription(){
        return "Add a Chuck Norris Axis in";
    }

    @Method(name="chuckNorrisAxis", description="Add a chuckNorrisAxis with only chucknorrisplugin entries")
    public Object chuckNorris() {
        return new ChuckNorrisAxis();
    }

    @Method(name="chuckNorrisAxis", description="Add a chuckNorrisAxis with manual entries")
    public Object chuckNorrisManual(@Parameter(name="quotes", description="The quotes to add as individual axis") String... quotes) {
        return new ChuckNorrisAxis();
    }

    @Method(name="chuckNorrisAxis", description="Add a chuckNorrisAxis with only manual entries")
    public Object chuckNorrisManual(@Parameter(name="quotes", description="The quotes to add as individual axis") List<String> quotes) {
        return new ChuckNorrisAxis();
    }

    @Method(name="chuckNorrisAxis", description="Add a chuckNorrisAxis with chucknorrisplugin and manual entries")
    public Object chuckNorrisBoth(@Parameter(name="quotes", description="The quotes to add as individual axis") String... quotes) {
        return new ChuckNorrisAxis();
    }

    @Method(name="chuckNorrisAxis", description="Add a chuckNorrisAxis with chucknorrisplugin and manual entries")
    public Object chuckNorrisBoth(@Parameter(name="quotes", description="The quotes to add as individual axis") List<String> quotes) {
        return new ChuckNorrisAxis();
    }
}
