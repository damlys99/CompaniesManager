package com.example.uniprogramming.models;

import javax.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String content;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "note_company",
            joinColumns = @JoinColumn(
                    name = "note_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"))
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "note_user",
            joinColumns = @JoinColumn(
                    name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private User user;

    public Note(){}
    public Note(String content){
        this.content = content;
        this.isDeleted = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
