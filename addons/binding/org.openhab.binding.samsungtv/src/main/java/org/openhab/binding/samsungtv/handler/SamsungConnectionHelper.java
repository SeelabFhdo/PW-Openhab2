package org.openhab.binding.samsungtv.handler;

import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;

import org.apache.commons.lang.ArrayUtils;
import org.openhab.binding.samsungtv.SamsungTvBindingConstants.KEY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsungConnectionHelper {
	
	 private boolean connected;
	 	
	 	private Logger logger = LoggerFactory.getLogger(SamsungConnectionHelper.class);	    
	    private Socket socket;
	    
	    //Construktor
	    public SamsungConnectionHelper(){
	        this.socket = null;
	        connected = false;
	      
	    }


	    //create socket to TV connection
	     public void connectToTv(String ip, Integer port){
	        if(socket!=null){
	            socket = null;
	        }
	        
	        try {
	            socket = new Socket(ip, port);
	            logger.debug(String.valueOf(socket.isConnected()));
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        
	        if(socket!=null){
	            logger.debug(socket.getInetAddress().toString());
	            connected = true;
	        }
	    }
	     
	    // Methode to print Hexa-Array´s
	    private String getHexString(byte[] b) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < b.length; i++){
	            if (i > 0)
	                sb.append(':');
	            sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
	    }
	    
	   // Authentification from PC to TV
	   public void sendAuth(){

	        byte[] array = {0x00, 0x13, 0x00, 0x69, 0x70, 0x68, 0x6f, 0x6e, 0x65, 0x2e, 0x69, 0x61, 0x70, 0x70, 0x2e, 0x73,
	                0x61, 0x6d, 0x73, 0x75, 0x6e, 0x67, 0x38, 0x00, 0x64, 0x00, 0x10, 0x00, 0x4d, 0x54, 0x6b, 0x79,
	                0x4c, 0x6a, 0x45, 0x32, 0x4f, 0x43, 0x34, 0x30, 0x4d, 0x69, 0x34, 0x32, 0x14, 0x00, 0x63, 0x6d,
	                0x46, 0x75, 0x5a, 0x47, 0x39, 0x74, 0x55, 0x6d, 0x56, 0x74, 0x62, 0x33, 0x52, 0x6c, 0x53, 0x55,
	                0x51, 0x3d, 0x0c, 0x00, 0x62, 0x58, 0x6c, 0x53, 0x5a, 0x57, 0x31, 0x76, 0x64, 0x47, 0x55, 0x3d
	        };
	        
	        //Send Array to TV
	        this.sendArray(array);
	    }

	    public void end(){
	        try {
	            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
	            dOut.close();
	            socket.close();

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	    }
	    
	    public void sendArray(byte [] array){
	    	try {
	            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
	            dOut.write(array);
	            dOut.flush();
	            logger.debug(getHexString(array));
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	    	
	    }


		public boolean isConnected() {
			return connected;
		}	  
				
		public void sendKey(String key){

	       
	        byte [] paketstart = {
	                0x00, 0x13, 0x00, 0x69, 0x70, 0x68, 0x6f, 0x6e, 0x65, 0x2e, 0x69, 0x61, 0x70, 0x70, 0x2e, 0x73,
	                0x61, 0x6d, 0x73, 0x75, 0x6e, 0x67
	        };

	        byte [] payloadstart = {
	                0x00, 0x00, 0x00
	        };

	        byte[]   keycode = Base64.getEncoder().encode((key.toString()).getBytes());
	        // +3 weil 3x 0x00, +2 wegen längenangabe des keycodes
	        Short payloadlength = (short) (keycode.length + 3 + 2);
	        byte[] payloadsize = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(payloadlength).array();
	        byte[] keycodesize = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) keycode.length).array();
	        //StartHex
	        
	        byte[] gesamtpaket = ArrayUtils.addAll(paketstart, payloadsize);
	        gesamtpaket = ArrayUtils.addAll(gesamtpaket, payloadstart);
	        gesamtpaket = ArrayUtils.addAll(gesamtpaket, keycodesize);
	        gesamtpaket = ArrayUtils.addAll(gesamtpaket, keycode);
	        this.sendArray(gesamtpaket);
		}
		
}
