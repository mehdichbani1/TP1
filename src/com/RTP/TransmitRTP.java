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


import java.io.IOException;
import java.net.MalformedURLException;
import javax.media.CannotRealizeException;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

public class TransmitRTP extends Thread{
    
    //media formats needed to build RTP stream
    static final Format[] FORMATS = new Format[] {new AudioFormat(AudioFormat.ULAW_RTP)}; //Ulaw_RTP
    static final ContentDescriptor CONTENT_DESCRIPTOR =new ContentDescriptor(ContentDescriptor.RAW_RTP);
    DataSink dataSink;
    private String remoteIP;    
    public TransmitRTP(String remoteIP) {
        this.remoteIP = remoteIP;
        
    }
    
    public void begin() throws MalformedURLException, IOException, NoDataSourceException, NoProcessorException, CannotRealizeException, NoDataSinkException {
        MediaLocator locator = new MediaLocator("javasound://0");
        DataSource source = Manager.createDataSource(locator);
        Processor mediaProcessor = Manager.createRealizedProcessor( new ProcessorModel(source, FORMATS, CONTENT_DESCRIPTOR));
        MediaLocator outputMediaLocator = new MediaLocator("rtp://"+remoteIP+":10000/audio");
        dataSink = Manager.createDataSink(mediaProcessor.getDataOutput(),outputMediaLocator);
        mediaProcessor.start(); 
        dataSink.open();
        dataSink.start();
        System.out.println("Transmiting...");
    }


    public void run() {
            try {
                begin();
            } catch (Exception ex) {
                System.out.println(ex);
            }
    }

    @Override
    public void interrupt() {
        // TODO Auto-generated method stub
        super.interrupt();
        dataSink.close();
        try {
            dataSink.stop();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    
    
}