package com.bloodbank.bookexchange.Model;

public class Post {

    private String aId;
    private String rescueBy;
    private String description;
    private String image;

    public Post() {
    }

    public Post(String aId, String rescueBy, String description, String image) {
        this.aId = aId;
        this.rescueBy = rescueBy;
        this.description = description;
        this.image = image;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getRescueBy() {
        return rescueBy;
    }

    public void setRescueBy(String rescueBy) {
        this.rescueBy = rescueBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
