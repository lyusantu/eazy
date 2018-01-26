package com.eazy.column.controller;

import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.dto.BaseResult;
import com.eazy.index.service.IndexService;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.eazy.post.service.ReplyService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tab")
@Api(value = "/tab", tags = "菜单栏接口")
public class ColumnController {

    @Autowired
    private PostService postService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/{tab}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable(Constants.GET_TAB) String tab, @RequestParam(value = Constants.GET_P, defaultValue = "1") Integer p) {
        List<Column> columnList = columnService.listColumn(new Column(0));
        Column column = null; // 获取当前标识
        if (ObjectUtil.isNotNull(columnList) && columnList.size() != 0) {
            column = new Column();
            for (Column c : columnList) {
                if (c.getSuffix().equalsIgnoreCase(tab)) {
                    column = new Column(c.getId());
                    break;
                }
            }
        }
        List<Column> columnList1 = null;
        if (ObjectUtil.isNotNull(column)) { // 加载二级类型
            columnList1 = columnService.listColumn(column);
            request.setAttribute(Constants.TAB2, columnList1);
        }
        String type = request.getParameter(Constants.GET_TYPE);
        String tab2 = request.getParameter(Constants.GET_TAB);
        Page page = new Page(((p - 1)) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p);
        page.setTotalCount(postService.count(tab, tab2, type));
        List<Post> postList = postService.list(page, tab, tab2, type);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.POST_LIST, postList);
        request.setAttribute(Constants.TYPE, type);
        request.setAttribute(Constants.TAB1, columnList);
        request.setAttribute(Constants.TAB1_SELECT, tab);
        request.setAttribute(Constants.TAB2_SELECT, tab2);
        request.setAttribute(Constants.REPLY_LIST, replyService.weeklyTop());// 回帖周榜
        request.setAttribute(Constants.HOT_WEEKLY_LIST, postService.weeklyTop());// 本周热议
        request.setAttribute(Constants.FS_LIST, indexService.listFriendsSite());// 友链
        request.setAttribute(Constants.KEYWORD_LIST, indexService.listKeyword());// 最热标签
        return Constants.URL_INDEX;
    }

}
