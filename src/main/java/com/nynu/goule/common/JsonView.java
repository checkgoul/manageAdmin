package com.nynu.goule.common;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView extends AbstractView {
    private Object result;

    public JsonView(Object result) {
        super();
        this.result = result;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        /*httpServletResponse.setHeader("Cache-Control", "no-cache");*/
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}