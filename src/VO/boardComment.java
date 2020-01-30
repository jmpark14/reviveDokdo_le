package VO;

public class boardComment {
    private String commentBrdno = null;
    private String commentUid = null;
    private String commentContents = null;
    private String commentRegDate = null;
    private int cmtCount;
    private int commentNo = 0;

    public boardComment() {

    }

    @Override
    public String toString() {
        return "boardComment{" +
                "commentBrdno='" + commentBrdno + '\'' +
                ", commentUid='" + commentUid + '\'' +
                ", commentContents='" + commentContents + '\'' +
                ", commentRegDate='" + commentRegDate + '\'' +
                '}';
    }

    public String getCommentBrdno() {
        return commentBrdno;
    }

    public void setCommentBrdno(String commentBrdno) {
        this.commentBrdno = commentBrdno;
    }

    public String getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(String commentUid) {
        this.commentUid = commentUid;
    }

    public String getCommentContents() {
        return commentContents;
    }

    public void setCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

    public String getCommentRegDate() {
        return commentRegDate;
    }

    public void setCommentRegDate(String commentRegDate) {
        this.commentRegDate = commentRegDate;
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }
}
