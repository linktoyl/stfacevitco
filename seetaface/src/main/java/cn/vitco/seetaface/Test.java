package cn.vitco.seetaface;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
	   public static void main(String[] args) throws Exception{
		   System.out.println("need for SeetaFaceJNIDLL.dll under java.library.path---"+ System.getProperty("java.library.path"));
		   for(int i=0; i<200; i++){
			   System.out.print("第"+i+"次：");
			   seetaTest();
		   }
	    }

	public static void seetaTest1() {
		SeetaFace tSeetaFace = new SeetaFace();

		tSeetaFace.initModelPath("D:\\test");

		String imgPath1 = "e:\\img\\bnz12.bmp";
		String imgPath2 = "e:\\img\\bnz9.jpg";
		CMSeetaFace[] tFaces1 = tSeetaFace.DetectFacesPath(imgPath1);
		CMSeetaFace[] tFaces2 = tSeetaFace.DetectFacesPath(imgPath2);
		String tFeatPath = "e:\\seetafeat.txt";
		//	        float xf[] = {1.5f, 2.5f, 3.5f};
		//	        FileUtils.saveFloatArray(xf, tFeatPath);
		//	        float[] tf = FileUtils.loadFloatArray(tFeatPath, 3);
		//	        for(int i=0; i<3; i++){
		//	        	System.out.println("sim="+tf[i]);
		//	        }
		//	        if(true){
		//	        	return ;
		//	        }

		FileUtils.saveFloatArray(tFaces1[0].features, tFeatPath);

		float[] tFeat = FileUtils.loadFloatArray(tFeatPath, 2048);

		//	        float tSim = tSeetaFace.CalcSimilarity(tFaces1[0].features, tFaces2[0].features);
		float tSim = tSeetaFace.CalcSimilarity(tFaces2[0].features, tFeat);

		System.out.println("sim="+tSim);
	}

	public static void seetaTest2() {
		String imgPath = "e:\\img\\bnz12.bmp";
		SeetaFace tSeetaFace = new SeetaFace();

		tSeetaFace.initModelPath("D:\\test");

		try {
			BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
			CMSeetaFace tfaces[] = tSeetaFace.DetectFacesImage(image);
			System.out.println("face_num="+tfaces.length);
			image = null;

		}catch(Exception e){

		}
	}

	public static void seetaTest() {
		SeetaFace tSeetaFace = new SeetaFace();

		tSeetaFace.initModelPath("D:\\test");

		String imgPath = "F:\\img\\4.bmp";
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
			System.out.println("image size="+ image.getWidth()+", "+image.getHeight());

			byte[] tBmpData = ((DataBufferByte) image.getData().getDataBuffer()).getData();
			int tWidth = image.getWidth();
			int tHeight = image.getHeight();
			int tCh = tBmpData.length / (tWidth*tHeight);

			CMSeetaFace[] tFaces = tSeetaFace.DetectFacesByte(tBmpData, tWidth, tHeight);
			if(null != tFaces){
				System.out.println("DetectFacesByte facenum=" + tFaces.length);

				//System.out.println(" tFaces[0].bottom"+   		tFaces[0].bottom);

			}else{
				System.out.println("DetectFacesByte no face");
			}
		} catch (FileNotFoundException e) {
			System.out.println("e="+e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("e="+e.toString());
			e.printStackTrace();
		}
	}
	    
}
