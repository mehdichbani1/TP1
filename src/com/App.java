package com;

import com.RTP.ReceiveRTP;
import com.RTP.TransmitRTP;

public class App {
    public static void main(String[] args) throws Exception {

        TransmitRTP transmitRTP = new TransmitRTP("192.168.1.221");
        ReceiveRTP receiveRTP = new ReceiveRTP("192.168.1.221");
        
        //on a testé la transmission et la réception en même temps et ça marche dans le réseau de l'emi avec les
        //adresses ip, entre 2 machines différentes.
        transmitRTP.start();
        receiveRTP.start();
    }
}
