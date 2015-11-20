package org.third.spring.basic;

public class ComplexBean {
    private AnotherBean beanOne;
    private YetAnotherBean beanTwo;
    private int i;

    private java.util.Properties people;
    private java.util.List comeList;
    private java.util.Map someMap;
    private java.util.Set someSet;
    public ComplexBean(AnotherBean beanOne, YetAnotherBean beanTwo, int i) {
        super();
        this.beanOne = beanOne;
        this.beanTwo = beanTwo;
        this.i = i;
    }

    public String toString() {
        // TODO Auto-generated method stub
        return "beanOne" + this.beanOne + ":beanTwo" + this.beanTwo + ":int" + this.i;
    }

    public AnotherBean getBeanOne() {
        return beanOne;
    }

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public YetAnotherBean getBeanTwo() {
        return beanTwo;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public java.util.Properties getPeople() {
        return people;
    }

    public void setPeople(java.util.Properties people) {
        this.people = people;
    }

    public java.util.List getComeList() {
        return comeList;
    }

    public void setComeList(java.util.List comeList) {
        this.comeList = comeList;
    }

    public java.util.Map getSomeMap() {
        return someMap;
    }

    public void setSomeMap(java.util.Map someMap) {
        this.someMap = someMap;
    }

    public java.util.Set getSomeSet() {
        return someSet;
    }

    public void setSomeSet(java.util.Set someSet) {
        this.someSet = someSet;
    }
}
