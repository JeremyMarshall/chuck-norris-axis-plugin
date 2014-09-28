/*
* The MIT License
*
* Copyright (c) 2010, InfraDNA, Inc.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package org.jenkinsci.chucknorrisaxis

import groovy.transform.InheritConstructors
import hudson.Extension
import jenkins.model.Jenkins
import org.jenkinsci.complex.axes.Axis
import org.jenkinsci.complex.axes.AxisDescriptor
import org.jenkinsci.complex.axes.Item
import org.jenkinsci.complex.axes.ItemList
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.QueryParameter
import hudson.util.FormValidation

class ChuckNorrisAxis extends Axis {

    @DataBoundConstructor
    ChuckNorrisAxis(String name, List<? extends Item> items) {
        super(name, items)
    }

    List<? extends ChuckNorrisItem> getItems() {
        axisItems as List<? extends ChuckNorrisItem>
    }

    @Override
    void addBuildVariable(String value, Map<String,String> map) {
       map.put(name, value)
    }

    @Extension
    @InheritConstructors
    static class DescriptorImpl extends AxisDescriptor {
        private int count = 3
        @SuppressWarnings('PrivateFieldCouldBeFinal')
        private IFact chuckNorrisPluginHandler
        final String displayName = 'Chuck Norris Quote Axis'

        DescriptorImpl() {
            if (Jenkins.instance.getPlugin('chucknorris') != null) {
                chuckNorrisPluginHandler = new ChuckNorrisPluginHandler()
            } else {
                chuckNorrisPluginHandler = new ChuckNorrisPluginNotInstalled()
            }
        }

        int getCount() {
            if ( count == null ) {
                3
            } else {
                count
            }
        }

        void setCount(int count) {
            this.count = count
        }

        List<ItemDescriptor> axisItemTypes() {
            def cait = Jenkins.instance.<Item,ItemDescriptor>getDescriptorList(Item)

            def ret = []

            for ( i in cait ) {
                def name = i.getClass().name

                //don't want the RO version to appear in the add list as it is added as part of the Container item
                //And only include the container if the chucknorris plugin is installed!

                if (name =~ /ChuckNorrisContainer/ ) {
                    if (chuckNorrisPluginHandler.installed() ) {
                        ret << i
                    }
                } else if (!(name =~ /ChuckNorrisItemRO/ )) {
                    ret << i
                }
            }
            ret
        }

        List<? extends ChuckNorrisItem> getItems() {
            try {
                if (chuckNorrisPluginHandler.installed()) {
                    def ret = new ItemList<ChuckNorrisItemRO>()
                    for (int i = 0; i < count; i++) {
                        ret << new ChuckNorrisItemRO( chuckNorrisPluginHandler.fact )
                    }
                    return ret
                } else {
                    ItemList.emptyList()
                }
            } catch (ex) {
                ItemList.emptyList()
            }
        }

        FormValidation doCheckCount(@QueryParameter String value) {
            if (value.isEmpty()) {
                return FormValidation.error('You must provide a count')
            }
            if (!value.isNumber()) {
                return FormValidation.error('Count must be a number between 1 and 10')
            }
            FormValidation.ok()
        }
    }
}
