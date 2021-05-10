package com.quaero.qbcTest.dto.entity;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.quaero.qbcTest.Enum.ReaType;

public class ReagentIDManager
	    {
	        private static int _maxReaTypeID = 0;
	        private static Dictionary<String, Integer> _itemDict =new  Hashtable<>();
	        private static Object _singletonLock = new Object();
	        private Lock lock = new ReentrantLock();

	        public static void Clear()
	        {
	        	synchronized(_singletonLock)
	            {
	                _maxReaTypeID = 0;
	                _itemDict=new  Hashtable<>();
	            }
	        }

	        public static int GetReaTypeID(int moduleId, String reaName, ReaType reaType)
	        {
	            int itemID = 0;
	            String key = GetKey(moduleId, reaName, reaType);
	            synchronized(_singletonLock)
	            {
	            	if(_itemDict.get(key) == null){
	            		 itemID = GenerateID();
		                    _itemDict.put(key, itemID);
	            	}
//	                if (!_itemDict.TryGetValue(key, out itemID))
//	                {
//	                    itemID = GenerateID();
//	                    _itemDict.put(key, itemID);
//	                }
	            }
	            return itemID;
	        }

	        public static int GetDiluentID(int moduleId, String itemName)
	        {
	            return GetReaTypeID(moduleId, ReaType.Diluent.toString(), ReaType.Diluent);
	        }

	        public static int GetAlkalineDetergentID(int moduleId, String itemName)
	        {
	            return GetReaTypeID(moduleId, ReaType.AlkalineDetergent.toString(), ReaType.AlkalineDetergent);
	        }

	        public static int GetPhosphorFreeDetergentID(int moduleId, String itemName)
	        {
	            return GetReaTypeID(moduleId, ReaType.PhosphorFreeDetergent.toString(), ReaType.PhosphorFreeDetergent);
	        }

	        private static String GetKey(int muduleId, String reaName, ReaType reaType)
	        {
	            return String.format("%d_%s_%d", muduleId, reaName, reaType.getValue());
	        }

	        private static int GenerateID()
	        {
	        	synchronized(_singletonLock)
	            {
	                _maxReaTypeID++;
	                return _maxReaTypeID;
	            }
	        }
	    }
