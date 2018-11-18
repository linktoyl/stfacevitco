package cn.vitco.seetaface;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SeetaFace {


    static{
//    	System.setProperty( "java.library.path", "D:\\vc_projects\\NativeDemoSeeta1\\x64\\Debug" );
        //System.out.println("need for SeetaFaceJNIDLL.dll under java.library.path---"+ System.getProperty("java.library.path"));
        System.loadLibrary("SeetaFaceJNIDLL");
    }

    /**
     * 人脸特征比对
     * @param vFeat1：人脸1特征
     * @param vFeat2：人脸2特征
     * @return 相似度范围在0~1,返回负数表示出错
     */
    public native float CalcSimilarity(float[] vFeat1, float[] vFeat2);

    /**检测人脸
     *
     * @param vPath：图像路径
     * @return
     */
    public CMSeetaFace[] DetectFacesPath(String vPath){
        //String tag = "DetectFacesPath";
        if(null == vPath || vPath.equals("")){
            return null;
        }

        CMSeetaFace[] tFaces = null; // new CMSeetaFace[0];

        try {
            BufferedImage image = ImageIO.read(new FileInputStream(vPath));
            //System.out.println(tag + ", image size="+ image.getWidth()+", "+image.getHeight());

            byte[] tBmpData = ((DataBufferByte) image.getData().getDataBuffer()).getData();
            int tWidth = image.getWidth();
            int tHeight = image.getHeight();
            //tCh 是计算的图像通道
            int tCh = tBmpData.length / (tWidth*tHeight);

            tFaces = DetectFacesByte(tBmpData, tWidth, tHeight);
            if(null != tFaces){
                //System.out.println(tag + " facenum=" + tFaces.length);
                //System.out.println(" tFaces[0].bottom"+   		tFaces[0].bottom);
            }else{
                //System.out.println(tag + " no face");
            }

            image = null;
            tBmpData = null;
            System.gc();
        } catch (FileNotFoundException e) {
            System.out.println("e="+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("e="+e.toString());
            e.printStackTrace();
        }

        return tFaces;
    }

    /**检测人脸
     *
     * @param vImage：图像
     * @return
     */
    public CMSeetaFace[] DetectFacesImage(BufferedImage vImage){
        //String tag = "DetectFacesPath";
        if(null == vImage){
            return null;
        }

        CMSeetaFace[] tFaces = null; // new CMSeetaFace[0];

        //System.out.println(tag + ", image size="+ vImage.getWidth()+", "+vImage.getHeight());

        byte[] tBmpData = ((DataBufferByte) vImage.getData().getDataBuffer()).getData();
        int tWidth = vImage.getWidth();
        int tHeight = vImage.getHeight();
        int tCh = tBmpData.length / (tWidth*tHeight);

        tFaces = DetectFacesByte(tBmpData, tWidth, tHeight);
        if(null != tFaces){
            //System.out.println(tag + " facenum=" + tFaces.length);
            //System.out.println(" tFaces[0].bottom"+   		tFaces[0].bottom);
        }else{
            //System.out.println(tag + " no face");
        }

        tBmpData = null;
        System.gc();
        return tFaces;
    }

    /**
     * 检测人脸
     * @param vBmpByte
     * @param vWidth
     * @param vHeight
     */
    public native CMSeetaFace[] DetectFacesByte(byte[] vBmpByte, int vWidth, int vHeight);

    /**
     * 初始化，指定人脸识别模型文件目录，该目录下应当包括这3个文件：
     * seeta_fd_frontal_v1.0.bin,
     * seeta_fa_v1.1.bin,
     * seeta_fr_v1.0.bin
     * @param vModelDir
     * @return
     */

    public native boolean initModelPath(String vModelDir);
}  