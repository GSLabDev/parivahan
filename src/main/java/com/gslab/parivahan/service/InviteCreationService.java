/*
 * Copyright (c) 2003-2019, Great Software Laboratory Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gslab.parivahan.service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.gslab.parivahan.util.DateUtil;

@Component
public class InviteCreationService {
    private String version =    "VERSION:2.0\n";
    private String prodid =     "PRODID:-//Google Inc//Google Calendar 70.9054//EN\n";
    private String calBegin =   "BEGIN:VCALENDAR\n";
    private String calEnd =     "END:VCALENDAR\n";
    private String eventBegin = "BEGIN:VEVENT\n";
    private String eventEnd =   "END:VEVENT\n";
    private String CALSCALE = "CALSCALE:GREGORIAN\n";
    private String METHOD ="METHOD:REQUEST\n";
    private String SEQUENCE = "SEQUENCE:0\n";
    private String TRANSP = "TRANSP:OPAQUE\n";

	@Autowired
	private Environment env;
	
        public void InviteCreationService(){
        }

        public File write(String Email,String summary,String startDate,String endDate){
        	  File file = null;
            StringBuilder builder = new StringBuilder();
            builder.append(Email);
            builder.append(".ics");

    String testExample= "DTSTART:"+startDate+"\r\n" + 
    		"DTEND:"+endDate+"\r\n" + 
    		"ORGANIZER;CN=cis-noreply@.com:mailto:"+env.getProperty("parivahan.support.email")+"\r\n" + 
    		"ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=\r\n" + 
    		" TRUE;CN="+Email+";X-NUM-GUESTS=0:mailto:"+Email+"\r\n" + 
    		"CREATED:"+DateUtil.ConvertDateToInvitationFormat(new Date())+"\n";
    
    String Summary = "SUMMARY:"+summary+"\n";
		try {

			file = new File(env.getProperty("parivahan.support.email.invitation.path")+builder.toString());

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(calBegin);
			bw.write(prodid);
			bw.write(version);
			bw.write(CALSCALE);
			bw.write(METHOD);
			bw.write(eventBegin);
			bw.write(testExample);
			bw.write(SEQUENCE);
			bw.write(Summary);
			bw.write(TRANSP);
			bw.write(eventEnd);
			bw.write(calEnd);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
            return file;
        }
}

