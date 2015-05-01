package org.jenkinsci.chucknorrisaxis.dsl

import org.jenkinsci.chucknorrisaxis.ChuckNorrisAxis
import hudson.Extension
import javaposse.jobdsl.dsl.DslExtensionMethod
import javaposse.jobdsl.plugin.ContextExtensionPoint
/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension(optional = true)
class Axis extends ContextExtensionPoint {

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorris(Runnable closure) {
        AxisContext context = new AxisContext();
        executeInContext(closure, context);

        new ChuckNorrisAxis(context.axisName, context.items);
    }

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorris( String name) {
        return ChuckNorrisAxis.factory(name, true, [])
    }

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorrisManual(String name, List<String> quotes) {
        return ChuckNorrisAxis.factory(name, false, quotes)
    }

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorrisManual(String name, String... quotes) {
        return ChuckNorrisAxis.factory(name, false, Arrays.asList(quotes))
    }

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorrisBoth(String name, List<String> quotes) {
        return ChuckNorrisAxis.factory(name, true, quotes)
    }

    @DslExtensionMethod(context = AxisContext)
    public Object chuckNorrisBoth(String name, String... quotes) {
        return ChuckNorrisAxis.factory(name, true, Arrays.asList(quotes))
    }
}
