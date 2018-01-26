package com.eazy.beauty.controller;

import com.eazy.beauty.entity.Beauty;
import com.eazy.beauty.service.BeautyService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
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
@Api(value = "/beauty", tags = "不可描述")
public class BeautyController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeautyService beautyService;

    @RequestMapping(value = "/{orderWay}/{p}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable(Constants.GET_ORDER_WAY) String order, @PathVariable(Constants.GET_P) Integer p) {
        if (isPa(order, null, request))
            return Constants.URL_ERR_ERR;
        request.setAttribute(Constants.HEADER, "beauty");
        request.setAttribute(Constants.TITLE, "beauty");
        Page page = new Page((p - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p);
        page.setTotalCount(beautyService.countBeauty(order, null));
        List<Beauty> listBeauty = beautyService.listBeauty(page, order, null);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.SET_ORDER, order);
        request.setAttribute(Constants.LIST_BEAUTY, listBeauty);
        return Constants.URL_BEAUTY_INDEX;
    }

    @RequestMapping(value = "/{u}/{orderWay}/{p}", method = RequestMethod.GET)
    public String myPush(HttpServletRequest request, @PathVariable(Constants.GET_U) Integer uid, @PathVariable(Constants.GET_ORDER_WAY) String order, @PathVariable(Constants.GET_P) Integer p) {
        if (isPa(order, uid, request))
            return Constants.URL_ERR_ERR;
        request.setAttribute(Constants.HEADER, "beauty");
        request.setAttribute(Constants.TITLE, "beauty");
        Page page = new Page((p - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p);
        page.setTotalCount(beautyService.countBeauty(order, uid));
        List<Beauty> listBeauty = beautyService.listBeauty(page, order, uid);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.SET_UID, uid);
        request.setAttribute(Constants.SET_ORDER, order);
        request.setAttribute(Constants.LIST_BEAUTY, listBeauty);
        return Constants.URL_BEAUTY_INDEX;
    }

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject push(HttpServletRequest request) {
        String pic = request.getParameter(Constants.GET_PIC);
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_MSG, "用户未登入");
        beautyService.add(new Beauty(user.getId(), pic, new Timestamp(System.currentTimeMillis()), 0, 0));
        return new JSONObject().put(Constants.SET_MSG, "发布成功,请等待审批 :)");
    }

    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject praise(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "用户未登入");
        String id = request.getParameter(Constants.GET_ID);
        String unpraise = request.getParameter(Constants.GET_UNPRAISE);
        Beauty beauty = beautyService.getBeauty(Integer.parseInt(id));
        beauty.setPraise(unpraise.equalsIgnoreCase(Constants.TRUE) ? beauty.getPraise() - 1 : beauty.getPraise() + 1);
        beautyService.praise(Integer.parseInt(id), beauty.getPraise());
        return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_PRAISE, beauty.getPraise());
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject approve(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "用户未登入");
        if (!user.getType().equalsIgnoreCase(Constants.ROLE_ADMIN))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "用户无权限");
        String id = request.getParameter(Constants.GET_ID);
        beautyService.approve(Integer.parseInt(id));
        return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_MSG, "审批成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject delete(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "用户未登入");
        if (!user.getType().equalsIgnoreCase(Constants.ROLE_ADMIN))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "用户无权限");
        String id = request.getParameter(Constants.GET_ID);
        beautyService.delete(Integer.parseInt(id));
        return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_MSG, "删除成功");
    }

    public boolean isPa(String order, Integer uid, HttpServletRequest request) {
        boolean flag = false;
        if (order.equalsIgnoreCase("pa")) {
            request.setAttribute(Constants.SET_MSG, "无权限");
            User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
            if (ObjectUtil.isNotNull(user)) {
                if (ObjectUtil.isNull(uid)) {
                    if (!user.getType().equalsIgnoreCase("admin"))
                        return true;
                } else {
                    if (!user.getType().equalsIgnoreCase("admin") && !user.getId().equals(uid))
                        return true;
                }
            }
        }
        return false;
    }

}
