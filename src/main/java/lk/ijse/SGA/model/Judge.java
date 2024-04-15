package lk.ijse.SGA.model;

public class Judge {
    private String judgeId;
    private String name;
    private String courtId;
    private int yrsOfExp;

    public Judge() {
    }

    public Judge(String judgeId, String name, String courtId, int yrsOfExp) {
        this.judgeId = judgeId;
        this.name = name;
        this.courtId = courtId;
        this.yrsOfExp = yrsOfExp;
    }

    public String getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(String judgeId) {
        this.judgeId = judgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public int getYrsOfExp() {
        return yrsOfExp;
    }

    public void setYrsOfExp(int yrsOfExp) {
        this.yrsOfExp = yrsOfExp;
    }

    @Override
    public String toString() {
        return "Judge{" +
                "judgeId='" + judgeId + '\'' +
                ", name='" + name + '\'' +
                ", courtId='" + courtId + '\'' +
                ", yrsOfExp=" + yrsOfExp +
                '}';
    }
}

