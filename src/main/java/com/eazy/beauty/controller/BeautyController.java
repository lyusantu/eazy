package com.eazy.beauty.controller;

import com.eazy.beauty.entity.Beauty;
import com.eazy.beauty.service.BeautyService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/beauty")
public class BeautyController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeautyService beautyService;

    @RequestMapping(value = "/{orderWay}/{p}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("orderWay") String order, @PathVariable("p") Integer p) {
        if (order.equalsIgnoreCase("pa")) {
            User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
            if (ObjectUtil.isNull(user)) {
                request.setAttribute("msg", "无权限");
                return "err/err";
            }
        }
        request.setAttribute("header", "beauty");
        Page page = new Page((p - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p);
        page.setTotalCount(beautyService.countBeauty(order, null));
        List<Beauty> listBeauty = beautyService.listBeauty(page, order, null);
        request.setAttribute("order", order);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute("listBeauty", listBeauty);
        return "beauty/index";
    }

    @RequestMapping(value = "/{u}/{orderWay}/{p}", method = RequestMethod.GET)
    public String myPush(HttpServletRequest request, @PathVariable("u") Integer uid, @PathVariable("orderWay") String order, @PathVariable("p") Integer p) {
        if (order.equalsIgnoreCase("pa")) {
            User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
            if (ObjectUtil.isNull(user)) {
                request.setAttribute("msg", "无权限");
                return "err/err";
            }
        }
        request.setAttribute("header", "beauty");
        Page page = new Page((p - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p);
        page.setTotalCount(beautyService.countBeauty(order, uid));
        List<Beauty> listBeauty = beautyService.listBeauty(page, order, uid);
        request.setAttribute("uid", uid);
        request.setAttribute("order", order);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute("listBeauty", listBeauty);
        return "beauty/index";
    }

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject push(HttpServletRequest request) {
        String pic = request.getParameter("pic");
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("msg", "用户未登入");
        beautyService.add(new Beauty(user.getId(), pic, new Timestamp(System.currentTimeMillis()), 0, 0));
        return new JSONObject().put("msg", "发布成功,请等待审批 :)");
    }

    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject praise(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("status", 1).put("msg", "用户未登入");
        String id = request.getParameter("id");
        String unpraise = request.getParameter("unpraise");
        Beauty beauty = beautyService.getBeauty(Integer.parseInt(id));
        beauty.setPraise(unpraise.equalsIgnoreCase("true") ? beauty.getPraise() - 1 : beauty.getPraise() + 1);
        beautyService.praise(Integer.parseInt(id), beauty.getPraise());
        return new JSONObject().put("status", 0).put("praise", beauty.getPraise());
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject approve(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("status", 1).put("msg", "用户未登入");
        if (!user.getType().equalsIgnoreCase("admin"))
            return new JSONObject().put("status", 1).put("msg", "用户无权限");
        String id = request.getParameter("id");
        beautyService.approve(Integer.parseInt(id));
        return new JSONObject().put("status", 0).put("msg", "审批成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject delete(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("status", 1).put("msg", "用户未登入");
        if (!user.getType().equalsIgnoreCase("admin"))
            return new JSONObject().put("status", 1).put("msg", "用户无权限");
        String id = request.getParameter("id");
        beautyService.delete(Integer.parseInt(id));
        return new JSONObject().put("status", 0).put("msg", "删除成功");
    }

}
