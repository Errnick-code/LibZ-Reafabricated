package net.libz.registry;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.LibzClient;
import net.libz.api.*;
import net.libz.util.SortList;

/**
 * Class to register tabs for screens. Use only on the client.
 *
 * @version 1.0
 */
@Environment(EnvType.CLIENT)
public class TabRegistry {

    /**
     * Registers a player inventory tab.
     *
     * @param tab An instance of an InventoryTab class.
     */
    public static void registerInventoryTab(InventoryTab tab) {
        LibzClient.inventoryTabs.add(tab);
        // Sort prefered pos
        List<Integer> priorityList = new ArrayList<Integer>();
        for (int i = 0; i < LibzClient.inventoryTabs.size(); i++) {
            int preferedPos = LibzClient.inventoryTabs.get(i).getPreferedPos();
            if (preferedPos == -1) {
                preferedPos = 99;
            }
            priorityList.add(preferedPos);
        }
        SortList.concurrentSort(priorityList, LibzClient.inventoryTabs);
    }

    /**
     * Registers a tab for all screens.
     * 
     * <p>
     * Handled screens do not need to call DrawTabHelper.drawTab on the render method and DrawTabHelper.onTabButtonClick on the mouseClicked method. Client screens need to call DrawTabHelper.drawTab
     * on the render method and DrawTabHelper.onTabButtonClick on the mouseClicked method.
     * 
     * @param tab         An instance of an InventoryTab class.
     * @param parentClass The parent class of the screen class where the tab will get added.
     */
    public static void registerOtherTab(InventoryTab tab, Class<?> parentClass) {
        if (LibzClient.otherTabs.get(parentClass) != null) {
            LibzClient.otherTabs.get(parentClass).add(tab);
            // Sort prefered pos
            List<Integer> priorityList = new ArrayList<Integer>();
            for (int i = 0; i < LibzClient.otherTabs.get(parentClass).size(); i++) {
                int preferedPos = LibzClient.otherTabs.get(parentClass).get(i).getPreferedPos();
                if (preferedPos == -1) {
                    preferedPos = 99;
                }
                priorityList.add(preferedPos);
            }
            SortList.concurrentSort(priorityList, LibzClient.otherTabs.get(parentClass));
        } else {
            List<InventoryTab> list = new ArrayList<InventoryTab>();
            list.add(tab);
            LibzClient.otherTabs.put(parentClass, list);
        }
    }
}
