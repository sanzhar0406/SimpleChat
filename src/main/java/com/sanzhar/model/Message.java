/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.model;

/**
 *
 * @author alyce
 */
public class Message {

    private String content;
    private String senderName;
    
    public Message(){
        this.content = "Empty";
        this.senderName = "Unknown";
    }
    
    public Message (String content, String senderName){
        this.content = content;
        this.senderName = senderName;
    }
    
    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }
}
