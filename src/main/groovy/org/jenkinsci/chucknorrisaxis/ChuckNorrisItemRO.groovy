package org.jenkinsci.chucknorrisaxis

import hudson.Extension
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor

class ChuckNorrisItemRO extends ChuckNorrisItem {

    ChuckNorrisItemRO() {
        super()
    }

    @DataBoundConstructor
    ChuckNorrisItemRO(String quote) {
        super(quote)
    }

    @Extension
    static class DescriptorImpl extends ItemDescriptor {
        final String displayName = 'Chuck Norris Item RO'
    }

}
