package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class LzAccount {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.accountid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private Integer accountid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.Dealid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private Integer dealid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.accountType
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private String accounttype;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.Amount
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private BigDecimal amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.accounttime
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private Date accounttime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.accountphone
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private String accountphone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lz_account.Status
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    private String status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.accountid
     *
     * @return the value of lz_account.accountid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public Integer getAccountid() {
        return accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.accountid
     *
     * @param accountid the value for lz_account.accountid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.Dealid
     *
     * @return the value of lz_account.Dealid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public Integer getDealid() {
        return dealid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.Dealid
     *
     * @param dealid the value for lz_account.Dealid
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setDealid(Integer dealid) {
        this.dealid = dealid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.accountType
     *
     * @return the value of lz_account.accountType
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public String getAccounttype() {
        return accounttype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.accountType
     *
     * @param accounttype the value for lz_account.accountType
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.Amount
     *
     * @return the value of lz_account.Amount
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.Amount
     *
     * @param amount the value for lz_account.Amount
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.accounttime
     *
     * @return the value of lz_account.accounttime
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public Date getAccounttime() {
        return accounttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.accounttime
     *
     * @param accounttime the value for lz_account.accounttime
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setAccounttime(Date accounttime) {
        this.accounttime = accounttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.accountphone
     *
     * @return the value of lz_account.accountphone
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public String getAccountphone() {
        return accountphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.accountphone
     *
     * @param accountphone the value for lz_account.accountphone
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setAccountphone(String accountphone) {
        this.accountphone = accountphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lz_account.Status
     *
     * @return the value of lz_account.Status
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lz_account.Status
     *
     * @param status the value for lz_account.Status
     *
     * @mbg.generated Mon Jun 24 11:44:52 CST 2019
     */
    public void setStatus(String status) {
        this.status = status;
    }
}