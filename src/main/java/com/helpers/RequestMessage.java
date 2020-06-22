/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

/**
 *
 * @author IETECHADMIN
 */
public class RequestMessage {
    private String reqTransID;
    private String accountNo;
    private Integer amount;
    private String bankCode;
    private Integer channel;
    private String tranCode;
    private String narration;
    private String atm_Location;
    private String issuerBIN;
    private String pan;
    private String issuerfiid;
    private String acqfiid;
    private String stan;

    /**
     * @return the reqTransID
     */
    public String getReqTransID() {
        return reqTransID;
    }

    /**
     * @param reqTransID the reqTransID to set
     */
    public void setReqTransID(String reqTransID) {
        this.reqTransID = reqTransID;
    }

    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the channel
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * @return the tranCode
     */
    public String getTranCode() {
        return tranCode;
    }

    /**
     * @param tranCode the tranCode to set
     */
    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the atm_Location
     */
    public String getAtm_Location() {
        return atm_Location;
    }

    /**
     * @param atm_Location the atm_Location to set
     */
    public void setAtm_Location(String atm_Location) {
        this.atm_Location = atm_Location;
    }

    /**
     * @return the issuerBIN
     */
    public String getIssuerBIN() {
        return issuerBIN;
    }

    /**
     * @param issuerBIN the issuerBIN to set
     */
    public void setIssuerBIN(String issuerBIN) {
        this.issuerBIN = issuerBIN;
    }

    /**
     * @return the pan
     */
    public String getPan() {
        return pan;
    }

    /**
     * @param pan the pan to set
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * @return the issuerfiid
     */
    public String getIssuerfiid() {
        return issuerfiid;
    }

    /**
     * @param issuerfiid the issuerfiid to set
     */
    public void setIssuerfiid(String issuerfiid) {
        this.issuerfiid = issuerfiid;
    }

    /**
     * @return the acqfiid
     */
    public String getAcqfiid() {
        return acqfiid;
    }

    /**
     * @param acqfiid the acqfiid to set
     */
    public void setAcqfiid(String acqfiid) {
        this.acqfiid = acqfiid;
    }

    /**
     * @return the stan
     */
    public String getStan() {
        return stan;
    }

    /**
     * @param stan the stan to set
     */
    public void setStan(String stan) {
        this.stan = stan;
    }
}
