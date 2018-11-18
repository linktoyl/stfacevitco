package cn.vitco.stface.common;



public class ByteUtiles {

    public static byte[] floatArrToBytes(float[] floats){

        byte[] bytes = null;
        if(floats!=null && floats.length>0){
            bytes = new byte[floats.length*4];
            for (int i = 0; i < floats.length; i++) {
                int tmp = Float.floatToIntBits(floats[i]);
                bytes[i*4+3] = (byte) (tmp >> 24);
                bytes[i*4+2] = (byte) (tmp >> 16);
                bytes[i*4+1] = (byte) (tmp >> 8);
                bytes[i*4] = (byte) (tmp & 0xff);
                /*byte[] tmp = float2byte(floats[i]);
                System.arraycopy(tmp,0, bytes, 4*i, 4);*/
            }

        }
        return bytes;
    }

    public static float[] bytesToFloatArr(byte[] data, int vCount){
        float[] tArr = new float[vCount];
        for (int i = 0; i < vCount; i++) {
            tArr[i] = byte2float(data, 4*i);
        }
        return tArr;
    }

    /**
     * 字节转换为浮点
     *
     * @param b 字节（至少4个字节）
     * @param index 开始位置
     * @return
     */
    public static float byte2float(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * 将int类型的数据转换为byte数组 原理：将int数据中的四个byte取出，分别存储
     *
     * @param n  int数据
     * @return 生成的byte数组
     */
    public static byte[] intToBytes2(int n) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (24 - i * 8));
        }
        return b;
    }

    /**
     * 浮点转换为字节
     *
     * @param f
     * @return
     */
    public static byte[] float2byte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = b.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }

        return dest;

    }
}
