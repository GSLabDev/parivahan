package com.gslab.parivahan.notification.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.gslab.parivahan.notification.dto.SMSDto;

@Service
public class SendSmsService {

	@Autowired
	private Environment env;
	
	public String sendSms(SMSDto dto) {
	//Prepare Url
    URLConnection myURLConnection=null;
    URL myURL=null;
    BufferedReader reader=null;

    //encoding message
    String encoded_message=URLEncoder.encode(dto.getMessage());

    //Send SMS API
    String mainUrl="http://api.msg91.com/api/sendhttp.php?";

    //Prepare parameter string
    StringBuilder sbPostData= new StringBuilder(mainUrl);
    sbPostData.append("authkey="+env.getProperty("gslab.sms.authkey"));
    sbPostData.append("&mobiles="+dto.getMobileNumber());
    sbPostData.append("&message="+encoded_message);
    sbPostData.append("&route="+env.getProperty("gslab.sms.route"));
    sbPostData.append("&sender="+env.getProperty("gslab.sms.senderid"));

    //final string
    mainUrl = sbPostData.toString();
    try
    {
        //prepare connection
        myURL = new URL(mainUrl);
        myURLConnection = myURL.openConnection();
        myURLConnection.connect();
        reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
        //reading response
        String response;
        while ((response = reader.readLine()) != null)
        //print response
        System.out.println(response);
         return response;
       
    }
    catch (IOException e)
    {
            e.printStackTrace();
    }
    finally {
       if(null != reader) {
    	 //finally close connection
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
       }
    }
    return null;
	
}

}
