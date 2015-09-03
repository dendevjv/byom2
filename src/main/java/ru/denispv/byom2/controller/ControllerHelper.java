package ru.denispv.byom2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.denispv.byom2.shared.ButtonMethod;
import ru.denispv.byom2.shared.HelperBaseValidator;
import ru.denispv.byom2.shared.HibernateHelper;
import ru.denispv.byom2.shared.SessionData;
import ru.denispv.byom2.model.Book;

public class ControllerHelper extends HelperBaseValidator {
    private static final String JSP_BASE = "/WEB-INF/jsp/";
    
    private Book data;
    
    public ControllerHelper(HttpServlet servlet, HttpServletRequest request,
            HttpServletResponse response) {
        super(servlet, request, response);
        data = new Book();
    }
    
    static public void initHibernate(HttpServlet servlet) {
        boolean createTables = Boolean.parseBoolean(servlet.getInitParameter("createTables"));
        if (createTables) {
            HibernateHelper.createTable(Book.class);
        }
        
        HibernateHelper.initSessionFactory(Book.class);
    }
    
    public Object getData() {
    	return data;
    }
    
    public void setData(Book data) {
        this.data = data;
    }
	
    @ButtonMethod(buttonName="editButton", isDefault=true)
    public String editMethod() {
        return jspLocation("EditBook.jsp");
    }
    
    @ButtonMethod(buttonName="confirmButton")
    public String confirmMethod() {
        fillBeanFromRequest(data);
        String page;
        if (isValid(data)) {
            page = "ConfirmBook.jsp";
        } else {
            page = "EditBook.jsp";
        }
        return jspLocation(page);
    }
    
    @ButtonMethod(buttonName="processButton")
    public String processMethod() {
        if (!isValid(getData())) {
            return jspLocation("Expired.jsp");
        }
        HibernateHelper.updateDB(getData());
        List<Object> list = HibernateHelper.getListData(getData().getClass());
        request.setAttribute("database", list);
        return jspLocation("ProcessBook.jsp");
    }
	
    public void doGet() throws ServletException, IOException {
        addHelperToSession("helper", SessionData.IGNORE);
        
        String address = editMethod();
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
    
    public void doPost() throws ServletException, IOException {
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
