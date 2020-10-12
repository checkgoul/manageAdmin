package com.nynu.goule.common;

import com.nynu.goule.exception.ExceptionResponse;
import org.springframework.web.servlet.ModelAndView;

public class BaseController extends ExceptionResponse {
    protected ModelAndView feedback() {
        return feedback(null);
    }

    protected ModelAndView feedback(Result obj) {
        Object result = obj != null ? obj : "success";
        return new ModelAndView(new JsonView(result));
    }

    /*protected static <T> T parseModel(String modelJSON, T model) throws ServiceException {
        return parseModel(modelJSON, model, null, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key) throws ServiceException {
        return parseModel(modelJSON, model, key, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key, ParseBeanOptions options)
            throws ServiceException {
        try {
            return Bean.fromJson(modelJSON, model, options);
        } catch (Exception e) {
            throw new ArgumentServiceException((key != null ? key : "model"));
        }
    }*/
}