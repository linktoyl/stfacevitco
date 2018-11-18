/**
 * 采用中科院山世光开源的SeetaFaceEngine实现android上的人脸检测与对齐、识别
 * 遵照BSD license
 * 广州炒米信息科技有限公司
 * www.cume.cc
 * 吴祖玉
 * wuzuyu365@163.com
 * 2016.11.9
 *
 */

package cn.vitco.seetaface;

import java.awt.image.BufferedImage;
import java.io.File;

public class XUtils {

  // 照片文件是否存在，注意，与数据库是否存在是不同的
  public static Boolean fileExists(String vPath) {
    if (null == vPath || "".equals(vPath)) {
      return false;
    }

    try {
      File file = new File(vPath);
      if (!file.exists()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }
 

  public static void LogOut(String vTag, String vStr){
	  System.out.println(vTag+", "+vStr);
  }

//  public static Boolean saveByteToFile(byte[] vData, String vFilePath) {
//	  LogOut("saveByteToFile", "1");
//
//    if (null == vData || isEmptyStr(vFilePath)) {
//      LogOut("saveByteToFile", "vData or filepath is error");
//      return false;
//    }
//
//    LogOut("saveByteToFile", "vData.len=" + vData.length);
//    LogOut("saveByteToFile", "vFilePath=" + vFilePath);
//
//    File tFile = new File(vFilePath);
//    if (null == tFile) {
//      LogOut("saveByteToFile", "new file failed ");
//      return false;
//    }
//
//    try {
//      FileOutputStream fos = new FileOutputStream(tFile);
//      try {
//        fos.write(vData);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    } catch (Exception e) {
//
//    }
//    LogOut("saveByteToFile", "success");
//
//    return true;
//  }
//
//  public static byte[] loadByteFromFile(String vFilePath, int vLen) {
//    LogOut("loadByteFromFile", "1");
//
//    if (isEmptyStr(vFilePath) || vLen < 1) {
//      return null;
//    }
//
//    File tFile = new File(vFilePath);
//    if (null == tFile) {
//      LogOut("loadByteFromFile", "new file failed ");
//      return null;
//    }
//
//    byte tByte[] = new byte[vLen];
//    FileInputStream fin = null;
//
//    try {
//      fin = new FileInputStream(tFile);
//      int r = fin.read(tByte);
//      if (r != vLen) {
//        throw new IOException("Can't read all, " + r + " != " + vLen);
//      }
//
//    } catch (Exception e) {
//
//    }
//
//    return tByte;
//  }
 
  // 整数数组转字符串
  public static String intArrayToString(int[] vArr) {
    String tRetString = "";
    if (null == vArr || vArr.length < 1) {
      return tRetString;
    }
    int num = vArr.length;
    for (int i = 0; i < num; i++) {
      tRetString += vArr[i] + ",";
    }

    if (!isEmptyStr(tRetString)) {
      tRetString = tRetString.substring(0, tRetString.length() - 1);
    }
    return tRetString;
  }

  /**
   * 根据图像完整路径判断是否图像文件
   * 
   * @param fName
   * @return
   */
  public static boolean isImageFile(String vPath) {
    if (null == vPath || vPath.trim().length() < 1) {
      return false;
    }

    String tPath = vPath.trim();

    // 检查后缀名
    String end = tPath.substring(tPath.lastIndexOf(".") + 1, tPath.length()).toLowerCase();

    if ("".equals(end.trim())) {
      return false;
    }

    // 依据文件扩展名判断是否为图像文件
    if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")
        || end.equals("mov")) {
    } else {
      return false;
    }

    // 判断文件是否存在
    File file = new File(tPath);
    if (!file.exists()) {
      return false;
    }

    return true;
  }

  public static Boolean isEmptyStr(String vStr){
	  return (vStr == null || vStr.length() <= 0);  
  }
  
  

  // 把逗号、分号等符号分隔的字符串转换为double数组
  public static String[] stringStringValueSplit(String vStr) {
    //LogOut("stringStringValueSplit", "1");
    if (isEmptyStr(vStr)) {
      return null;
    }

    LogOut("stringStringValueSplit", vStr);

    String oldString = vStr;
    vStr = vStr.replaceAll("\\r", "");
    vStr = vStr.replaceAll("\\n", "");
    vStr = vStr.replaceAll("\\[", "");
    vStr = vStr.replaceAll("\\]", "");
    vStr = vStr.replaceAll(";", ",");
    vStr = vStr.replaceAll(" ", "");

    LogOut("stringStringValueSplit", "str after replace =" + vStr + ", old=" + oldString);

    return vStr.split(",|;|\\[|\\]");
  }

  // 把逗号、分号等符号分隔的字符串转换为double数组
  public static int[] stringIntValueSplit(String vStr) {
    LogOut("stringIntValueSplit", "1");
    if (isEmptyStr(vStr)) {
      return null;
    }

    LogOut("stringIntValueSplit", vStr);

    String oldString = vStr;
    vStr = vStr.replaceAll("\\r", "");
    vStr = vStr.replaceAll("\\n", "");
    vStr = vStr.replaceAll("\\[", "");
    vStr = vStr.replaceAll("\\]", "");
    vStr = vStr.replaceAll(";", ",");
    vStr = vStr.replaceAll(" ", "");

    LogOut("stringIntValueSplit", "str after replace =" + vStr + ", old=" + oldString);

    String[] pStr = vStr.split(",|;|\\[|\\]");

    if (null == pStr || 0 == pStr.length) {
      LogOut("stringIntValueSplit", "pStr.length=0");
      return null;
    }
    int[] dRet = new int[pStr.length];

    LogOut("stringIntValueSplit", "pStr.length=" + pStr.length);

    for (int i = 0; i < pStr.length; i++) {
      String tString = pStr[i];
      if (null == tString || tString.trim().equals("")) {
        LogOut("stringIntValueSplit", "i=" + i + ", null, or '' ");
        continue;
      }

      try {
        dRet[i] = Integer.parseInt(tString);
      } catch (NumberFormatException ex) {
        // System.out.println("The String does not contain a parsable integer");
        LogOut("stringIntValueSplit", "NumberFormatException," + ex.toString());
      }
    }

    return dRet;

  }
 

  /**
   * 
   * 方法名: </br>
   * 详述:取识别人脸截图 </br>
   * 开发人员：王太顺</br>
   * 创建时间：2015年12月10日</br>
   * 
   * @param fileName
   * @param dstWidth
   * @return
   */
  
  /*
  public static Bitmap getScaledBitmap(String vPath, int vWidth, int vHeight) {
    LogOut("getThumbImg", "1==================================");
    if (null == vPath) {
      LogOut("getThumbImg", "路径为null");
      return null;
    }

    if (vPath.trim().equals("")) {
      LogOut("getThumbImg", "路径为空");
      return null;
    }

    LogOut("getThumbImg", "path=" + vPath + ", 期望：width=" + vWidth + ",vheight=" + vHeight);

    File file = new File(vPath);
    // 如果不存在了，直接返回
    if (!file.exists()) {
      LogOut("getThumbImg", "文件不存在：path=" + vPath);
      return null;
    }

    // 先获取图片的宽和高
    Options options = new Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(vPath, options);
    if (options.outWidth <= 0 || options.outHeight <= 0) {
      LogOut("getThumbImg", "解析图片失败");
      return null;
    }
    LogOut("getThumbImg", "wid1:" + options.outWidth + ",height:" + options.outHeight + ",path=" + vPath);
    int height0 = options.outHeight;

    // 压缩图片，注意inSampleSize只能是2的整数次幂，如果不是的，话，向下取最近的2的整数次幂，例如3实际上会是2，7实际上会是4
    options.inSampleSize = calculateInSampleSize(options, vWidth, vHeight);

    LogOut("getThumbImg", "options.inSampleSize=" + options.inSampleSize);

    // 不能用Config.RGB_565

    options.inJustDecodeBounds = false;
    Bitmap thumbImgNow = null;
    try {
      thumbImgNow = BitmapFactory.decodeFile(vPath, options);
    } catch (OutOfMemoryError e) {
      LogOut("getThumbImg", "OutOfMemoryError, decodeFile失败 XXXXXXXXXXXXXXXXXXXXXXXXXXX ");
      return null;
    }
    
    //Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter)
    
    

    if (null == thumbImgNow) {
      LogOut("getThumbImg", "decodeFile失败 XXXXXXXXXXXXXXXXXXXXXXXXXXX ");
      return null;
    }

    int wid = thumbImgNow.getWidth();
    int hgt = thumbImgNow.getHeight();
    LogOut("getThumbImg", "1, wid=" + wid + ",hgt=" + hgt);

    int degree = readPictureDegree(vPath);
    if (degree != 0) {
      LogOut("getThumbImg", "degree=" + degree);

      // 把图片旋转为正的方向
      thumbImgNow = rotateImage(degree, thumbImgNow);
    }
    return thumbImgNow;
  }
  
 */
  
//获取图片的缩略图,宽度和高度中较小的缩放到vMinWidth. 确保宽度和高度最小的都能覆盖到，
 	//比如图片是3000*2000，要缩放到 150*100, 那么vMinWidth=100;	
	public static BufferedImage getScaledBitmap(String vPath, int vMinWidth) {	
		String tag = "getScaledBitmap";
//		LogOut(tag, "1==");
//		if(null == vPath){
//			LogOut(tag, "路径为null");
//			return null; 
//		}
//		
//		if(vPath.trim().equals("")){
//			LogOut(tag, "路径为空");
//			return null; 
//		}
//		
//		LogOut(tag, "path="+vPath+", 期望：vMinWidth="+vMinWidth);  
//		
//		File file = new File(vPath);
//		//如果不存在了，直接返回
//		if(!file.exists()){
//			LogOut(tag, "文件不存在：path="+vPath);
//			return null; 
//		}
//		 		
//		// 先获取图片的宽和高
//		Options options = new Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeFile(vPath, options);
//		if (options.outWidth <= 0 || options.outHeight <= 0) {
//			LogOut(tag, "解析图片失败");
//			return null;
//		}
//		LogOut(tag, "原图大小：width:" + options.outWidth + ",height:"
//				+ options.outHeight + ",path=" + vPath);
//		int height0 = options.outHeight;
//
//		int tMinWidth = Math.min(options.outWidth, options.outHeight);
//				
//		// 压缩图片，注意inSampleSize只能是2的整数次幂，如果不是的，话，向下取最近的2的整数次幂，例如3实际上会是2，7实际上会是4
//		options.inSampleSize = Math.max(1, tMinWidth/vMinWidth);   
//		//LogOut(tag, "options.inSampleSize="+options.inSampleSize);
//			 
//		//不能用Config.RGB_565
//		//options.inPreferredConfig = Config.RGB_565;  
//		options.inDither = false; 
//		options.inPurgeable = true; 
//		options.inInputShareable = true; 
//		options.inJustDecodeBounds = false;
//		Bitmap thumbImgNow = null; 
//		try{
//			 thumbImgNow = BitmapFactory.decodeFile(vPath, options);
//		}catch(OutOfMemoryError e){
//			LogOut(tag, "OutOfMemoryError, decodeFile失败   ");
//			return null; 
//		}
//		 
//		//LogOut(tag, "thumbImgNow.size="+thumbImgNow.getWidth()+","+thumbImgNow.getHeight()); 
//		
//		if(null == thumbImgNow){
//			LogOut(tag, "decodeFile失败   ");
//			return null;
//		}
//		
//		int wid = thumbImgNow.getWidth();
//		int hgt = thumbImgNow.getHeight();
//		
//		int degree = readPictureDegree(vPath);
//		if (degree != 0) {
//			//LogOut(tag, "degree="+degree);			
//			// 把图片旋转为正的方向 
//			thumbImgNow = rotateImage(degree, thumbImgNow);
//		}
//				 
//		 wid = thumbImgNow.getWidth();
//		 hgt = thumbImgNow.getHeight();
//				
//		tMinWidth = Math.min(wid, hgt);
//		if (tMinWidth > vMinWidth) {
//			//如果原图片最小宽度比预期最小高度大才进行缩小
//			float ratio = ((float) vMinWidth) / tMinWidth; 
//			Matrix matrix = new Matrix();    
//			matrix.postScale(ratio, ratio);    
//			thumbImgNow  = Bitmap.createBitmap(thumbImgNow, 0, 0, wid, hgt, matrix, true);   
//		}
		 
	  //  LogOut(tag, "处理后, size, width="+thumbImgNow.getWidth()+",height="+thumbImgNow.getHeight());
	
		return null;
	}

}




