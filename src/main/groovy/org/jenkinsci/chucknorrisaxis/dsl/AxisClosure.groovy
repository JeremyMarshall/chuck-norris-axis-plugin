package org.jenkinsci.chucknorrisaxis.dsl

import hudson.Extension
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter

/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension
class AxisClosure extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure{

    def items = []
    def axisName = 'axis'

    @Override
    public String getName(){
        return "chuckNorrisAxisClosure";
    }

    @Override
    public String getDescription(){
        return "ChuckNorris Axis closure";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    }

    @Override
    public Object getClosureDelegate() {
        return new ItemClosure()
    }

    @Method(description="Axis name")
    public void axisName(@Parameter(description = "The name of the axis") String axisName) {
        this.axisName = axisName
    }

    @Method(description="Add chuckNorrisItems with a closure")
    public Object items(@Parameter(description="Closure for items") Object closure) {

        ItemClosure i = runClosure(closure)

        i.items.each {
            items << it
        }
    }
}
