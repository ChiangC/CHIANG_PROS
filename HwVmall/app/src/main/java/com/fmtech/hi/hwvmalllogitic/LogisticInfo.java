package com.fmtech.hi.hwvmalllogitic;

/**
 * ==================================================================
 * Copyright (C) 2016 fmtech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/6/26 16:55
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class LogisticInfo {

    private String logisticDate;
    private String logisticTime;
    private String logisticDetail;

    public LogisticInfo(String logisticDate, String logisticTime, String logisticDetail) {
        this.logisticDate = logisticDate;
        this.logisticTime = logisticTime;
        this.logisticDetail = logisticDetail;
    }

    public String getLogisticDate() {
        return logisticDate;
    }

    public void setLogisticDate(String logisticDate) {
        this.logisticDate = logisticDate;
    }

    public String getLogisticTime() {
        return logisticTime;
    }

    public void setLogisticTime(String logisticTime) {
        this.logisticTime = logisticTime;
    }

    public String getLogisticDetail() {
        return logisticDetail;
    }

    public void setLogisticDetail(String logisticDetail) {
        this.logisticDetail = logisticDetail;
    }
}
