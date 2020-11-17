package com.dsm.pick.domains.domain;

import com.dsm.pick.utils.exception.NonExistIdOrPasswordException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "name")
    private String name;

    @Column(name = "office")
    private String office;

    public Teacher() {}
    public Teacher(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }
    public Teacher(String id, String pw, String name, String office) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.office = office;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void existIdOrPassword() {
        if(this.id == null)
            throw new NonExistIdOrPasswordException();
        else if(this.pw == null)
            throw new NonExistIdOrPasswordException();
    }
}
