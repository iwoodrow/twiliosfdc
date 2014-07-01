package main.java.servlet;
//package com.twilio;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    	// Create a dict of people we know.
        HashMap<String, String> callers = new HashMap<String, String>();
        callers.put("+14158675309", "Curious George");
        callers.put("+14158675310", "Boots");
        callers.put("+14158675311", "Virgil");
        callers.put("+19702746400", "Isabelle");
 
        String fromNumber = request.getParameter("From");
        String knownCaller = callers.get(fromNumber);
        String message;
        if (knownCaller == null) {
            // Use a generic message
            message = "Thanks for the message!";
        } else {
            // Use the caller's name
            message = knownCaller + ", thanks for the message!";
        }
    	
    	TwiMLResponse twiml = new TwiMLResponse();
        Message sms = new Message(message);
        try {
            twiml.append(sms);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
   
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}
