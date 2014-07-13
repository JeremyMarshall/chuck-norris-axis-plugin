package org.jenkinsci.chucknorrisaxis

import groovy.transform.InheritConstructors
import hudson.Extension
import hudson.util.FormValidation
import org.jenkinsci.complex.axes.Item
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.QueryParameter

class ChuckNorrisItem extends  Item implements Comparable {

    protected String quote

    ChuckNorrisItem() {
        quote = 'Chuck Norris is his own DataBoundConstructor'
    }

    @DataBoundConstructor
    ChuckNorrisItem(String quote) {
        this.quote = quote
    }

    @Override
    String toString() {
        String.format('%s', quote)
    }

    String getQuote() {
        this.quote
    }

    @Extension
    @InheritConstructors
    static class DescriptorImpl extends ItemDescriptor {
        final String displayName = 'Your Own Chuck Norris Quote'

        FormValidation doCheckQuote(@QueryParameter String value) {
            if (value.isEmpty()) {
                return FormValidation.error('You must provide a quote')
            }
            if (!value.contains( /Chuck Norris/)) {
                return FormValidation.error('Quote must contain Chuck Norris')
            }
            FormValidation.ok()
        }
    }
}
