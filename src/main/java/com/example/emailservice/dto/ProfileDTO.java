package com.example.emailservice.dto;

public class ProfileDTO {

    private String username;
    private Integer nbTestControle;
    private Long nbTestControlePerfect;
    private Long nbTestControleFailed;
    private Long nbTestControlePassed;


    public ProfileDTO() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNbTestControle() {
        return nbTestControle;
    }
    
    public void setNbTestControle(Integer nbTestControle) {
        this.nbTestControle = nbTestControle;
    }

    public Long getNbTestControlePerfect() {
        return nbTestControlePerfect;
    }
    
    public void setNbTestControlePerfect(Long nbTestControlePerfect) {
        this.nbTestControlePerfect = nbTestControlePerfect;
    }

    public Long getNbTestControleFailed() {
        return nbTestControleFailed;
    }
    
    public void setNbTestControleFailed(Long nbTestControleFailed) {
        this.nbTestControleFailed = nbTestControleFailed;
    }

    public Long getNbTestControlePassed() {
        return nbTestControlePassed;
    }
    
    public void setNbTestControlePassed(Long nbTestControlePassed) {
        this.nbTestControlePassed = nbTestControlePassed;
    }
    
    
}
