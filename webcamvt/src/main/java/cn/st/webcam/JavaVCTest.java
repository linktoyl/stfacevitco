package cn.st.webcam;

import cn.vitco.seetaface.CMSeetaFace;
import cn.vitco.seetaface.FileUtils;
import cn.vitco.seetaface.SeetaFace;
import org.bytedeco.javacpp.opencv_core;

import org.bytedeco.javacv.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_core.*;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class JavaVCTest {

    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {
        //System.out.println(System.getProperty("java.library.path"));

        shibie();
    }

    public static void register()  throws FrameGrabber.Exception, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(640,480);
        canvas.setAlwaysOnTop(true);
        int ex = 0;
        SeetaFace tSeetaFace = new SeetaFace();

        tSeetaFace.initModelPath("D:/test");
        while(true)
        {
            if(!canvas.isDisplayable()||ex>1000)
            {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(0);//退出
                break;
            }

            //canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示，
            // 这里的frame是一帧视频图像
            Frame frame=grabber.grab();
            opencv_core.Mat mat = converter.convertToMat(frame);
            IplImage image = converter.convertToIplImage(frame);
            //将图片指针转为二进制byte[]  start
            Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
            BufferedImage bufferedImage= java2dFrameConverter.convert(grabber.grabFrame());

            CMSeetaFace[] tFaces = tSeetaFace.DetectFacesImage(bufferedImage);
            if(null != tFaces){
                System.out.println("DetectFacesByte facenum=" + tFaces.length);
                if(tFaces.length==1) {
                    String tFeatPath = "e:\\facedata\\yanglin.txt";
                    FileUtils.saveFloatArray(tFaces[0].features, tFeatPath);
                    grabber.stop();//停止抓取
                    System.exit(0);//退出
                    break;
                }else{
                    System.out.println("注册时请保证1个人的环境！");
                }

            }else{
                System.out.println("DetectFacesByte no face");
            }

            canvas.showImage(frame);

            ex++;
            //opencv_imgcodecs.imwrite("f:\\img\\" + ex + ".png", mat);
            Thread.sleep(20);//50毫秒刷新一次图像
        }
    }

    public static void shibie() throws FrameGrabber.Exception, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(640,480);
        canvas.setAlwaysOnTop(true);
        int ex = 0;
        SeetaFace tSeetaFace = new SeetaFace();

        tSeetaFace.initModelPath("D:/test");
        while(true)
        {
            if(!canvas.isDisplayable()||ex>1000)
            {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(0);//退出
                break;
            }

            //canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示，
            // 这里的frame是一帧视频图像
            Frame frame=grabber.grab();
            opencv_core.Mat mat = converter.convertToMat(frame);
            IplImage image = converter.convertToIplImage(frame);
            //将图片指针转为二进制byte[]  start
            Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
            BufferedImage bufferedImage= java2dFrameConverter.convert(grabber.grabFrame());


            float[] srcFeat = FileUtils.loadFloatArray("e:\\facedata\\yanglin.txt", 2048);
            CMSeetaFace[] tFaces = tSeetaFace.DetectFacesImage(bufferedImage);
            if(null != tFaces){
                System.out.println("DetectFacesByte facenum=" + tFaces.length);
                if(tFaces.length>0) {
                    for(int i=0; i<tFaces.length; i++) {
                        CMSeetaFace stf = tFaces[i];
                        cvRectangle(image, cvPoint(stf.left, stf.top), cvPoint(stf.right, stf.bottom), CV_RGB(255, 0, 0), 1, 4, 0);
                        for(int j=0;j+1<10;j+=2) {
                            cvCircle(image, cvPoint(stf.landmarks[j], stf.landmarks[j + 1]), 2, CV_RGB(0, 255, 0), -1, 0, 0);
                        }
                        float tSim = tSeetaFace.CalcSimilarity(stf.features, srcFeat);
                        System.out.println("匹配结果:" + tSim);


                        frame = converter.convert(image);
                        System.out.println(" tFaces[0].features" + tFaces[0].left);
                    }
                }

            }else{
                System.out.println("DetectFacesByte no face");
            }

            canvas.showImage(frame);

            ex++;
            //opencv_imgcodecs.imwrite("f:\\img\\" + ex + ".png", mat);
           // Thread.sleep(20);//50毫秒刷新一次图像
        }
    }
}
