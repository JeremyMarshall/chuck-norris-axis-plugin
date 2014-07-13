package org.jenkinsci.chucknorrisaxis

import hudson.Extension
import jenkins.model.Jenkins
import org.jenkinsci.complex.axes.Container
import org.jenkinsci.complex.axes.ContainerDescriptor
import org.jenkinsci.complex.axes.Item
import org.kohsuke.stapler.DataBoundConstructor

class ChuckNorrisContainer extends  Container {

    ChuckNorrisContainer() {
        super( [] )
    }

    @DataBoundConstructor
    ChuckNorrisContainer(List<ChuckNorrisItemRO> items) {
        super( items)
    }

    List<ChuckNorrisItemRO> getItems() {
        axisItems
    }

    void setItems(List<ChuckNorrisItemRO> sc) {
        setAxisItems(sc)
    }

    @Override
    String toString() {
        'Quotes from Chuck Norris Plugin'
    }

    @Override
    List<String> rebuild(List<String> list) {
        ChuckNorrisContainer.DescriptorImpl cncd = Jenkins.instance.getDescriptorOrDie(ChuckNorrisContainer)

        List<ChuckNorrisItemRO> cni = cncd.loadDefaultItems()

        if (cni.size() == 0) {
            throw (new ChuckNorrisException('No Chuck Norris Plugin Found'))
        }

        setItems(cni)

        cni.each { list << it.toString() }
        list
    }

    @Override
    List<String> getValues(List<String> list) {
        items.each { list.add( it.toString()) }

        if (list.size() == 0) {
            list << 'Rebuilt at build time'
        }
        list
    }

    @Extension
    static class DescriptorImpl extends ContainerDescriptor {
        final String displayName = 'Chuck Norris Plugin Quotes'

        @Override
        List<? extends Item> loadDefaultItems(List<? extends Item> items) {
            items << new ChuckNorrisContainer(loadDefaultItems())

            items
        }

        @Override
        List<ChuckNorrisItemRO> loadDefaultItems() {
            Jenkins.instance.getDescriptorOrDie(ChuckNorrisAxis).items
        }
    }
}
