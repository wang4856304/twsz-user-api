package com.twsz.utils;


import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.twsz.exception.BusinessException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Hessian2Util {

    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serializable(Object object) {
        //定义一个字节数组输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //对象输出流
        HessianOutput out = new HessianOutput(os);
        try {
            //将对象写入到字节数组输出，进行序列化
            out.writeObject(object);
            byte[] bytes = os.toByteArray();
            return bytes;
        }
        catch (Exception e) {
            throw new BusinessException("序列化对象失败", e);
        }
        finally {
            try {
                out.close();
                os.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object deserializable(byte[] bytes) {

        ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        //Hessian的反序列化读取对象
        HessianInput hi = new HessianInput(is);
        try {
            Object value = hi.readObject();
            return value;
        }
        catch (Exception e) {
            throw new BusinessException("反序列化对象失败", e);
        }
        finally {
            try {
                hi.close();
                is.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
