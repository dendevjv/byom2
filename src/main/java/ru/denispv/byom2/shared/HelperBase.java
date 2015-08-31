package ru.denispv.byom2.shared;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public abstract class HelperBase {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpServlet servlet;
    protected Logger logger;
    private Method methodDefault = null;
    
    public HelperBase(HttpServlet servlet, HttpServletRequest request,
            HttpServletResponse response) {
        this.servlet = servlet;
        this.request = request;
        this.response = response;
        
        initLogger();
    }
    
    protected void initLogger() {
        String logName = "ru.denispv.byom2";
        String initName = servlet.getInitParameter("logName");
        if (initName != null) {
            logName = initName;
        }
        
        Level logLevel = Level.DEBUG;
        String strLevel = servlet.getInitParameter("logLevel");
        if (strLevel != null) {
            logLevel = Level.toLevel(strLevel);
        }
        
        logger = Logger.getLogger(logName);
        logger.setLevel(logLevel);
        logger.info("Starting " + logger.getName());
    }
    
    protected abstract void copyFromSession(Object helper);
    
    public void addHelperToSession(String name, SessionData state) {
        if (SessionData.READ == state) {
            Object sessionObj = request.getSession().getAttribute(name);
            if (sessionObj != null) {
                copyFromSession(sessionObj);
            }
        }
        request.getSession().setAttribute(name, this);
    }
    
    public void addHelperToSession(String name, boolean checkSession) {
        if (checkSession) {
            Object sessionObj = request.getSession().getAttribute(name);
            if (sessionObj != null) {
                copyFromSession(sessionObj);
            }
        }
        request.getSession().setAttribute(name, this);
    }
    
    @SuppressWarnings("rawtypes")
    protected String executeButtonMethod() throws IOException {
        String result = "";
        methodDefault = null;
        Class clazz = this.getClass();
        Class enclosingClass = clazz.getEnclosingClass();
        while (enclosingClass != null) {
            clazz = this.getClass();
            enclosingClass = clazz.getEnclosingClass();
        }
        
        try {
            result = executeButtonMethod(clazz, true);
        } catch (Exception e) {
            writeError(request, response, "Button Method Error", e);
            return "";
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    protected String executeButtonMethod(Class clazz, boolean searchForDefault) throws IllegalAccessException, InvocationTargetException {
        String result = "";
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            ButtonMethod annotation = method.getAnnotation(ButtonMethod.class);
            if (annotation != null) {
                if (searchForDefault && annotation.isDefault()) {
                    methodDefault = method;
                }
                if (request.getParameter(annotation.buttonName()) != null) {
                    result = invokeButtonMethod(method);
                    break;
                }
            }
        }
        if (result.equals("")) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                result = executeButtonMethod(superClass, methodDefault == null);
            }
            if (result.equals("")) {
                if (methodDefault != null) {
                    result = invokeButtonMethod(methodDefault);
                } else {
                    logger.error("(executeButtonMethod) No default method "
                            + "was specified, but one was needed.");
                    result = "No default method was specified.";
                }
            }
        }
        return result;
    }
    
    private String invokeButtonMethod(Method method) throws IllegalAccessException, InvocationTargetException {
        String resultInvoke = "Could not invoke method";
        try {
            Object obj = method.invoke(this, (Object[]) null);
            if (obj instanceof String) {
                resultInvoke = (String) obj;
            }
        } catch (IllegalAccessException e) {
            logger.error("(invoke) Button method is not public. ", e);
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("(invoke) Illegal Argument. ", e);
            throw e;
        } catch (InvocationTargetException e) {
            logger.error("(invoke) Button method exception. ", e);
            throw e;
        }
        return resultInvoke;
    }
    
    public void fillBeanFromRequest(Object bean) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean,
                    request.getParameterMap());
        } catch (IllegalAccessException e) {
            logger.error("Populate - Illegal Access. ", e);
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target. ", e);
        }
    }
    
    private void writeError(HttpServletRequest req,
            HttpServletResponse resp, String title, Exception ex) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<html>");
        out.println("  <head>\n    <title>" + title + "</title>\n  </head>");
        out.println("  <body>\n    <h2>" + title + "</h2>");
        if (ex.getMessage() != null) {
            out.println("    <h3>" + ex.getMessage() + "</h3>");
        }
        if (ex.getCause() != null) {
            out.println("    <h4>" + ex.getCause() + "</h4>");
        }
        StackTraceElement[] trace = ex.getStackTrace();
        if (trace != null && trace.length > 0) {
            out.print("<pre>");
            ex.printStackTrace(out);
            out.print("</pre>");
        }
        out.println("  </body>\n</html>");
        out.close();
    }
    
    protected void doGet() throws ServletException, IOException {
        response.getWriter().print("The doGet method must be overriden "
                + "in the class that extends HelperBase.");
    }
    
    protected void doPost() throws ServletException, IOException {
        response.getWriter().print("The doPost method must be overriden "
                + "in the class that extends HelperBase.");
    }
}
