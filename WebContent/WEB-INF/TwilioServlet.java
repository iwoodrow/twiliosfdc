package main.java.servlet;
//package com.twilio;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


import java.io.IOException;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;
 

//@WebServlet(name = "MyServlet1", urlPatterns = { "/sms" })
public class TwilioServlet extends HttpServlet {
 
    // service() responds to both GET and POST requests. 
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServletOutputStream out = response.getOutputStream();
		out.write("SMS".getBytes());
		out.flush();
		out.close();
    	TwiMLResponse twiml = new TwiMLResponse();
        Message message = new Message("Hello, Mobile Monkey");
        try {
            twiml.append(message);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
   
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}
