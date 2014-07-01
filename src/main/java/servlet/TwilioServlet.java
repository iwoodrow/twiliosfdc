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
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Gets session cookie
		HttpSession session = request.getSession(true);
		Integer counter = (Integer) session.getAttribute("counter");

		if (counter == null) {
			counter = new Integer(0);
		}

		// Increment the counter by one, and store the count in the session.
		int count = counter.intValue();

		// Figure out where in the session they are
		String newMessage = "nooooooooo";
		String body = (String)request.getParameter("Body");
		if (body != null) {
			if (count >= 0) {
				if (body.equals("y") || body.equals("yes")) {
					newMessage = "Great Job!";
					counter++;
				} else if (body.equals("n") || body.equals("no")) {
					newMessage = "That's ok, tomorrow is a new day. Try to remember tomorrow!";
					//Not positive I want to increment the counter here, but for example purposes I did.
					counter++;
					//Here is where we need to mark something so that if they don't respond we can alert their doctor.
					//maybe make a new field. Need to figure out how to make sessions last longer than four hours, perhaps a premiere account or something.
				
				} else {
					newMessage = "Please respond yes or no.";
				}
			}else if (count > 1){
				//example if the convo wanted to go past a 1 response thing
			}
		}
		else{
			//figure out what to do if body is null.
		}
		//Set counter to be what we want it to be to track conversation progress
		session.setAttribute("counter", new Integer(count));

		// Create a dict of people we know, just for example. Should probably come from a database.
		HashMap<String, String> callers = new HashMap<String, String>();
		callers.put("+16175832821", "Donald");
		callers.put("+19702746400", "Isabelle");

		String fromNumber = request.getParameter("From");
		String fromName = callers.get(fromNumber);
		if (fromName == null) {
			// Use a generic message
			fromName = "You";
		}
		/* For old example
		 * String toNumber = request.getParameter("To");
		 * String message = fromName + " has messaged " + toNumber + " "
				+ String.valueOf(count) + " times.";*/

		// Create a TwiML response and add our message.
		TwiMLResponse twiml = new TwiMLResponse();
		Message sms = new Message(newMessage);
		try {
			twiml.append(sms);
		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		response.setContentType("application/xml");
		response.getWriter().print(twiml.toXML());
	}
}
