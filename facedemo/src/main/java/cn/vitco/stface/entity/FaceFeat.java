package cn.vitco.stface.entity;

public class FaceFeat {

    private String id_card_num;
    private byte[] face_feat;

    public String getId_card_num() {
        return id_card_num;
    }

    public void setId_card_num(String id_card_num) {
        this.id_card_num = id_card_num;
    }

    public byte[] getFace_feat() {
        return face_feat;
    }

    public void setFace_feat(byte[] face_feat) {
        this.face_feat = face_feat;
    }
}
