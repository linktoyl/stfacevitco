package cn.vitco.test;

import cn.vitco.stface.common.ByteUtiles;

public class Tester {

    public static void main(String[] args) {
        float[] src = new float[]{0.2f,0.1f,0.25f};

        byte[] bdata = ByteUtiles.floatArrToBytes(src);
        for (int i = 0; i < bdata.length; i++) {
            System.out.print(bdata[i]+",");
        }
        System.out.println("--");

        float[] b2f = ByteUtiles.bytesToFloatArr(bdata, 3);

        for (int i = 0; i < b2f.length; i++) {
            System.out.print(b2f[i]+",");
        }
        System.out.println("--");
        float ff = 0.2f;
        int f = Float.floatToIntBits(ff);

        byte[] ddd = ByteUtiles.float2byte(ff);
        float ffff = ByteUtiles.byte2float(ddd, 0);
        System.out.println(ffff);
    }
}
