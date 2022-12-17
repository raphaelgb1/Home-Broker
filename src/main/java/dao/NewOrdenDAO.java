package dao;

public class NewOrdenDAO{
    int IDATIVO;
    int IDCONTA;
    int TIPOORDEN;
    double QUANT;
    double QUANTEXE;
    double VALOR;
    String DATAORDEN;
    public NewOrdenDAO(int iDCONTA) {
        IDCONTA = iDCONTA;
    }
    public int getIDATIVO() {
        return IDATIVO;
    }
    public void setIDATIVO(int iDATIVO) {
        IDATIVO = iDATIVO;
    }
    public int getIDCONTA() {
        return IDCONTA;
    }
    public void setIDCONTA(int iDCONTA) {
        IDCONTA = iDCONTA;
    }
    public int getTIPOORDEN() {
        return TIPOORDEN;
    }
    public void setTIPOORDEN(int tIPOORDEN) {
        TIPOORDEN = tIPOORDEN;
    }
    public double getQUANT() {
        return QUANT;
    }
    public void setQUANT(double qUANT) {
        QUANT = qUANT;
    }
    public double getQUANTEXE() {
        return QUANTEXE;
    }
    public void setQUANTEXE(double qUANTEXE) {
        QUANTEXE = qUANTEXE;
    }
    public double getVALOR() {
        return VALOR;
    }
    public void setVALOR(double vALOR) {
        VALOR = vALOR;
    }
    public String getDATAORDEN() {
        return DATAORDEN;
    }
    public void setDATAORDEN(String dATAORDEN) {
        DATAORDEN = dATAORDEN;
    }
}
