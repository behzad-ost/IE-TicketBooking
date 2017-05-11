package service.reserveManagement;

/**
 * Created by ali on 5/2/17.
 */
public class ReserveResult {
    private int adultSum;
    private int infantSum;
    private int childSum;
    private int totalSum;
    private int adultFee;
    private int infantFee;
    private int childFee;
    private boolean success;
    private String errorMessage;

    public ReserveResult() {
        this.success = true;
        this.errorMessage = "";
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public int getAdultSum() {
        return adultSum;
    }

    public void setAdultSum(int adultSum) {
        this.adultSum = adultSum;
    }

    public int getInfantSum() {
        return infantSum;
    }

    public void setInfantSum(int infantSum) {
        this.infantSum = infantSum;
    }

    public int getChildSum() {
        return childSum;
    }

    public void setChildSum(int childSum) {
        this.childSum = childSum;
    }

    public int getAdultFee() {
        return adultFee;
    }

    public void setAdultFee(int adultFee) {
        this.adultFee = adultFee;
    }

    public int getInfantFee() {
        return infantFee;
    }

    public void setInfantFee(int infantFee) {
        this.infantFee = infantFee;
    }

    public int getChildFee() {
        return childFee;
    }

    public void setChildFee(int childFee) {
        this.childFee = childFee;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
