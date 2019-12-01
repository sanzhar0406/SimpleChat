/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.model;

/**
 *
 * @author Sanzhar
 */
public interface Player {
    
    void send(String content);

    Message receive() throws InterruptedException;
    
}
