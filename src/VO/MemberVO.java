package VO;

public class MemberVO {
    private int mbrno;
    private String userid;
    private String passwd;
    private String name;
    private String statement;
    private String addr1;
    private String addr2;
    private String email;
    private String tel;
    private String regdate;
    //기본생성자
    public MemberVO () {
    }
    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public int getMbrno() {
        return mbrno;
    }
    public void setMbrno(int mbrno) {
        this.mbrno = mbrno;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddr1() {
        return addr1;
    }
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }
    public String getAddr2() {
        return addr2;
    }
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
