package com.example.napo01;

// 자격증 정보 VO
public class CareerCertVO {

    private String certName;
    private String certInst;
    private String certDate;

    public CareerCertVO(String certName, String certInst, String certDate) {
        this.certName = certName;
        this.certInst = certInst;
        this.certDate = certDate;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertInst() {
        return certInst;
    }

    public void setCertInst(String certInst) {
        this.certInst = certInst;
    }

    public String getCertDate() {
        return certDate;
    }

    public void setCertDate(String certDate) {
        this.certDate = certDate;
    }
}
