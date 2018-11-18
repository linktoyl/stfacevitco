package cn.vitco.stface.web.controller;

import cn.vitco.seetaface.CMSeetaFace;
import cn.vitco.seetaface.FileUtils;
import cn.vitco.seetaface.SeetaFace;
import cn.vitco.stface.DemoApplication;
import cn.vitco.stface.ErrorCode;
import cn.vitco.stface.common.ByteUtiles;
import cn.vitco.stface.dao.StFaceDao;
import cn.vitco.stface.entity.FaceFeat;
import cn.vitco.stface.entity.IdentityInfo;
import cn.vitco.stface.entity.RespObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FaceApiController {
    private static final Logger log = LoggerFactory.getLogger(FaceApiController.class);

    private static final SeetaFace tSeetaFace = new SeetaFace();

    @Autowired
    private StFaceDao stFaceDao;

    static {

        tSeetaFace.initModelPath("D:/test");
    }

    /**
     * @Description: 注册人脸信息
     * @Author: lin.yang
     * @CreateDate: 2018/11/17 20:34
     * @UpdateUser: lin.yang
     * @UpdateDate: 2018/11/17 20:34
     * @UpdateRemark:
     * @Version: 1.0
     */

    @RequestMapping("/facereg")
    @ResponseBody
    public Object regsiter(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("imgs") MultipartFile imgs) {
        if (!imgs.isEmpty()) {
            try {
                long start = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(imgs.getInputStream());
                CMSeetaFace[] tFaces = tSeetaFace.DetectFacesImage(image);
                long end = System.currentTimeMillis();
                log.debug("执行耗时:" + (end - start) + "ms");
                if (null != tFaces) {
                    if (tFaces.length == 1) {
                        String tFeatPath = "e:\\facedata\\yanglin.txt";

                        /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(bos);
                        bos.write(2.5);*/

                        byte[] feat_data = ByteUtiles.floatArrToBytes(tFaces[0].features);
                        FileUtils.saveFloatArray(tFaces[0].features, tFeatPath);
                        FaceFeat ffeat = new FaceFeat();
                        ffeat.setId_card_num("500101198810222016");
                        ffeat.setFace_feat(feat_data);

                        stFaceDao.regFace(ffeat);

                        DemoApplication.faceFeats.add(ffeat);

                        return new RespObj(0, "注册成功！", tFaces);
                    } else {
                        return new RespObj(ErrorCode.Warn_Person_Num, "注册所采集的图片中出现多个人脸！");
                    }

                } else {
                    return new RespObj(ErrorCode.Warn_Person_Num, "注册所采集的图片中没有人脸");
                }

            } catch (FileNotFoundException e) {
                return new RespObj(ErrorCode.Error_Img_Upload_Code, "注册所采集的图片上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return new RespObj(ErrorCode.Error_Img_Upload_Code, "注册所采集的图片上传失败," + e.getMessage());
            }

        } else {
            return new RespObj(ErrorCode.Error_Img_Upload_Code, "注册所采集的图片接受为空！");
        }

    }

    @RequestMapping("/faceident")
    @ResponseBody
    public Object identify(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("imgs") MultipartFile imgs) {
        if (!imgs.isEmpty()) {
            try {
                List<IdentityInfo> result = new ArrayList<IdentityInfo>();
                long start = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(imgs.getInputStream());
                CMSeetaFace[] tFaces = tSeetaFace.DetectFacesImage(image);
                long end = System.currentTimeMillis();
                log.debug("执行耗时:" + (end - start) + "ms");
                if (null != tFaces) {
                    if (tFaces.length == 1) {
                        for (FaceFeat ff: DemoApplication.faceFeats) {
                            float[] srcFeat = ByteUtiles.bytesToFloatArr(ff.getFace_feat(), 2048);
                            float tSim = tSeetaFace.CalcSimilarity(tFaces[0].features, srcFeat);
                            log.info("比对结果:"+tSim);

                            if(tSim>DemoApplication.SIM_TARGET) {
                                IdentityInfo identityInfo = stFaceDao.queryIdentByID(ff.getId_card_num());
                                result.add(identityInfo);
                            }
                        }
                        return new RespObj(0, "比对成功！", result);
                    } else {
                        return new RespObj(ErrorCode.Warn_Person_Num, "比对所采集的图片中出现多个人脸！");
                    }

                } else {
                    return new RespObj(ErrorCode.Warn_Person_Num, "比对所采集的图片中没有人脸");
                }

            } catch (FileNotFoundException e) {
                return new RespObj(ErrorCode.Error_Img_Upload_Code, "比对所采集的图片上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return new RespObj(ErrorCode.Error_Img_Upload_Code, "比对所采集的图片上传失败," + e.getMessage());
            }
        } else {
            return new RespObj(ErrorCode.Error_Img_Upload_Code, "比对所采集的图片接受为空！");
        }

    }
}
