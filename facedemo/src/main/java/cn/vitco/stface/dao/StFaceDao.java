package cn.vitco.stface.dao;

import cn.vitco.stface.entity.FaceFeat;
import cn.vitco.stface.entity.IdentityInfo;

import java.util.List;

public interface StFaceDao {

    public boolean regFace(FaceFeat faceFeat);

    public List<FaceFeat> queryAllFaceBase();

    public IdentityInfo queryIdentByID(String id_card_num);
}
