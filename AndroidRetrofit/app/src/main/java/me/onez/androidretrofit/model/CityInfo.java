package me.onez.androidretrofit.model;

/**
 * Created by Vincent on 15/12/16.
 */
public class CityInfo {
  private int errNum;
  private String retMsg;

  private RetData retData;

  public int getErrNum() {
    return errNum;
  }

  public void setErrNum(int errNum) {
    this.errNum = errNum;
  }

  public String getRetMsg() {
    return retMsg;
  }

  public void setRetMsg(String retMsg) {
    this.retMsg = retMsg;
  }

  public RetData getRetData() {
    return retData;
  }

  public void setRetData(RetData retData) {
    this.retData = retData;
  }
}
