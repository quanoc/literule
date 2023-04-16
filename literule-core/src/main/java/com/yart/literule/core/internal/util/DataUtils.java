package com.yart.literule.core.internal.util;

import com.yart.literule.core.model.data.DataType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataUtils {
    public static DataType getDataType(Object obj){
        DataType dataType=null;
        if(obj==null){
            dataType=DataType.Object;
        }else{
            if(obj instanceof Integer){
                dataType=DataType.Integer;
            }else if(obj instanceof Long){
                dataType=DataType.Long;
            }else if(obj instanceof Double){
                dataType=DataType.Double;
            }else if(obj instanceof Float){
                dataType=DataType.Float;
            }else if(obj instanceof BigDecimal){
                dataType=DataType.BigDecimal;
            }else if(obj instanceof Boolean){
                dataType=DataType.Boolean;
            }else if(obj instanceof Date){
                dataType=DataType.Date;
            }else if(obj instanceof List){
                dataType=DataType.List;
            }else if(obj instanceof Set){
                dataType=DataType.Set;
            }else if(obj instanceof Enum){
                dataType=DataType.Enum;
            }else if(obj instanceof Map){
                dataType=DataType.Map;
            }else if(obj instanceof String){
                dataType=DataType.String;
            }else if(obj instanceof Character){
                dataType=DataType.Char;
            }else{
                dataType=DataType.Object;
            }
        }
        return dataType;
    }
}
