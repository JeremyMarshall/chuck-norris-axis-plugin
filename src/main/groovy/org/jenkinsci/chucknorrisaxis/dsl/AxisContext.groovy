package org.jenkinsci.chucknorrisaxis.dsl

import javaposse.jobdsl.dsl.Context

/**
 * Created by jeremymarshall on 1/01/2015.
 */

class AxisContext implements Context{

    def items = []
    def axisName = 'axis'

    public void axisName(String axisName) {
        this.axisName = axisName
    }

    public Object items(Object closure) {
        ItemContext context = new ItemContext()
        executeInContext(closure, context)

        context.items.each {
            items << it
        }
    }
}
