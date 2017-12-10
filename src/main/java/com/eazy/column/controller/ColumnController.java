package com.eazy.column.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/column")
public class ColumnController {

    @RequestMapping(value = "/{column}")
    public String index(HttpServletRequest request, @PathVariable("column") String column){
        request.setAttribute("tab_column", column); // curr check
        return "t/index";
    }

}
