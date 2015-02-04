package org.jenkinsci.chucknorrisaxis.dsl

import org.jenkinsci.chucknorrisaxis.ChuckNorrisAxis
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter
import hudson.Extension
/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension
class Axis extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Axis{

    @Override
    public String getName(){
        return "chuckNorrisAxis";
    }

    @Override
    public String getDescription(){
        return "Add a Chuck Norris Axis in";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };

    @Method(description="Add a chuckNorrisAxis with a closure", closureClass = AxisClosure)
    public Object chuckNorris(@Parameter(description="Closure for axes") Object closure) {
        AxisClosure i = runClosure(closure, AxisClosure)
        new ChuckNorrisAxis(i.axisName, i.items)
    }


    @Method(description="Add a chuckNorrisAxis with only chucknorrisplugin entries")
    public Object chuckNorris(@Parameter(description="Axis name") String name) {
        return ChuckNorrisAxis.factory(name, true, [])
    }

    @Method(description="Add a chuckNorrisAxis with only manual (List) entries")
    public Object chuckNorrisManual(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") List<String> quotes) {
        return ChuckNorrisAxis.factory(name, false, quotes)
    }

    @Method(description="Add a chuckNorrisAxis with only manual (vaargs) entries")
    public Object chuckNorrisManual(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") String... quotes) {
        return ChuckNorrisAxis.factory(name, false, Arrays.asList(quotes))
    }

    @Method(description="Add a chuckNorrisAxis with chucknorrisplugin and (List) manual entries")
    public Object chuckNorrisBoth(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") List<String> quotes) {
        return ChuckNorrisAxis.factory(name, true, quotes)
    }

    @Method(description="Add a chuckNorrisAxis with chucknorrisplugin and (vaargs) manual entries")
    public Object chuckNorrisBoth(@Parameter(description="Axis name") String name, @Parameter(description="The quotes to add as individual axis") String... quotes) {
        return ChuckNorrisAxis.factory(name, true, Arrays.asList(quotes))
    }
}
