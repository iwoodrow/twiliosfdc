package main.java.servlet;
//package com.twilio;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.HashMap;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;
 

@WebServlet(name = "MyServlet1", urlPatterns = { "/sms" })
public class TwilioServlet extends HttpServlet {
 
    // service() responds to both GET and POST requests. 
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	//Gets session cookie
    	HttpSession session = request.getSession(true);
        Integer counter = (Integer)session.getAttribute("counter");
        
        if (counter == null) {
            counter = new Integer(0);
        }
        
        String body = (String)request.getParameter("Body");
        String m2;
        if(body != null) m2 = body;
        else m2 = ":( :(";
        
        /* Increment the counter by one, and store the count in the session. */
        int count = counter.intValue();
        count++;
        session.setAttribute("counter", new Integer(count));
    	
    	// Create a dict of people we know.
        HashMap<String, String> callers = new HashMap<String, String>();
        callers.put("+16175832821", "Donald");
        callers.put("+19702746400", "Isabelle");
 
        String fromNumber = request.getParameter("From");
        String toNumber = request.getParameter("To");
        String fromName = callers.get(fromNumber);
        if (fromName == null) {
            // Use a generic message
            fromName = "You";
        }
        String message = fromName + " has messaged " + toNumber + 
                " " + String.valueOf(count) + " times.";
    	
     // Create a TwiML response and add our message.
        TwiMLResponse twiml = new TwiMLResponse();
        Message sms = new Message(m2);
        try {
            twiml.append(sms);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
   
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}
