package com.dsm.pick.domains.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher {

    @Id
    private String id;

    private String pw;

    private String name;

    @Column(name = "token")
    private String refreshToken;

    public Teacher() {}
    public Teacher(String id, String pw, String name, String refreshToken) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.refreshToken = refreshToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
