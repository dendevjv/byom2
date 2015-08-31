package ru.denispv.byom2.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.denispv.byom2.shared.ButtonMethod;
import ru.denispv.byom2.shared.SessionData;
import ru.denispv.byom2.model.Book;
import ru.denispv.byom2.shared.HelperBase;

public class ControllerHelper extends HelperBase {
    private static final String JSP_BASE = "/WEB-INF/jsp/";
    
	private Book data;

	public ControllerHelper(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) {
		super(servlet, request, response);
		data = new Book();
	}

	public Object getData() {
		return data;
	}
	
	@ButtonMethod(buttonName="editButton", isDefault=true)
    public String editMethod() {
        return jspLocation("EditBook.jsp");
    }
    
    @ButtonMethod(buttonName="confirmButton")
    public String confirmMethod() {
        fillBeanFromRequest(data);
        return jspLocation("ConfirmBook.jsp");
    }
    
    @ButtonMethod(buttonName="processButton")
    public String processMethod() {
        return jspLocation("ProcessBook.jsp");
    }
	
	public void doGet() throws ServletException, IOException {
	    addHelperToSession("helper", SessionData.READ);
	    
		String address = executeButtonMethod();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

    @Override
    protected void copyFromSession(Object sessionHelper) {
        if (this.getClass() == sessionHelper.getClass()) {
            ControllerHelper oldHelper = (ControllerHelper) sessionHelper;
            data = oldHelper.data;
        }
    }
    
    protected String jspLocation(String page) {
        return JSP_BASE + page;
    }
}
