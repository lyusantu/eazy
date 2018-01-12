package com.eazy.commons;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {

    private Constants() {
    }

    public static final Integer NUM_PER_PAGE = 15;

    public static final String LOGIN_USER = "loginUser";

    /* url */
    public static final String INDEX = "index";

    public static final String POST_TAGS = "post/tags";

    /* url */

    public static final String SEARCH_TAG = "searchTag";

    public static final String TITLE = "title";

    public static final String TAB1 = "tab1";

    public static final String TAB2 = "tab2";

    public static final String TYPE = "type";

    public static final String TAB1_SELECT = "tab1_select";

    public static final String TAB2_SELECT = "tab2_select";

    public static final String TAB_SELECT_ALL = "all";

    public static final String HOT_WEEKLY_LIST = "hotWeeklyList";

    public static final String PAGE = "page";

    public static final String ROLE_USER = "user";

    public static final String ROLE_ADMIN = "admin";

    public static final String POST_LIST = "postList";

    public static final String REPLY_LIST = "replyList";

    public static final String KEYWORD_LIST = "keywordList";

    public static final String FS_LIST = "fsList";

    public static final String SPONSOR_LIST = "sponsorList";

    public static final String QINIU_CHAIN = "http://oih7sazbd.bkt.clouddn.com/"; // 七牛外链前缀

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
