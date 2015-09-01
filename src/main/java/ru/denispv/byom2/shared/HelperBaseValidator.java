package ru.denispv.byom2.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.internal.engine.PathImpl;

public abstract class HelperBaseValidator extends HelperBase {
    protected static final ValidatorFactory validatorFactory = Validation
            .buildDefaultValidatorFactory();
    protected static final Validator validator = validatorFactory
            .getValidator();
    
    protected Map<String, String> errorMap = new HashMap<String, String>();

    public HelperBaseValidator(HttpServlet servlet, HttpServletRequest request,
            HttpServletResponse response) {
        super(servlet, request, response);
    }

    public void setErrors(Object data) {
        Set<ConstraintViolation<Object>> violations = validator.validate(data);
        
        errorMap.clear();
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> msg : violations) {
                PathImpl value = (PathImpl) msg.getPropertyPath();
                errorMap.put(value.getLeafNode().getName(), msg.getMessage());
            }
        }
    }
    
    public boolean isValid(Object data) {
        setErrors(data);
        return errorMap.isEmpty();
    }
    
    public Map<String, String> getErrors() {
        return errorMap;
    }
    
    public void clearErrors() {
        if (errorMap != null) {
            errorMap.clear();
        }
    }
    
    public boolean isValidProperty(String name) {
        String msg = errorMap.get(name);
        return msg == null || msg.equals("");
    }
}
