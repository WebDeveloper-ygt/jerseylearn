package com.learn.jersey.exceptions;

public class CustomException extends  Exception{
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
    private String description;
    private String link;

    public CustomException() {

    }

    public CustomException(int status, String message, String description, String link) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
    
}
