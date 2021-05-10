package com.quaero.qbcTest.dto.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.quaero.qbcTest.Enum.NetworkDataType;

public class MonitorNetworkData
{
    private NetworkDataType _dataType;
    private byte[] _data;
    private String _dataString = "";
    private Date _dataTime;
    private int _moduleId;
    private int _cmd;
    public String bytes_String16(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<b.length;i++) {
            sb.append(String.format("%02x", b[i]));
        }
        return sb.toString();
    }
	public MonitorNetworkData(NetworkDataType dataType, int moduleId, int cmd, byte[] data)  
    {
        this._dataType = dataType;
        this._data = data;
        this._dataString = GetDataString();
        this._dataTime = new Date();
        this._moduleId = moduleId;
        this._cmd = cmd;

    }

    private String GetDataString()
    {
        if ((null == _data) || (0 == _data.length))
        {
            return "";
        }
        return bytes_String16(_data).replace("-", " ").toUpperCase();
       // return BitConverter.ToString(_data, 0, _data.Length).Replace("-", " ").ToUpper();
    }
    public NetworkDataType getDataType() {
		return _dataType;
	}

	public void setDataType(NetworkDataType dataType) {
		this._dataType = dataType;
	}

    public String DataTypeString;
    
    public String DataTypeString() {
		return (_dataType == NetworkDataType.Send) ? "S>>" : "R<<";
	}

	public String ModuleString;
	
   /* {
        get { return string.Format("{0}", _moduleId); }
    }*/

    public String getModuleString() {
		return String.format("%d", _moduleId);
	}
    public String CmdString;
    
    
//    {
//        get { return string.Format("0x{0:X4}", _cmd); }
//    }

    public String getCmdString() {
		return Integer.toHexString(_cmd);
	}

	public byte[] Data;
    
//    {
//        get { return _data; }
//        set { _data = value; }
//    }

    public byte[] getData() {
		return Data;
	}

	public void setData(byte[] data) {
		Data = data;
	}
    
	public String get_dataString() {
		return _dataString;
	}


    public Date get_dataTime() {
		return _dataTime;
	}

	public void set_dataTime(Date _dataTime) {
		this._dataTime = _dataTime;
	}

	/*public DateTime DataTime
    {
        get { return _dataTime; }
        set { _dataTime = value; }
    }*/

    public String DataTimeString;

	public String getDataTimeString() {
		SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss:ffff");
		return sdf1.format(_dataTime);
	}
    
    /*{
        get { return _dataTime.ToString("HH:mm:ss:ffff"); }
    }*/
}