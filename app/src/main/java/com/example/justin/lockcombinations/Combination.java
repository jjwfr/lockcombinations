// JUSTIN WEI, 91618787

package com.example.justin.lockcombinations;

import java.io.Serializable;
import java.util.Date;

public class Combination implements Serializable {

    private int no1;
    private int no2;
    private int no3;
    private int id;

    private Date created;

    public Combination(int no1, int no2, int no3, Date created, int id){

        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
        this.created = created;
        this.id = id;
    }

    public Combination(){

    }

    public int getId() {
        return id;
    }

    public int getNo1() {
        return no1;
    }

    public int getNo2() {
        return no2;
    }

    public int getNo3() {
        return no3;
    }

    public Date getCreated() {
        return created;
    }

    public boolean hasNo(int no){

        if(no1 == no || no2 == no || no3 == no || no == -1)
            return true;
        else return false;

    }

}
