package com.quaero.qbcTest.dto.entity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import lombok.Data;

@Data
public class DeviceDataModuleStatus {

	/// <summary>
    /// 模块工作状态
    /// </summary>
    public byte WorkStatus;
    /// <summary>
    /// 反应槽温度
    /// </summary>
    public int ReactTemperature;
    /// <summary>
    /// 制冷温度
    /// </summary>
    public int RefTemperature;

    public String GetReactTemperatureText()
    {
        byte b1 = (byte)(this.ReactTemperature >> 8);
        byte b2 = (byte)(this.ReactTemperature);
        float f = (float)b1 + (float)b2 / 100;
        return  roundByScale((float)this.RefTemperature,2);
    }

    public String GetRefTemperatureText()
    {
    	/*byte b1 = (byte)(this.RefTemperature >> 8);
    	byte b2 = (byte)(this.RefTemperature);
        float f = (float)b1 + (float)b2 / 100;*/
    	//return  roundByScale((float)this.RefTemperature,2);
    	 return  new DecimalFormat("#.00").format(Double.parseDouble(this.RefTemperature+""));
        //return  String.format("0.00", RefTemperature);
    }
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

}
