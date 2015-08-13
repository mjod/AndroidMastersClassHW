package com.example.matthew.lab_7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 3/1/2015.
 */
public class DrawerData {
    List<Map<String,?>> dataList;
    public List<Map<String,?>> getDataList(){
        return dataList;
    }
    public int getSize() {
        return dataList.size();
    }
    public HashMap getItem(int i){
        if (i >=0 && i < dataList.size()){
            return (HashMap) dataList.get(i);
        } else return null;
    }
    public void makeFalse(){
        for (Map<String,?> data : dataList){
            final HashMap<String,Boolean> itemMap_bool = (HashMap<String,Boolean>) data;
            itemMap_bool.put("selection", false);
        }
    }
    public DrawerData(){
        dataList = new ArrayList<Map<String,?>>();
        String title = "Home";
        Integer image = R.drawable.search;
        Integer viewType = 0;
        boolean selection = true;
        dataList.add(createDrawer(title,image, selection,viewType));
        title = "Movie List View";
        image = R.drawable.threedot;
        selection = false;
        dataList.add(createDrawer(title,image, selection,viewType));
        title = "Movie Grid View";
        image = R.drawable.trashcan;
        selection = false;
        dataList.add(createDrawer(title,image, selection,viewType));
        viewType = 1;
        dataList.add(createDrawer(title,image, selection,viewType));
        viewType = 0;
        title = "Simple Fragment 1";
        image = null;
        selection = false;
        dataList.add(createDrawer(title,image, selection,viewType));
        title = "Simple Fragment 2";
        image = null;
        selection = false;
        dataList.add(createDrawer(title,image, selection,viewType));
        title = "Simple Fragment 3";
        image = null;
        selection = false;
        dataList.add(createDrawer(title,image, selection,viewType));

    }
    private HashMap createDrawer(String name, Integer image, boolean selection, Integer viewType) {
        HashMap drawer = new HashMap();
        drawer.put("image",image);
        drawer.put("name", name);
        drawer.put("selection", selection);
        drawer.put("viewtype", viewType);
        return drawer;
    }
}
