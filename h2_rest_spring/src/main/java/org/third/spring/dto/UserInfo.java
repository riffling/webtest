package org.third.spring.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.third.spring.utils.DateUtil;
import org.third.spring.utils.ListUtil;

@XmlRootElement(name = "user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5461373449802431627L;
    private String userName;
    private BigDecimal salary;
    private Date birthday;
    private boolean isVip;
    private int id;
    private ListBean hobbies;

    public UserInfo() {
            this.id = 0;
        }

    public UserInfo(int id2) {
        this.id = id2;
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @XmlElement
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @XmlElement
    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public ListBean getHobbies() {
        return hobbies;
    }

    public void setHobbies(ListBean hobbies) {
        this.hobbies = hobbies;
    }

    public String toString() {
        return "id:" + this.id + ",userName:" + this.userName + ",isVip=" + this.isVip + ",birthday="
                + DateUtil.formatDate(this.birthday) + ",hobbies:" + ListUtil.getString(this.hobbies.getList());
    }

}
