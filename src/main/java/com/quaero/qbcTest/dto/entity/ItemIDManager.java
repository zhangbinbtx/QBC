package com.quaero.qbcTest.dto.entity;

import java.util.Dictionary;
import java.util.Hashtable;

public class ItemIDManager {

	private static int _maxItemID = 0;
    private static Dictionary<String, Integer> _itemDict = new Hashtable<>();
    private static Object _singletonLock = new Object();

    public static void Clear()
    {
    	synchronized(_singletonLock)
        {
            _maxItemID = 0;
            _itemDict=new  Hashtable<>();
        }
    }

    public static int GetItemID(String itemName)
    {
        int itemID = 0;
        synchronized(_singletonLock)
        {
        	if(_itemDict.get(itemName) == null){
       		      itemID = GenerateID();
                   _itemDict.put(itemName, itemID);
       	    }
//            if (!_itemDict.TryGetValue(itemName, out itemID))
//            {
//                itemID = GenerateID();
//                _itemDict.put(itemName, itemID);
//            }
        }
        return itemID;
    }

    private static int GenerateID()
    {
    	synchronized(_singletonLock)
        {
            _maxItemID++;
            return _maxItemID;
        }
    }

}
