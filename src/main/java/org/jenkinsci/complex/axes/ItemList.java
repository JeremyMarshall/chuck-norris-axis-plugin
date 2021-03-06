package org.jenkinsci.complex.axes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Created by jeremymarshall on 3/09/2014.
 */
public class ItemList<E> extends ArrayList {

    public static List<Item> emptyList(){
        return Collections.emptyList();
    }

    @Override public String toString() {

        StringBuffer ret = new StringBuffer();
        for (Object i : this) {
            ret.append(i.toString()).append("\n");
        }

        return ret.toString();
    }
}
