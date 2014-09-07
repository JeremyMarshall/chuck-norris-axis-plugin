package org.jenkinsci.complex.axes;

import hudson.matrix.MatrixBuild;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Axis extends hudson.matrix.Axis {

    private List<? extends Item> axisItems;

    public Axis(String name, String... value){
        super(name, value);
        axisItems = new ArrayList<Item>();
    }

    public Axis(String name, List<? extends Item> axisItems){
        super(name, Axis.convertToAxisValue(axisItems));
        this.axisItems = (axisItems!=null)?axisItems: ItemList.emptyList();
    }

    public static List<String> convertToAxisValue(List<? extends Item> axisItems){
        List<String> ret = new ArrayList<String>();

        if(axisItems == null)
            axisItems = ItemList.emptyList();

        for (Item item : axisItems) {
            String i = item.toString();
            if( i.length() > 0){
                ret.add(item.toString());
            }
        }
        //there has to be something here for the Axis.value
        if (ret.size() == 0)
            ret.add("default");

        return ret;
    }

    public List<? extends Item> getAxisItems(){
        return Collections.unmodifiableList(axisItems);
    }

    @Override
    public void addBuildVariable(String value, Map<String,String> map){}

    //@Override
    //public String getValueString(){
    //    String ret = convertToAxisValue(this.axisItems).toString();
    //    if(ret == null){
    //        return ret;
    //    }else{
    //        return "default";
    //    }
    //}

    @Override
    public List<String> rebuild( MatrixBuild.MatrixBuildExecution context )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < axisItems.size(); i++){
            axisItems.get(i).rebuild(ret);
        }

        return ret;

    }

    @Override
    public List<String> getValues( )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < axisItems.size(); i++){
            axisItems.get(i).getValues(ret);
        }
        return ret;
    }
}
