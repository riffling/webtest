package org.third.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mvc", method = RequestMethod.GET)
public class HelloMVCController {

    public static final String VIEW_NAME = "hello_mvc_response";

    @RequestMapping(value = "helloMVC", method = RequestMethod.GET)
    public String hello(ModelMap model) {
        model.addAttribute("name", "JCG Hello World");
        return VIEW_NAME;
    }

    @RequestMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Welcome - Spring Security Hello World");
        mav.addObject("message", "This is welcome page!");
        mav.setViewName("/hello");
        return mav;
    }

    @RequestMapping(value = { "/", "/welcome", "/confidential" }, method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Welcome - Spring Security Hello World");
        mav.addObject("message", "This is welcome page!");
        mav.setViewName("/hello");
        return mav;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Admin - Spring Security Hello World");
        mav.addObject("message", "This is protected page!");
        mav.setViewName("/admin");
        return mav;

    }

}
