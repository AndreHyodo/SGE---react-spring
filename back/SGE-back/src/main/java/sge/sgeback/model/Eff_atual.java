package sge.sgeback.model;

public class Eff_atual {

    private String testCell;
    private int tempoTotalSec;
    private int EffPercent;

    public Eff_atual(String testCell,int tempoTotalSec, int effPercent) {
        this.testCell = testCell;
        this.tempoTotalSec = tempoTotalSec;
        EffPercent = effPercent;
    }

    public String getTestCell() {
        return testCell;
    }

    public void setTestCell(String testCell) {
        this.testCell = testCell;
    }

    public int getTempoTotalSec() {
        return tempoTotalSec;
    }

    public void setTempoTotalSec(int tempoTotalSec) {
        this.tempoTotalSec = tempoTotalSec;
    }

    public int getEffPercent() {
        return EffPercent;
    }

    public void setEffPercent(int effPercent) {
        EffPercent = effPercent;
    }
}
