package cn.vitco.stface;

import cn.vitco.stface.dao.StFaceDao;
import cn.vitco.stface.entity.FaceFeat;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages="cn.vitco.stface.dao")
public class DemoApplication implements ApplicationRunner {

    public static List<FaceFeat> faceFeats = null;

    public static final double SIM_TARGET = 0.7;

    @Autowired
    private StFaceDao stFaceDao;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //加载人脸库
        faceFeats = stFaceDao.queryAllFaceBase();
        System.out.println("-------------->" + "项目启动，now=" + new Date());
        System.out.println("-------------->" + "加载人脸数量:" + faceFeats==null?0:faceFeats.size());

    }
}
