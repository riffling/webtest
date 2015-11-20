package org.third.spring.rest;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.third.spring.dto.ListBean;
import org.third.spring.dto.UserInfo;
import org.third.spring.rest.rest.UserInfoManager;
import org.third.spring.rest.service.UserService;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserInfo show(@PathVariable int id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return userService.getUserInfo(id);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ListBean getAll() throws Exception {
        return userService.getAllUsers();
    }

    private static final String template = "Hello, %s";
    UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(ModelMap model) {
        model.addAttribute("msg", "JCG Hello World!");
        return "helloWorld";
    }

    @RequestMapping(value = "/displayMessage/{msg}", method = RequestMethod.GET)
    public String displayMessage(@PathVariable String msg, ModelMap model) {
        model.addAttribute("msg", msg);
        return "helloWorld";
    }

    @RequestMapping("/users")
    public @ResponseBody Collection<UserInfo> listUsers(
            @RequestParam(value = "name", defaultValue = "world") String name) {
        return userInfoManager.getUsers();
    }

    // @RequestMapping(method = RequestMethod.POST)
    // public String create(HttpServletRequest request, HttpServletResponse
    // response, UserInfo userInfo) throws Exception {
    // userInfoManager.save(userInfo);
    // return "{result: success}";
    // }
    //
    // /** 编辑 */
    // @RequestMapping(value = "/{id}/edit")
    // public ModelAndView edit(@PathVariable Long id, HttpServletRequest
    // request, HttpServletResponse response)
    // throws Exception {
    // UserInfo userInfo = (UserInfo) userInfoManager.getById(id);
    // return new ModelAndView("/userinfo/edit", "userInfo", userInfo);
    // }
    //
    // /** 保存新增 */
    // @RequestMapping(method = RequestMethod.POST)
    // public ModelAndView create(HttpServletRequest request,
    // HttpServletResponse response, UserInfo userInfo)
    // throws Exception {
    // userInfoManager.save(userInfo);
    // return new ModelAndView(LIST_ACTION);
    // }
    //
    // /** 保存更新 */
    // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // public ModelAndView update(@PathVariable Long id, HttpServletRequest
    // request, HttpServletResponse response)
    // throws Exception {
    // UserInfo userInfo = (UserInfo) userInfoManager.getById(id);
    // bind(request, userInfo);
    // userInfoManager.update(userInfo);
    // return new ModelAndView(LIST_ACTION);
    // }
    //
    // /** 删除 */
    // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    // public ModelAndView delete(@PathVariable Long id, HttpServletRequest
    // request, HttpServletResponse response) {
    // userInfoManager.removeById(id);
    // return new ModelAndView(LIST_ACTION);
    // }
    //
    // /** 批量删除 */
    // @RequestMapping(method = RequestMethod.DELETE)
    // public ModelAndView batchDelete(HttpServletRequest request,
    // HttpServletResponse response) {
    // String[] items = request.getParameterValues("items");
    // for (int i = 0; i < items.length; i++) {
    // java.lang.Long id = new java.lang.Long(items[i]);
    // userInfoManager.removeById(id);
    // }
    // return new ModelAndView(LIST_ACTION);
    // }

}
