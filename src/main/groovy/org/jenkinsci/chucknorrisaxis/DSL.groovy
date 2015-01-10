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

    public final boolean hasMethods(){
        return true;
    };


    @Method(description="Add a chuckNorrisAxis with only chucknorrisplugin entries")
    public Object chuckNorris(@Parameter(description="Axis name") String name) {
        return ChuckNorrisAxis.factory(name, true, []);
    }

    @Method(description="Add a chuckNorrisAxis with only manual (List) entries")
    public Object chuckNorrisManual(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") List<String> quotes) {
        return ChuckNorrisAxis.factory(name, false, quotes);
    }

    @Method(description="Add a chuckNorrisAxis with only manual (vaargs) entries")
    public Object chuckNorrisManual(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") String... quotes) {
        return ChuckNorrisAxis.factory(name, false, Arrays.asList(quotes));
    }

    @Method(description="Add a chuckNorrisAxis with chucknorrisplugin and (List) manual entries")
    public Object chuckNorrisBoth(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") List<String> quotes) {
        return ChuckNorrisAxis.factory(name, true, quotes);
    }

    @Method(description="Add a chuckNorrisAxis with chucknorrisplugin and (vaargs) manual entries")
    public Object chuckNorrisBoth(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") String... quotes) {
        return ChuckNorrisAxis.factory(name, true, Arrays.asList(quotes));
    }
}
