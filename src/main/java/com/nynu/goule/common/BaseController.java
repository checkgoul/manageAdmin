package com.nynu.goule.common;

import com.nynu.goule.exception.ExceptionResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公共Controller
 */
public class BaseController extends ExceptionResponse {
    //无参时默认返回空
    protected ModelAndView feedback() {
        return feedback(null);
    }

    //有参时，如果传过来的result不为空则将result转json，用于返回
    protected ModelAndView feedback(Result result) {
        Object obj = result != null ? result : "success";
        return new ModelAndView(new JsonView(obj));
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