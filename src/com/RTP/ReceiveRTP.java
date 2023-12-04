/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RTP;

/**
 *
 * @author slimane
 */

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;


public class ReceiveRTP extends Thread{

    Player player;     


    private String remoteIP;
    public ReceiveRTP(String remoteIP) {
        this.remoteIP = remoteIP;
    }


    @Override
    public void run() {
            try {
            // medialocator to receive data from this url : includes the sender that we want to receive data from
            MediaLocator url = new MediaLocator("rtp://"+remoteIP+":10000/audio");
            
            //creating a player to receive data
            player = Manager.createPlayer(url);
            player.realize();
            player.start();
            
            System.out.println("Receiving...");
                
            } catch (Exception ex) {
                System.out.println(ex);
            }
    }



    @Override
    public void interrupt() {
        player.stop();
        player.close();
    }

}