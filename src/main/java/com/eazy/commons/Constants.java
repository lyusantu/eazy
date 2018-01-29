package com.eazy.commons;

import com.eazy.post.entity.Post;
import com.eazy.post.entity.Reply;
import com.eazy.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Constants {

    private Constants() {
    }

    public static String getAccountRecordDesc(int way, User user, Post post, Reply reply, Integer reward) {
        switch (way) {
            case 0:
                return "感谢 <a href=\"/user/" + user.getId() + "\">" + user.getNickName() + "</a> 在主题 <a href=\"/post/" + post.getId() + "/#item-" + reply.getId() + "\">" + post.getTitle() + "</a> 中的评论";
            case 1:
                return "<a href=\"/user/" + user.getId() + "\">" + user.getNickName() + "</a> 感谢你在主题 <a href\"/post/" + post.getId() + "/#item-" + reply.getId() + "\">" + post.getTitle() + "</a> 中的评论";
            case 2:
                return new SimpleDateFormat("yyyyMMdd").format(new Date()) + " 的每日签到奖励 " + reward + " 飞吻";
            case 3:
                return "收到 <a href=\"/user/" + user.getId() + "\">" + user.getNickName() + "</a> 在主题 <a href=\"/post/" + post.getId() + "/#item-" + reply.getId() + "\">" + post.getTitle() + "</a> 中的评论";
            case 4:
                return "评论了主题 › <a href=\"/post/" + post.getId() + "/#item-" + reply.getId() + "\">" + post.getTitle() + "</a>";
            case 5:
                return "创建了主题 › <a href=\"/post/" + post.getId() + "\">" + post.getTitle() +"</a>";
            case 6:
                return "获得初始资金 " +  reward + " 飞吻";
            default:
                return null;
        }
    }

    public static Timestamp getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(LOGIN_USER);
    }

    public static final Integer DEFAULT_REDUCE = 5;

    public static final Integer DEFAULT_THANKS = 10;

    public static final Integer DEFAULT_BALANCE = 200;

    public static final Integer NUM_PER_PAGE = 20;

    public static final String LOGIN_USER = "loginUser";

    public static final String HEADER = "header";

    public static final String TITLE = "title";

    public static final String TAB1 = "tab1";

    public static final String TAB2 = "tab2";

    public static final String TYPE = "type";

    public static final String TAB1_SELECT = "tab1_select";

    public static final String TAB2_SELECT = "tab2_select";

    public static final String TAB_SELECT_ALL = "all";

    public static final String PAGE = "page";

    public static final String LIMIT = "limit";

    public static final String KEYWORDS = "keywords";

    public static final String VERIFY = "verify";

    public static final String VERIFY_ID = "verid";

    public static final String VERIFY_CODE = "vercode";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    /* get */

    public static final String GET_U = "u";

    public static final String GET_P = "p";

    public static final String GET_Q = "q";

    public static final String GET_ID = "id";

    public static final String GET_ALL = "all";

    public static final String GET_CID = "cid";

    public static final String GET_PID = "pid";

    public static final String GET_TAB = "tab";

    public static final String GET_TAG = "tag";

    public static final String GET_PIC = "pic";

    public static final String GET_CODE = "code";

    public static final String GET_PASS = "pass";

    public static final String GET_REPASS = "repass";

    public static final String GET_USERNAME = "username";

    public static final String GET_UNPRAISE = "unpraise";

    public static final String GET_TYPE = "type";

    public static final String GET_FILE = "file";

    public static final String GET_ORDER_WAY = "orderWay";

    public static final String GET_FIEID = "field";

    public static final String GET_RANK = "rank";

    /* set */

    public static final String SET_COLLECTION = "collection";

    public static final String SET_STATUS = "status";

    public static final String SET_ERR = "err";

    public static final String SET_MSG = "msg";

    public static final String SET_UID = "uid";

    public static final String SET_WAY = "way";

    public static final String SET_TIME = "time";

    public static final String SET_COUNT = "count";

    public static final String SET_ORDER = "order";

    public static final String SET_ACTION = "action";

    public static final String SET_PRAISE = "praise";

    public static final String SET_DAYS = "days";

    public static final String SET_CODE = "code";

    public static final String SET_SIGNED = "signed";

    public static final String SET_EXPERIENCE = "experience";

    public static final String SET_USERNAME = "username";

    public static final String SET_AVATAR = "avatar";

    public static final String SET_EMAIL = "email";

    public static final String SET_PASSWORD = "password";

    public static final String SET_REPASSWORD = "repassword";

    /* url */
    public static final String URL_INDEX = "index";

    public static final String URL_ERR_ERR = "err/err";

    public static final String URL_POST_ADD = "post/add";

    public static final String URL_POST_TAGS = "post/tags";

    public static final String URL_POST_EDIT = "post/edit";

    public static final String URL_POST_INDEX = "post/index";

    public static final String URL_BEAUTY_INDEX = "beauty/index";

    public static final String URL_USER_SIGNIN = "/user/signin";

    public static final String URL_USER_SET = "user/set";

    public static final String URL_USER_HOME = "user/home";

    public static final String URL_USER_REG = "user/reg";

    public static final String URL_USER_LOGIN = "user/login";

    public static final String URL_USER_USERHOME = "user/userhome";

    public static final String URL_USER_POST = "user/post";

    public static final String URL_USER_MESSAGE = "user/message";

    public static final String URL_USER_FORGET = "user/forget";

    public static final String URL_USER_CUSTOM = "/user/custom";


    /* entity */

    public static final String ENTITY_POST = "post";

    public static final String ENTITY_USER = "user";

    /* role */

    public static final String ROLE_USER = "user";

    public static final String ROLE_ADMIN = "admin";

    /* list */

    public static final String LIST = "list";

    public static final String POST_LIST = "postList";

    public static final String REPLY_LIST = "replyList";

    public static final String KEYWORD_LIST = "keywordList";

    public static final String HOT_WEEKLY_LIST = "hotWeeklyList";

    public static final String FS_LIST = "fsList";

    public static final String SPONSOR_LIST = "sponsorList";

    public static final String LIST_BEAUTY = "listBeauty";

    public static final String LIST_TYPE = "listType";

    public static final String LIST_POST_UPDATE_RECORD = "postUpdateRecordList";

    public static final String WEEK_HOT = "weekHot";

    public static final String SEARCH_TAG = "searchTag";

    public static final String QINIU_CHAIN = "http://oih7sazbd.bkt.clouddn.com/"; // 七牛外链前缀

    public static void resetUserInfo(HttpServletRequest request, User user) {
        request.setAttribute(Constants.LOGIN_USER, user);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
            ipAddress = request.getHeader("Proxy-Client-IP");
        if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress))
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > 15) {
            //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
