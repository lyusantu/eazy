/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : eazy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-12 16:07:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `collection_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('28', '17', '1', '2017-12-21 13:54:40');
INSERT INTO `collection` VALUES ('29', '18', '1', '2017-12-21 14:53:51');
INSERT INTO `collection` VALUES ('31', '27', '1', '2017-12-26 15:42:16');
INSERT INTO `collection` VALUES ('32', '36', '15', '2017-12-27 11:08:13');
INSERT INTO `collection` VALUES ('34', '29', '1', '2018-01-10 18:14:32');

-- ----------------------------
-- Table structure for `column`
-- ----------------------------
DROP TABLE IF EXISTS `column`;
CREATE TABLE `column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `suffix` varchar(255) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of column
-- ----------------------------
INSERT INTO `column` VALUES ('2', '技术', 'tech', '0');
INSERT INTO `column` VALUES ('3', '创意', 'creative', '0');
INSERT INTO `column` VALUES ('4', '交易', 'deals', '0');
INSERT INTO `column` VALUES ('5', '问与答', 'qna', '0');
INSERT INTO `column` VALUES ('7', '酷工作', 'jobs', '0');
INSERT INTO `column` VALUES ('8', '城市', 'city', '0');
INSERT INTO `column` VALUES ('9', '程序员', 'programmer', '2');
INSERT INTO `column` VALUES ('10', 'Python', 'python', '2');
INSERT INTO `column` VALUES ('11', 'IOS', 'ios', '2');
INSERT INTO `column` VALUES ('12', 'Android', 'android', '2');
INSERT INTO `column` VALUES ('13', 'Linux', 'linux', '2');
INSERT INTO `column` VALUES ('14', '云计算', 'cloud', '2');
INSERT INTO `column` VALUES ('15', '分享创造', 'sharecreate', '3');
INSERT INTO `column` VALUES ('16', '设计', 'design', '3');
INSERT INTO `column` VALUES ('17', '奇思妙想', 'ideas', '3');
INSERT INTO `column` VALUES ('18', '好玩', 'play', '0');
INSERT INTO `column` VALUES ('19', '分享发现', 'sharefind', '18');
INSERT INTO `column` VALUES ('20', '电子游戏', 'games', '18');
INSERT INTO `column` VALUES ('21', '电影', 'movie', '18');
INSERT INTO `column` VALUES ('22', '音乐', 'music', '18');
INSERT INTO `column` VALUES ('23', '旅游', 'travel', '18');
INSERT INTO `column` VALUES ('24', '物物交换', 'exchange', '4');
INSERT INTO `column` VALUES ('25', '免费赠送', 'free', '4');
INSERT INTO `column` VALUES ('26', '域名', 'dn', '4');
INSERT INTO `column` VALUES ('27', '团购', 'tuan', '4');
INSERT INTO `column` VALUES ('28', '求职', 'cv', '7');
INSERT INTO `column` VALUES ('29', '外包', 'outsourcing', '7');
INSERT INTO `column` VALUES ('30', '职场话题', 'career', '7');
INSERT INTO `column` VALUES ('31', '北京', 'beijing', '8');
INSERT INTO `column` VALUES ('32', '上海', 'shanghai', '8');
INSERT INTO `column` VALUES ('33', '广州', 'guangzhou', '8');
INSERT INTO `column` VALUES ('34', '深圳', 'shenzhen', '8');
INSERT INTO `column` VALUES ('35', '杭州', 'hangzhou', '8');
INSERT INTO `column` VALUES ('36', '西安', 'xian', '8');
INSERT INTO `column` VALUES ('37', '成都', 'chengdu', '8');
INSERT INTO `column` VALUES ('38', '问与答', 'qna', '5');

-- ----------------------------
-- Table structure for `friends_site`
-- ----------------------------
DROP TABLE IF EXISTS `friends_site`;
CREATE TABLE `friends_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friends_site
-- ----------------------------
INSERT INTO `friends_site` VALUES ('1', 'MyBlog', 'http://blog.lyu3.com/', '2017-12-26 16:22:50');

-- ----------------------------
-- Table structure for `keyword`
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(255) NOT NULL,
  `pid` int(11) NOT NULL,
  `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of keyword
-- ----------------------------
INSERT INTO `keyword` VALUES ('6', '阿里云高性能', '45', '2018-01-10 18:13:45');
INSERT INTO `keyword` VALUES ('7', '哈哈哈', '45', '2018-01-10 18:13:45');
INSERT INTO `keyword` VALUES ('18', '主页', '29', '2018-01-12 10:32:09');
INSERT INTO `keyword` VALUES ('19', '越南', '29', '2018-01-12 10:32:09');
INSERT INTO `keyword` VALUES ('20', '特色', '29', '2018-01-12 10:32:09');
INSERT INTO `keyword` VALUES ('21', '个人', '29', '2018-01-12 10:32:09');
INSERT INTO `keyword` VALUES ('22', '这个', '29', '2018-01-12 10:32:09');
INSERT INTO `keyword` VALUES ('24', '解析', '43', '2018-01-12 11:37:56');
INSERT INTO `keyword` VALUES ('25', '大声', '43', '2018-01-12 11:37:56');
INSERT INTO `keyword` VALUES ('26', '存储', '43', '2018-01-12 11:37:56');
INSERT INTO `keyword` VALUES ('27', '实例', '43', '2018-01-12 11:37:56');
INSERT INTO `keyword` VALUES ('28', '当前', '43', '2018-01-12 11:37:56');
INSERT INTO `keyword` VALUES ('29', '你好', '41', '2018-01-12 11:39:14');
INSERT INTO `keyword` VALUES ('30', '网页', '40', '2018-01-12 11:39:24');
INSERT INTO `keyword` VALUES ('31', '语言', '40', '2018-01-12 11:39:24');
INSERT INTO `keyword` VALUES ('32', '可以', '40', '2018-01-12 11:39:24');
INSERT INTO `keyword` VALUES ('33', '网站', '40', '2018-01-12 11:39:24');
INSERT INTO `keyword` VALUES ('34', '学习', '40', '2018-01-12 11:39:24');
INSERT INTO `keyword` VALUES ('35', '音质', '37', '2018-01-12 11:39:36');
INSERT INTO `keyword` VALUES ('36', '会员', '37', '2018-01-12 11:39:36');
INSERT INTO `keyword` VALUES ('37', '自动', '37', '2018-01-12 11:39:36');
INSERT INTO `keyword` VALUES ('38', '音乐', '37', '2018-01-12 11:39:36');
INSERT INTO `keyword` VALUES ('39', '无损', '37', '2018-01-12 11:39:36');
INSERT INTO `keyword` VALUES ('40', '办公室', '28', '2018-01-12 11:39:48');
INSERT INTO `keyword` VALUES ('41', '扫地', '28', '2018-01-12 11:39:48');
INSERT INTO `keyword` VALUES ('42', '比较', '27', '2018-01-12 11:40:04');
INSERT INTO `keyword` VALUES ('43', '深圳', '27', '2018-01-12 11:40:04');
INSERT INTO `keyword` VALUES ('44', '最好', '27', '2018-01-12 11:40:04');
INSERT INTO `keyword` VALUES ('45', '推荐', '27', '2018-01-12 11:40:04');
INSERT INTO `keyword` VALUES ('46', '浪漫', '27', '2018-01-12 11:40:04');
INSERT INTO `keyword` VALUES ('47', '吐槽', '26', '2018-01-12 11:40:21');
INSERT INTO `keyword` VALUES ('48', '域名', '26', '2018-01-12 11:40:21');
INSERT INTO `keyword` VALUES ('49', '万网', '26', '2018-01-12 11:40:21');
INSERT INTO `keyword` VALUES ('50', '国内', '26', '2018-01-12 11:40:21');
INSERT INTO `keyword` VALUES ('51', '黑暗', '26', '2018-01-12 11:40:21');
INSERT INTO `keyword` VALUES ('52', '代理', '25', '2018-01-12 11:40:29');
INSERT INTO `keyword` VALUES ('53', '多少', '25', '2018-01-12 11:40:29');
INSERT INTO `keyword` VALUES ('54', '连接', '25', '2018-01-12 11:40:29');
INSERT INTO `keyword` VALUES ('55', '负载', '25', '2018-01-12 11:40:29');
INSERT INTO `keyword` VALUES ('56', '均衡', '25', '2018-01-12 11:40:29');
INSERT INTO `keyword` VALUES ('57', '登录', '24', '2018-01-12 11:40:33');
INSERT INTO `keyword` VALUES ('58', '--', '24', '2018-01-12 11:40:33');
INSERT INTO `keyword` VALUES ('59', '验证', '24', '2018-01-12 11:40:33');
INSERT INTO `keyword` VALUES ('60', '框架', '24', '2018-01-12 11:40:33');
INSERT INTO `keyword` VALUES ('61', '基于', '24', '2018-01-12 11:40:33');
INSERT INTO `keyword` VALUES ('62', '早上', '23', '2018-01-12 11:40:47');
INSERT INTO `keyword` VALUES ('63', '晚上', '23', '2018-01-12 11:40:47');
INSERT INTO `keyword` VALUES ('64', '怎么办', '23', '2018-01-12 11:40:47');
INSERT INTO `keyword` VALUES ('65', '每天', '23', '2018-01-12 11:40:47');
INSERT INTO `keyword` VALUES ('66', '时不时', '23', '2018-01-12 11:40:47');
INSERT INTO `keyword` VALUES ('67', '程序员', '22', '2018-01-12 11:40:53');
INSERT INTO `keyword` VALUES ('68', '言论', '22', '2018-01-12 11:40:53');
INSERT INTO `keyword` VALUES ('69', '怎么', '22', '2018-01-12 11:40:53');
INSERT INTO `keyword` VALUES ('70', '对于', '22', '2018-01-12 11:40:53');
INSERT INTO `keyword` VALUES ('71', '原文', '22', '2018-01-12 11:40:53');
INSERT INTO `keyword` VALUES ('72', '编辑器', '21', '2018-01-12 11:40:59');
INSERT INTO `keyword` VALUES ('73', '各位', '21', '2018-01-12 11:40:59');
INSERT INTO `keyword` VALUES ('74', '什么', '21', '2018-01-12 11:40:59');
INSERT INTO `keyword` VALUES ('75', '现在', '21', '2018-01-12 11:40:59');
INSERT INTO `keyword` VALUES ('76', '使用', '21', '2018-01-12 11:40:59');
INSERT INTO `keyword` VALUES ('77', '你好', '46', '2018-01-12 11:43:47');
INSERT INTO `keyword` VALUES ('78', '世界', '46', '2018-01-12 11:43:47');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `atid` int(11) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` int(11) NOT NULL COMMENT '0:评论,1:艾特,2:通知',
  `content` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `reply` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('35', '29', '10', '10', '2017-12-26 15:38:48', '1', null, '1', '65');
INSERT INTO `message` VALUES ('41', '32', '12', '12', '2017-12-27 10:59:35', '0', null, '1', '69');
INSERT INTO `message` VALUES ('42', '33', '14', '13', '2017-12-27 11:01:27', '0', null, '1', '70');
INSERT INTO `message` VALUES ('43', '36', '15', '15', '2017-12-27 11:08:27', '0', null, '0', '71');
INSERT INTO `message` VALUES ('44', '36', '15', '15', '2017-12-27 11:08:30', '0', null, '0', '72');
INSERT INTO `message` VALUES ('45', '33', '1', '13', '2018-01-04 14:11:18', '0', null, '1', '73');
INSERT INTO `message` VALUES ('46', '33', '1', '13', '2018-01-04 14:32:56', '0', null, '1', '74');
INSERT INTO `message` VALUES ('47', '33', '1', '13', '2018-01-04 14:32:59', '0', null, '1', '75');
INSERT INTO `message` VALUES ('48', '33', '1', '13', '2018-01-04 14:33:02', '0', null, '1', '76');
INSERT INTO `message` VALUES ('49', '33', '1', '13', '2018-01-04 14:33:05', '0', null, '1', '77');
INSERT INTO `message` VALUES ('50', '33', '1', '13', '2018-01-04 14:46:26', '0', null, '1', '78');
INSERT INTO `message` VALUES ('51', '33', '1', '13', '2018-01-04 15:05:34', '0', null, '1', '79');
INSERT INTO `message` VALUES ('52', '33', '1', '13', '2018-01-04 15:10:04', '0', null, '1', '80');
INSERT INTO `message` VALUES ('53', '33', '12', '13', '2018-01-05 16:15:45', '0', null, '1', '81');
INSERT INTO `message` VALUES ('54', '33', '12', '13', '2018-01-05 16:17:00', '0', null, '1', '82');
INSERT INTO `message` VALUES ('55', '33', '13', '13', '2018-01-05 16:57:07', '0', null, '1', '83');

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `content` text NOT NULL,
  `reward` int(11) NOT NULL COMMENT '奖励',
  `delete` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `author` int(11) NOT NULL,
  `comments` int(11) DEFAULT NULL,
  `readers` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '状态:未结/已结',
  `top` int(11) NOT NULL,
  `wonderful` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('16', 'eazy社区未完成版本说明', '9', '凡提示“请求异常，请重试”的功能，均为未完成功能 face[嘻嘻] \n\nimg[https://static.oschina.net/uploads/space/2017/1220/143949_dmar_2903254.jpg] ', '20', '1', '2017-11-20 16:06:05', '1', '23', '97', '0', '0', '1');
INSERT INTO `post` VALUES ('17', '占位文章', '19', 'face[思考] face[思考] face[思考] ', '20', '1', '2017-12-20 16:06:44', '1', '4', '92', '0', '0', '0');
INSERT INTO `post` VALUES ('18', '每个功能都是一笔iana啊啊啊', '24', '测试代码啊啊\n[pre]\n                            upload.render({\n                                elem: \'#uploadImg\'\n                                , url: \'/api/upload/\'\n                                , size: 200\n                                , done: function (res) {\n                                    if (res.status == 0) {\n                                        image.val(res.url);\n                                    } else {\n                                        layer.msg(res.msg, {icon: 5});\n                                    }\n                                }\n                            });\n[/pre]\n[hr]\n测试上传图片\nimg[http://oih7sazbd.bkt.clouddn.com/Fvb7JPJkVnaPNWUb9m35ZIE56kCd] \n[hr]\n测试表情\nface[微笑] face[嘻嘻] face[哈哈] face[可爱] face[可怜] \n[hr]\n测试超链接\n a(https://github.com/lyusantu)[https://github.com/lyusantu] ', '20', '1', '2017-12-21 10:49:20', '1', '0', '21', '0', '0', '0');
INSERT INTO `post` VALUES ('19', '啊啊', '28', '啊', '80', '1', '2017-12-21 10:53:28', '1', '0', '0', '0', '0', '0');
INSERT INTO `post` VALUES ('20', '1', '9', '1', '80', '0', '2017-12-25 16:43:29', '1', '0', '3', '0', '0', '0');
INSERT INTO `post` VALUES ('21', '各位现在使用的是什么编辑器啊？', '19', '使用 sublime 了一段时间，不喜欢就一直想着换 \n换过 vscode，又不可以直接在编辑器打开浏览器查看 php 文件 \n也试了一下 netbeans，不过总感觉很别扭 \n大佬们有什么好的看法', '20', '0', '2017-12-25 18:08:40', '1', '0', '6', '0', '0', '0');
INSERT INTO `post` VALUES ('22', '对于“程序员到 35 岁就不行了”的言论，程序员，你怎么看？', '24', '原文：  a(https://mp.weixin.qq.com/s/gZ4auao7buWk3feJ5_kuhg)[https://mp.weixin.qq.com/s/gZ4auao7buWk3feJ5_kuhg] \n\n常规程序员的职业规划有哪几种？只能转技术专家、技术总监、产品经理、自由职业者这几种吗？', '20', '0', '2017-12-25 18:09:19', '1', '0', '2', '0', '0', '0');
INSERT INTO `post` VALUES ('23', '晚上睡不着，早上起不来。该怎么办？', '9', '每天都要拖到 8:15 才肯起床，每天都迟到 3-8 分钟（时不时堵车）。\n\n而且，一到晚上精力满满，12 点多都很精神。\n\n然而，第二天早上却没什么精神。\n\n该怎么办啊？都一个多月了。', '20', '0', '2017-12-25 18:09:46', '1', '0', '2', '0', '0', '0');
INSERT INTO `post` VALUES ('24', '基于权限安全框架 Shiro 的登录验证功能实现', '9', '目前在企业级项目里做权限安全方面喜欢使用 Apache 开源的 Shiro 框架或者 Spring 框架的子框架 Spring Security。\n\nApache Shiro 是一个强大且易用的 Java 安全框架,执行身份验证、授权、密码学和会话管理。\n\nShiro 框架具有轻便，开源的优点，所以本博客介绍基于 Shiro 的登录验证实现。\n\n本博客只提供基于 Shiro 的登录验证实现，具体代码可以去我的 github 下载：  a(https://github.com/u014427391/jeeplatform)[https://github.com/u014427391/jeeplatform]  欢迎 star\n\n在 maven 里加入 shiro 需要的 jar\n\n[pre]\n<!--shiro start-->\n      <dependency>\n            <groupId>org.apache.shiro</groupId>\n            <artifactId>shiro-all</artifactId>\n            <version>1.2.3</version>\n       </dependency>\n<!-- shiro end-->\n[/pre]\n\n在 web.xml 加上 Shiro 过滤器配置：\n\n[pre]\n<!-- Shiro 过滤器配置 start -->\n   <filter>\n     <filter-name>shiroFilter</filter-name>\n     <filter-class>\n             org.springframework.web.filter.DelegatingFilterProxy\n         </filter-class>\n     <init-param>\n       <param-name>targetFilterLifecycle</param-name>\n       <param-value>true</param-value>\n     </init-param>\n   </filter>\n   <filter-mapping>\n     <filter-name>shiroFilter</filter-name>\n     <url-pattern>/*</url-pattern>\n   </filter-mapping>\n  <!-- Shiro 过滤器配置 end -->\n[/pre]\n\n编写 shiro 的 ShiroRealm 类：\n\n[pre]\npackage org.muses.jeeplatform.core.security.shiro;\n\nimport javax.annotation.Resource;\n\nimport org.apache.shiro.authc.AuthenticationException;\nimport org.apache.shiro.authc.AuthenticationInfo;\nimport org.apache.shiro.authc.AuthenticationToken;\nimport org.apache.shiro.authc.LockedAccountException;\nimport org.apache.shiro.authc.SimpleAuthenticationInfo;\nimport org.apache.shiro.authc.UnknownAccountException;\nimport org.apache.shiro.authz.AuthorizationInfo;\nimport org.apache.shiro.authz.SimpleAuthorizationInfo;\nimport org.apache.shiro.realm.AuthorizingRealm;\nimport org.apache.shiro.subject.PrincipalCollection;\nimport org.muses.jeeplatform.model.entity.User;\nimport org.muses.jeeplatform.service.UserService;\n\n/**\n * @description 基于 Shiro 框架的权限安全认证和授权\n * @author Nicky\n * @date 2017 年 3 月 12 日\n */\npublic class ShiroRealm extends AuthorizingRealm {\n\n	/**注解引入业务类**/\n	@Resource\n	UserService userService;\n	\n	/**\n	 * 登录信息和用户验证信息验证(non-Javadoc)\n	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)\n	 */\n	@Override\n	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {\n\n		 String username = (String)token.getPrincipal();  				//得到用户名 \n	     String password = new String((char[])token.getCredentials()); 	//得到密码\n	     \n	     User user = userService.findByUsername(username);\n\n	     /**检测是否有此用户 **/\n	     if(user == null){\n	    	 throw new UnknownAccountException();//没有找到账号异常\n	     }\n	     /**检验账号是否被锁定 **/\n	     if(Boolean.TRUE.equals(user.getLocked())){\n	    	 throw new LockedAccountException();//抛出账号锁定异常\n	     }\n	     /**AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配**/\n	     if(null != username && null != password){\n	    	 return new SimpleAuthenticationInfo(username, password, getName());\n	     }else{\n	    	 return null;\n	     }\n	     \n	}\n	\n	/**\n	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)\n	 * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)\n	 */\n	@Override\n	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {\n		String username = (String)pc.getPrimaryPrincipal();\n		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();\n	    authorizationInfo.setRoles(userService.getRoles(username));\n	    authorizationInfo.setStringPermissions(userService.getPermissions(username));\n		System.out.println(\"Shiro 授权\");\n	    return authorizationInfo;\n	}\n	\n	 @Override\n	 public void clearCachedAuthorizationInfo(PrincipalCollection principals) {\n		 super.clearCachedAuthorizationInfo(principals);\n	 }\n\n	 @Override\n	 public void clearCachedAuthenticationInfo(PrincipalCollection principals) {\n	     super.clearCachedAuthenticationInfo(principals);\n	 }\n\n	 @Override\n	 public void clearCache(PrincipalCollection principals) {\n	      super.clearCache(principals);\n	 }\n\n}\n\n[/pre]\n\n在 Spring 框架里集成 Shiro，加入配置\n\n[pre]\n<!--  Shiro start  -->\n		<bean id=\"securityManager\" class=\"org.apache.shiro.web.mgt.DefaultWebSecurityManager\">\n			<property name=\"realm\" ref=\"ShiroRealm\" />\n		</bean>\n		\n		<!-- 项目自定义的 Realm -->\n	    <bean id=\"ShiroRealm\" class=\"org.muses.jeeplatform.core.security.shiro.ShiroRealm\" ></bean>\n		\n		<!-- Shiro Filter -->\n		<bean id=\"shiroFilter\" class=\"org.apache.shiro.spring.web.ShiroFilterFactoryBean\">\n			<property name=\"securityManager\" ref=\"securityManager\" />\n			\n			<property name=\"loginUrl\" value=\"/login\" />\n			\n			<property name=\"successUrl\" value=\"/admin/index\" />\n			\n			<property name=\"unauthorizedUrl\" value=\"/login\" />\n			\n			<property name=\"filterChainDefinitions\">\n				<value>\n				/static/**					= anon\n				/upload/**			    	= anon\n				/plugins/** 				= anon\n	           	/code 						= anon\n	           	/login    	 	       		= anon\n	           	/logincheck					= anon\n	           	/**							= authc\n				</value>\n			</property>\n		</bean>\n	<!--  Shiro end  -->	\n[/pre]\n\n登录验证控制类实现：\n\n[pre]\npackage org.muses.jeeplatform.web.controller;\n\nimport java.util.ArrayList;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport javax.servlet.http.HttpServletRequest;\n\nimport net.sf.json.JSONArray;\nimport net.sf.json.JSONObject;\n\nimport org.apache.shiro.SecurityUtils;\nimport org.apache.shiro.authc.AuthenticationException;\nimport org.apache.shiro.authc.UsernamePasswordToken;\nimport org.apache.shiro.crypto.hash.SimpleHash;\nimport org.apache.shiro.session.Session;\nimport org.apache.shiro.subject.Subject;\nimport org.muses.jeeplatform.core.Constants;\nimport org.muses.jeeplatform.model.entity.Menu;\nimport org.muses.jeeplatform.model.entity.Permission;\nimport org.muses.jeeplatform.model.entity.Role;\nimport org.muses.jeeplatform.model.entity.User;\nimport org.muses.jeeplatform.service.MenuService;\nimport org.muses.jeeplatform.service.UserService;\nimport org.muses.jeeplatform.utils.Tools;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Controller;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.ResponseBody;\nimport org.springframework.web.servlet.ModelAndView;\n\n/**\n * @description 登录操作的控制类，使用 Shiro 框架，做好了登录的权限安全认证，\n * getRemortIP()方法获取用户登录时的 ip 并保存到数据库\n * @author Nicky\n * @date 2017 年 3 月 15 日\n */\n@Controller\npublic class LoginController extends BaseController {\n	\n	@Autowired\n	UserService userService;\n	@Autowired\n	MenuService menuService;\n	\n	/**\n	 * 获取登录用户的 IP\n	 * @throws Exception \n	 */\n	public void getRemortIP(String username)  {  \n		HttpServletRequest request = this.getRequest();\n		Map<String,String> map = new HashMap<String,String>();\n		String ip = \"\";\n		if (request.getHeader(\"x-forwarded-for\") == null) {  \n			ip = request.getRemoteAddr();  \n	    }else{\n	    	ip = request.getHeader(\"x-forwarded-for\");  \n	    }\n		map.put(\"username\", username);\n		map.put(\"loginIp\", ip);\n		 userService.saveIP(map);\n	}  \n	\n	/**\n	 * 访问后台登录页面\n	 * @return\n	 * @throws Exception\n	 */\n	@RequestMapping(value=\"/login\",produces=\"text/html;charset=UTF-8\")\n	public ModelAndView toLogin()throws ClassNotFoundException{\n		ModelAndView mv = this.getModelAndView();\n		mv.setViewName(\"admin/frame/login\");\n		return mv;\n	}\n	\n	/**\n	 * 基于 Shiro 框架的登录验证,页面发送 JSON 请求数据，\n	 * 服务端进行登录验证之后，返回 Json 响应数据，\"success\"表示验证成功\n	 * @param request\n	 * @return\n	 * @throws Exception\n	 */\n	@RequestMapping(value=\"/logincheck\", produces=\"application/json;charset=UTF-8\")\n	@ResponseBody\n	public String loginCheck(HttpServletRequest request)throws AuthenticationException{\n		JSONObject obj = new JSONObject();\n		String errInfo = \"\";//错误信息\n		String logindata[] = request.getParameter(\"LOGINDATA\").split(\",\");\n		if(logindata != null && logindata.length == 3){\n			//获取 Shiro 管理的 Session\n			Subject subject = SecurityUtils.getSubject();\n			Session session = subject.getSession();\n			String codeSession = (String)session.getAttribute(Constants.SESSION_SECURITY_CODE);\n			String code = logindata[2]; \n			/**检测页面验证码是否为空，调用工具类检测**/\n			if(Tools.isEmpty(code)){\n				errInfo = \"nullcode\";\n			}else{\n				String username = logindata[0];\n				String password = logindata[1];\n				if(Tools.isNotEmpty(codeSession) && codeSession.equalsIgnoreCase(code)){\n					//Shiro 框架 SHA 加密\n					String passwordsha = new SimpleHash(\"SHA-1\",username,password).toString();\n					System.out.println(passwordsha);\n					//检测用户名和密码是否正确\n					User user = userService.doLoginCheck(username,passwordsha);\n					if(user != null){\n						if(Boolean.TRUE.equals(user.getLocked())){\n							errInfo = \"locked\";\n						}else{\n							//Shiro 添加会话\n							session.setAttribute(\"username\", username);\n							session.setAttribute(Constants.SESSION_USER, user);\n							//删除验证码 Session\n							session.removeAttribute(Constants.SESSION_SECURITY_CODE);\n							//保存登录 IP\n							getRemortIP(username);\n							/**Shiro 加入身份验证**/\n							Subject sub = SecurityUtils.getSubject();\n							UsernamePasswordToken token = new UsernamePasswordToken(username,password);\n							sub.login(token);\n						}\n					}else{\n						//账号或者密码错误\n						errInfo = \"uerror\";\n					}\n					if(Tools.isEmpty(errInfo)){\n						errInfo = \"success\";\n					}\n				}else{\n					//缺少参数\n					errInfo=\"codeerror\";\n				}\n			}\n		}\n		obj.put(\"result\", errInfo);\n		return obj.toString();\n	}\n		\n	/**\n	 * 后台管理系统主页\n	 * @return\n	 * @throws Exception\n	 */\n	@RequestMapping(value=\"/admin/index\")\n	public ModelAndView toMain() throws AuthenticationException{\n		ModelAndView mv = this.getModelAndView();\n		/**获取 Shiro 管理的 Session**/\n		Subject subject = SecurityUtils.getSubject();\n		Session session = subject.getSession();\n		User user = (User)session.getAttribute(Constants.SESSION_USER);\n		\n		if(user != null){\n			...//业务实现\n		}else{\n			//会话失效，返回登录界面\n			mv.setViewName(\"admin/frame/login\");\n		}\n		mv.setViewName(\"admin/frame/index\");\n		return mv;\n	}\n	\n	/**\n	 * 注销登录\n	 * @return\n	 */\n	@RequestMapping(value=\"/logout\")\n	public ModelAndView logout(){\n		ModelAndView mv = this.getModelAndView();\n		/**Shiro 管理 Session**/\n		Subject sub = SecurityUtils.getSubject();\n		Session session = sub.getSession();\n		session.removeAttribute(Constants.SESSION_USER);\n		session.removeAttribute(Constants.SESSION_SECURITY_CODE);\n		/**Shiro 销毁登录**/\n		Subject subject = SecurityUtils.getSubject();\n		subject.logout();\n		/**返回后台系统登录界面**/\n		mv.setViewName(\"admin/frame/login\");\n		return mv;\n	}\n\n\n}\n[/pre]\n\n前端 Ajax 和 JQeury 校验实现：\n\n[pre]\n/**客户端校验**/\n    function checkValidity() {\n\n        if ($(\"#username\").val() == \"\") {\n\n            $(\"#username\").tips({\n                side : 2,\n                msg : \'用户名不得为空\',\n                bg : \'#AE81FF\',\n                time : 3\n            });\n\n            $(\"#username\").focus();\n            return false;\n        }\n\n        if ($(\"#password\").val() == \"\") {\n            $(\"#password\").tips({\n                side : 2,\n                msg : \'密码不得为空\',\n                bg : \'#AE81FF\',\n                time : 3\n            });\n\n            $(\"#password\").focus();\n            return false;\n        }\n        if ($(\"#code\").val() == \"\") {\n\n            $(\"#code\").tips({\n                side : 1,\n                msg : \'验证码不得为空\',\n                bg : \'#AE81FF\',\n                time : 3\n            });\n\n            $(\"#code\").focus();\n            return false;\n        }\n\n        return true;\n    }\n\n    /**服务器校验**/\n    function loginCheck(){\n        if(checkValidity()){\n            var username = $(\"#username\").val();\n            var password = $(\"#password\").val();\n            var code = username+\",\"+password+\",\"+$(\"#code\").val();\n            $.ajax({\n                type: \"POST\",//请求方式为 POST\n                url: \'logincheck\',//检验 url\n                data: {LOGINDATA:code,tm:new Date().getTime()},//请求数据\n                dataType:\'json\',//数据类型为 JSON 类型\n                cache: false,//关闭缓存\n                success: function(data){//响应成功\n                    if(\"success\" == data.result){\n                        $(\"#login\").tips({\n                            side : 1,\n                            msg : \'正在登录 , 请稍后 ...\',\n                            bg : \'#68B500\',\n                            time : 10\n                        });\n                        window.location.href=\"admin/index\";\n                    }else if(\"uerror\" == data.result){\n                        $(\"#username\").tips({\n                            side : 1,\n                            msg : \"用户名或密码有误\",\n                            bg : \'#FF5080\',\n                            time : 15\n                        });\n                        $(\"#username\").focus();\n                    }else if(\"codeerror\" == data.result){\n                        $(\"#code\").tips({\n                            side : 1,\n                            msg : \"验证码输入有误\",\n                            bg : \'#FF5080\',\n                            time : 15\n                        });\n                        $(\"#code\").focus();\n                    }else if(\"locked\" == data.result){\n                        alert(\'您的账号被锁定了，呜呜\');\n                    }else{\n                        $(\"#username\").tips({\n                            side : 1,\n                            msg : \"缺少参数\",\n                            bg : \'#FF5080\',\n                            time : 15\n                        });\n                        $(\"#username\").focus();\n                    }\n                }\n            });\n        }\n    }\n[/pre]\n\nimg[http://img.blog.csdn.net/20171022013539943?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast] \n\n登录成功，Session 会话过期，需要重新登录，保证系统安全性 \n\nimg[http://img.blog.csdn.net/20171022013623162?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast] \n\n本博客只提供基于 Shiro 的登录验证实现，具体代码可以去我的 github 下载：  a(https://github.com/u014427391/jeeplatform)[https://github.com/u014427391/jeeplatform]  \n欢迎 star', '20', '0', '2017-12-25 18:13:56', '1', '0', '7', '0', '0', '0');
INSERT INTO `post` VALUES ('25', '使用 Nginx 作为 websocket 的负载均衡代理，能支持多少 websocket 并发连接', '19', '一直有一个疑惑的地方，在此发帖请教各位。使用 Nginx 作为 websocket 的负载均衡代理，能支持多少 websocket 并发连接？ 由于 Linux 服务器有 65535 的文件描述符的限制，nginx 做代理转发的时候，理论最多使用 65535 个文件描述符，这样，单台 nginx 是否就是理论最多支持 65535 个 websocket 连接呢？', '20', '0', '2017-12-25 18:14:53', '1', '0', '2', '0', '0', '0');
INSERT INTO `post` VALUES ('26', '吐槽一下国内注册域名太黑暗了吧', '24', 'img[http://upload-images.jianshu.io/upload_images/6876363-dd76c4a402e7de46.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240] \n\n上周五在万网看到了一个域名还在删除期就在万网预定了一下。 还没到删除的日期，就有人发邮件过来了，更可怕的是知道我的姓名，知道我的邮箱。这个也太黑暗了吧。对万网的好感全无，太恶心了', '20', '0', '2017-12-25 18:15:52', '1', '0', '3', '0', '0', '0');
INSERT INTO `post` VALUES ('27', '深圳 2018 去那跨年比较好', '28', '有没有在推荐的呢，最好是要比较浪漫的地方', '20', '0', '2017-12-25 18:16:16', '1', '0', '8', '0', '0', '0');
INSERT INTO `post` VALUES ('28', '办公室里的扫地僧', '9', 'img[http://wx4.sinaimg.cn/mw600/6f52f1dcgy1fmoeflx18sj20di0ggjua.jpg] ', '20', '0', '2017-12-25 18:17:06', '1', '2', '15', '0', '0', '0');
INSERT INTO `post` VALUES ('29', '这个越南人的主页是我见过的最有特色的个人主页', '19', ' a(http://dinhquangtrung.net)[http://dinhquangtrung.net] \n\n真有想法能折腾。\n\n我每一个高显的位置都点一下结果发现上当了。', '20', '0', '2017-12-25 18:17:42', '1', '27', '137', '0', '1', '1');
INSERT INTO `post` VALUES ('30', '泰国~', '24', 'img[http://player.youku.com/player.php/sid/XMzI1MDA2NDc4NA==/v.swf] ', '20', '1', '2017-12-26 13:49:19', '1', '0', '2', '0', '0', '0');
INSERT INTO `post` VALUES ('31', '身上静电太大怎么办', '28', '拿着钥匙碰暖气，啪得一声眼看着一道电弧从钥匙根部打到手上', '20', '0', '2017-12-27 10:58:18', '11', '0', '0', '0', '0', '0');
INSERT INTO `post` VALUES ('32', '有什么食疗或药品可以明目的？最近用眼过度视力下降了。', '9', '最近老是睡前躺着玩手机导致视力下降，于是赶紧戒掉了。\n\n同时想要保养下眼睛，应该吃些什么有效？\n\n最好是从个人经验出发。', '20', '0', '2017-12-27 10:59:24', '12', '1', '7', '0', '0', '0');
INSERT INTO `post` VALUES ('33', '你们收了这么多钱，能不能给建个骑行专用道啊？', '19', '早就听闻有小伙伴早上上班骑车被交警抓到并罚款，今天终于轮到我了\n\n他们真的很鸡贼，藏在路口拐角处，趁你专心转弯冒出两个协警抓住你，让你去交罚款\n\n非主干道，第一次就直接 50，罚钱我认，但好歹你们也得帮助解决一下问题吧，到底还让不让骑友上路了？', '20', '0', '2017-12-27 11:00:51', '13', '12', '72', '0', '1', '0');
INSERT INTO `post` VALUES ('34', '什么，不想看广告？那我就大发慈悲让你开个会员吧', '24', '想到国产 APP 越来越多的首屏广告， 用户在他们眼里就是融资的一个数字而已\n\n“我不敢让你一口吃下去，但是我会一小口一小口喂你吃”', '20', '0', '2017-12-27 11:02:21', '14', '0', '2', '0', '0', '0');
INSERT INTO `post` VALUES ('35', 'Debian 下充放一次电，电池损耗增加一些', '10', '前段时间发现最近笔记本电池损耗比较大，也没有在意，结果这两天用电池多，发现 Debian 下每一次充电完成电池损耗都会增加，这两天充放两次已经从 8%增加到 22%了。简直了\n\n系统是 Debian 9，Stretch-backports 安装的 4.13 的内核，安装了 tlp，电源管理是 xfce 的组件。\n\n现在滚回 Windows 了，看看能不能校正回来', '20', '0', '2017-12-27 11:02:49', '14', '0', '3', '0', '0', '0');
INSERT INTO `post` VALUES ('36', '被又拍云坑惨了（为什么开了 CDN 比不开还慢？）', '25', '开 CDN 的时候，网站首页打开速度为 1s+。\n\n关闭 CDN 后，首页打开速度为 300ms 左右\n\n这是 CDN 加速？明明是 CDN 减速好吧。\n\n这里我仅仅是关闭了动态资源的加速。静态资源还是保留的。\n\n其实想想这个道理也是明白的，动态加速时，CDN 加速是多了一个环节。\n\n普通模式是，用户请求 -> 网站服务器。\n\nCDN 动态加速的模式是，用户请求 -> CDN -> 网站服务器。（静态加速可以增加访问速度，这个我同意）\n\n既然是这样，动态资源还有用 CDN 的必要吗？在国内备了案，服务器在国内的网站，应该全国区域访问速度都差不多吧？', '20', '0', '2017-12-27 11:08:00', '15', '2', '9', '0', '0', '0');
INSERT INTO `post` VALUES ('37', 'qq 音乐绿钻会员 wifi 环境下播放没有自动选择 sq 无损音质', '10', '设置里已经选择默认 sq 无损了。。\n并不是每首歌都这样，出问题的歌哪怕手动选择了 sq 无损切走再切回来又变成普通音质了。。\n第一次用 qq 音乐，受不到网易云越来越多的灰色，ios 7.9.5 版本', '20', '0', '2017-12-27 13:56:18', '1', '0', '5', '0', '0', '0');
INSERT INTO `post` VALUES ('40', '我不开玩笑，2018 年你还需要学习 JavaScript ', '10', 'JavaScript 是 web 开发语言。看看网上点击量超过 1000 万受欢迎的网页，将近 95% 的是用 JavaScript 开发的。\n\n我们再来看 2018 最具就业前景的 7 大编程语言。JavaScript 位居第三。\n\n a(https://static.oschina.net/uploads/space/2017/1226/182820_53se_3703517.png)[https://static.oschina.net/uploads/space/2017/1226/182820_53se_3703517.png] \n\n像谷歌，火狐和 IE 等浏览器都支持 JavaScript 语言。所以，你决定现在学习这门语言，你可以很容易找到工作。但是事物都有两面性，也有人反对这种语言的学习。而且这与 JavaScript 语言本身没有太大的关系：是因为有这么多的 JavaScript 框架，初学者不用学习基本的 JavaScript 编程语言，直接学习如何实现框架就行。\n\n框架非常棒，因为它们提供了随时可用的易于阅读和调试的代码。但是，由于这些框架提供了一个更简单的方法来将代码放在一起，新手程序员不能将 JavaScript 的基础学的扎实，让那些经验丰富的开发人员感到恼火。\n\n在美国，JavaScript 开发者的平均工资是 72,500 美元，而经验丰富的开发者可以轻松赚取超过 10 万美元的年薪。 \n\n什么是 JavaScript，是什么让它这么受欢迎？\n\n要了解为什么 JavaScript 变得如此受欢迎，我们首先要看看另外两个紧密相关的 Web 语言，即 HTML 和CSS。\n\nHTML 让浏览器渲染什么样的内容。是文本，连接还是视频？都是 HTML 负责渲染的。\n\n另外，CSS 则是为网页添加颜色和样式的。如果 HTML 是网页的骨架，那么 CSS 就是让 HTML 看起来更加自然的肉体和皮肤。\n\n但是，虽然 HTML 和 CSS 都适合构建和设计一个网页，它们不能让网页动态的显示。比如用户填写表单或者点击一个选项的时候，这个请求就会被发送至服务器，页面会重新刷新。这就是 JavaScript 所做的。\n\nJavaScript 使网页活跃起来。发布状态更新时，网页无需重新加载。用户发送的所有请求都在自己的计算机上处理。\n\n这就是 JavaScript 如此受欢迎的原因，这就是 JavaScript 值得前端开发人员学习的原因。\n\n它支持客户端处理，减少了服务器端的负载，大大提高了处理事务能力。此外，它还支持动画的渲染，可以使网页更加生动。\n\nJavaScript 还值得学习吗？\n\n这是必然的， 只要有人和网站互动，前端开发人员的对 JavaScript 需求就会一直存在。\n\n虽然像 WordPress 和 Joomla 这样的内容管理系统（CMS）很受欢迎，但它们不会让 JavaScript 过时。\n\n当然，Google，微软，Firefox和其他浏览器正试图想出更好的技术来取代 JavaScript，但是 JavaScript 很难在短时间内被取代。\n\n因为 JavaScript 不仅可以对用户行为做出响应，而且也是编写跨平台应用程序的好语言。随着 Node.js 的出现，程序员现在可以编写复杂的服务器端代码。\n\n这里有一些实用的方法可以让你的 JavaScript 知识得到很好的使用：\n\n可以创建交互式表单来检测用户输入内容时是否有错误\n\n可以创建一个搜索框，以响应网站上的用户查询（如Google）\n\n可以创建需要不断更新的信息（例如公司股票价格或倒数计时器）的网页\n\n可以将HTML每个元素准确定位到您想要的位置; 就像定位菜单项或图像一样。\n\n可以纯粹为了娱乐而使用 JavaScript，或者添加流畅的动画，使网页更加高级和专业。\n\n而且你可以肯定，大多数大公司不会很快使用 WordPress。而且，JavaScript 及其框架具有无与伦比的灵活性。\n\n但这并不是说 JavaScript 没有缺点。JavaScript 最大的问题就是其安全性。一旦页面重新加载，这些脚本就会不经过用户许可就运行。虽然这是一件好事，但在许多情况下，可能会导致您的 Web 浏览器崩溃。而不用 JavaScript 是不可行的，因为许多重要的网站，包括谷歌，Facebook 和 Quora 不能没有 JavaScript而运行，至少现在不能没有 JavaScript 。\n\n在 2018 年及以后学习 JavaScript\n\nJavaScript 是一个非常有趣，多功能和重要的 web 开发语言，它可以让网站变得更加活跃。不仅如此，它还很容易学习，越深入了解它，就会越多地了解它的所有惊人的创造性。 \n\n你可以创建网页游戏，创建跨平台的应用程序，甚至建立令人难以置信互动网站。\n\n另外，学习了这门技能意味着你多了一个选择—做一个朝九晚五的的自由职业者，编程可以在任何地方进行。许多软件公司可以远程工作，可以拥有高新和其他的福利。\n\n如果你对自己的工作充满激情，对工作有真正的兴趣。这样的话，在 2018 年学习 JavaScript 并成为前端开发者还是不错的。', '20', '0', '2017-12-27 14:16:12', '1', '0', '14', '0', '0', '1');
INSERT INTO `post` VALUES ('41', '你好', '24', '', '20', '0', '2018-01-10 17:03:36', '1', '0', '4', '0', '0', '0');
INSERT INTO `post` VALUES ('43', '阿大声道是当前我额', '9', '前言\n最近一段时间一直再看mysql binlog相关的内容，也整理了几篇相关的文章，对mysql的事件以及通讯协议在理论上有了一个大概的了解，但是缺少实战；本文的目的就是从实战出发，了解binlog解析的整个过程。\n\n解析思路\n把binlog的解析过程大致分为以下几个步骤：\n1.服务器启动首先获取上一次解析成功的位置（实例中存储在本地文件中）；\n2.和mysql服务器建立连接；\n3.接受mysql发送来的binlog事件；\n4.对不同的binlog事件进行解析；\n5.将数据进行存储（实例中仅在日志中打印）；\n6.存储成功后，定时记录Binaly Log位置。\n\n关于binlog相关的配置可以参考系列文章，里面有详解的介绍，下面对步骤进行详细的介绍；\n\n1.服务器启动首先获取上一次解析成功的位置（实例中存储在本地文件中）\nbinlog的位置信息存储在文件namePosition，有更新也同样更新到namePosition中，部分代码如下', '20', '0', '2018-01-10 17:18:07', '1', '0', '4', '0', '0', '0');
INSERT INTO `post` VALUES ('45', '阿里云高性能', '11', '哈哈哈', '20', '0', '2018-01-10 17:25:40', '1', '0', '10', '0', '0', '0');
INSERT INTO `post` VALUES ('46', 'hello world', '9', '你好 世界', '20', '0', '2018-01-12 11:43:42', '1', '0', '2', '0', '0', '0');

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` varchar(255) DEFAULT NULL,
  `accept` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('1', '16', '1', '2017-12-23 11:01:23', '1111', '0');
INSERT INTO `reply` VALUES ('2', '16', '1', '2017-12-25 14:26:56', '厉害了我的哥face[嘻嘻] ', '0');
INSERT INTO `reply` VALUES ('3', '16', '1', '2017-12-25 14:32:53', '1', '0');
INSERT INTO `reply` VALUES ('4', '16', '1', '2017-12-25 14:32:56', '1', '0');
INSERT INTO `reply` VALUES ('5', '16', '1', '2017-12-25 14:32:57', '1', '0');
INSERT INTO `reply` VALUES ('6', '16', '1', '2017-12-25 14:33:00', '1', '0');
INSERT INTO `reply` VALUES ('7', '16', '1', '2017-12-25 14:33:01', '1', '0');
INSERT INTO `reply` VALUES ('8', '16', '1', '2017-12-25 14:33:02', '1', '0');
INSERT INTO `reply` VALUES ('9', '16', '1', '2017-12-25 14:33:03', '1', '0');
INSERT INTO `reply` VALUES ('10', '16', '1', '2017-12-25 14:33:04', '1', '0');
INSERT INTO `reply` VALUES ('11', '16', '1', '2017-12-25 14:33:06', '1', '0');
INSERT INTO `reply` VALUES ('12', '16', '1', '2017-12-25 14:33:07', '1', '0');
INSERT INTO `reply` VALUES ('13', '16', '1', '2017-12-25 14:33:10', '1', '0');
INSERT INTO `reply` VALUES ('14', '16', '1', '2017-12-25 14:33:13', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('15', '16', '1', '2017-12-25 14:33:15', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('16', '16', '1', '2017-12-25 14:33:16', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('17', '16', '1', '2017-12-25 14:33:17', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('18', '16', '1', '2017-12-25 14:33:19', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('19', '16', '1', '2017-12-25 14:33:20', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('20', '16', '1', '2017-12-25 14:33:23', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('21', '16', '1', '2017-12-25 14:33:24', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('22', '16', '1', '2017-12-25 14:33:25', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('23', '16', '1', '2017-12-25 14:33:26', 'face[怒] ', '0');
INSERT INTO `reply` VALUES ('24', '17', '1', '2017-12-25 15:14:48', '你好在吗我的啊face[偷笑] ', '0');
INSERT INTO `reply` VALUES ('25', '17', '1', '2017-12-25 15:15:40', 'face[猪头] ', '0');
INSERT INTO `reply` VALUES ('26', '17', '1', '2017-12-25 15:16:31', 'face[哈哈] ', '0');
INSERT INTO `reply` VALUES ('32', '17', '1', '2017-12-25 16:22:33', 'img[http://oih7sazbd.bkt.clouddn.com/Fvb7JPJkVnaPNWUb9m35ZIE56kCd] ', '0');
INSERT INTO `reply` VALUES ('33', '29', '1', '2017-12-26 10:41:16', 'ahh', '0');
INSERT INTO `reply` VALUES ('34', '29', '1', '2017-12-26 11:01:35', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 \nface[good] face[good] face[good] ', '0');
INSERT INTO `reply` VALUES ('35', '29', '1', '2017-12-26 11:02:46', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 \n[good] [good] [good] ', '0');
INSERT INTO `reply` VALUES ('42', '29', '1', '2017-12-26 11:49:32', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 ', '0');
INSERT INTO `reply` VALUES ('43', '29', '1', '2017-12-26 11:51:22', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 ', '0');
INSERT INTO `reply` VALUES ('44', '29', '1', '2017-12-26 11:52:44', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 \n[good] [good] [good] ', '0');
INSERT INTO `reply` VALUES ('45', '29', '1', '2017-12-26 11:53:56', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 \n[good] [good] [good] ', '0');
INSERT INTO `reply` VALUES ('46', '29', '1', '2017-12-26 11:54:44', 'face[good] ', '0');
INSERT INTO `reply` VALUES ('47', '29', '1', '2017-12-26 11:58:39', '@超人 @小王八123 @张三的小利斯 @你在哪里呢 \n[good] [good] [good] ', '0');
INSERT INTO `reply` VALUES ('48', '29', '1', '2017-12-26 14:39:39', '@超人 hhaha', '0');
INSERT INTO `reply` VALUES ('49', '29', '1', '2017-12-26 14:39:49', '@超人 @嗯', '0');
INSERT INTO `reply` VALUES ('50', '29', '1', '2017-12-26 14:40:23', '@超人 啊', '0');
INSERT INTO `reply` VALUES ('51', '29', '1', '2017-12-26 14:40:26', '@超人 嗯', '0');
INSERT INTO `reply` VALUES ('52', '29', '1', '2017-12-26 14:40:29', '@超人 额鹅鹅鹅', '0');
INSERT INTO `reply` VALUES ('53', '29', '1', '2017-12-26 14:40:33', '@超人 吞吞吐吐', '0');
INSERT INTO `reply` VALUES ('54', '29', '1', '2017-12-26 14:43:47', '@超人 @face[嘻嘻] ', '0');
INSERT INTO `reply` VALUES ('55', '29', '1', '2017-12-26 14:48:57', '@超人 分飞燕', '0');
INSERT INTO `reply` VALUES ('56', '29', '1', '2017-12-26 14:53:28', '@超人 face[疑问] ', '0');
INSERT INTO `reply` VALUES ('57', '29', '1', '2017-12-26 14:55:09', '@超人 face[怒] ', '0');
INSERT INTO `reply` VALUES ('58', '29', '1', '2017-12-26 14:55:11', 'face[太开心] ', '0');
INSERT INTO `reply` VALUES ('59', '29', '1', '2017-12-26 14:55:14', 'face[奥特曼] ', '0');
INSERT INTO `reply` VALUES ('60', '29', '1', '2017-12-26 14:55:17', 'face[阴险] ', '0');
INSERT INTO `reply` VALUES ('61', '29', '1', '2017-12-26 14:55:20', '啊哈哈哈哈', '0');
INSERT INTO `reply` VALUES ('62', '29', '1', '2017-12-26 14:55:45', '@超人 a啊', '0');
INSERT INTO `reply` VALUES ('63', '29', '1', '2017-12-26 14:55:50', '热', '0');
INSERT INTO `reply` VALUES ('64', '29', '1', '2017-12-26 14:55:58', '@超人 @柔柔弱弱', '0');
INSERT INTO `reply` VALUES ('65', '29', '10', '2017-12-26 15:38:48', '@lyusantu 牛逼了 @superman 能@自己吗', '0');
INSERT INTO `reply` VALUES ('67', '28', '1', '2017-12-27 10:26:36', 'img[http://oih7sazbd.bkt.clouddn.com/Fvb7JPJkVnaPNWUb9m35ZIE56kCd] ', '0');
INSERT INTO `reply` VALUES ('68', '28', '11', '2017-12-27 10:58:38', 'face[给力] ', '0');
INSERT INTO `reply` VALUES ('69', '32', '12', '2017-12-27 10:59:35', 'face[威武] face[威武] face[威武] ', '0');
INSERT INTO `reply` VALUES ('70', '33', '14', '2017-12-27 11:01:27', '向 HK 学习', '0');
INSERT INTO `reply` VALUES ('71', '36', '15', '2017-12-27 11:08:27', '不听', '0');
INSERT INTO `reply` VALUES ('72', '36', '15', '2017-12-27 11:08:30', 'face[ok] ', '0');
INSERT INTO `reply` VALUES ('73', '33', '1', '2018-01-04 14:11:18', 'img[http://oih7sazbd.bkt.clouddn.com/FkOblv1cPBb6a-_DeKTJKTt0Z375] ', '0');
INSERT INTO `reply` VALUES ('74', '33', '1', '2018-01-04 14:32:55', 'img[http://oih7sazbd.bkt.clouddn.com/Fpx7nrzohBmfr5DaJJ6iNtZwkmzt] \n', '0');
INSERT INTO `reply` VALUES ('75', '33', '1', '2018-01-04 14:32:59', 'img[http://oih7sazbd.bkt.clouddn.com/Fo_m6kMUvvj4mxeYvEEILAoy5J4E] \n', '0');
INSERT INTO `reply` VALUES ('76', '33', '1', '2018-01-04 14:33:02', 'img[http://oih7sazbd.bkt.clouddn.com/Fu8ehbXRVp729grkk6MVWCg0j5Vv] \n', '0');
INSERT INTO `reply` VALUES ('77', '33', '1', '2018-01-04 14:33:05', 'img[http://oih7sazbd.bkt.clouddn.com/FiPLwX9HaD1sEW0jKqH0lteEzdRw] ', '0');
INSERT INTO `reply` VALUES ('78', '33', '1', '2018-01-04 14:46:26', 'img[http://oih7sazbd.bkt.clouddn.com/FhxxhQyfi3cISdwY-4C4kZSHQEex] ', '0');
INSERT INTO `reply` VALUES ('79', '33', '1', '2018-01-04 15:05:34', 'img[http://oih7sazbd.bkt.clouddn.com/Fl1Q3avi_mqJZXikKxjfPeFkW3nj] ', '0');
INSERT INTO `reply` VALUES ('80', '33', '1', '2018-01-04 15:10:04', 'img[http://oih7sazbd.bkt.clouddn.com/FsQ5JOGzHQxudE1LJT5aqXZ2l2Uu] ', '0');
INSERT INTO `reply` VALUES ('81', '33', '12', '2018-01-05 16:15:45', 'img[http://oih7sazbd.bkt.clouddn.com/FsBASabeBPcBwMSoOHlcONb-jRsT] ', '0');
INSERT INTO `reply` VALUES ('82', '33', '12', '2018-01-05 16:17:00', 'img[http://oih7sazbd.bkt.clouddn.com/FlNnkOv4CsnUal_mZdb2F5Y2rq4x] ', '0');
INSERT INTO `reply` VALUES ('83', '33', '13', '2018-01-05 16:57:07', 'img[http://oih7sazbd.bkt.clouddn.com/FrEsoLuROkfMlgzP7scGvRPg1Fkf] ', '0');

-- ----------------------------
-- Table structure for `sign`
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('40', '1', '2018-01-09 10:11:33', '2018-01-10 11:02:08');
INSERT INTO `sign` VALUES ('41', '2', '2017-12-25 15:18:10', '2017-12-25 15:18:10');
INSERT INTO `sign` VALUES ('42', '10', '2017-12-26 15:38:28', '2017-12-26 15:38:28');
INSERT INTO `sign` VALUES ('43', '13', '2017-12-27 11:00:54', '2017-12-27 11:00:54');
INSERT INTO `sign` VALUES ('44', '15', '2017-12-27 11:03:04', '2017-12-27 11:03:04');

-- ----------------------------
-- Table structure for `sponsor`
-- ----------------------------
DROP TABLE IF EXISTS `sponsor`;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `desc` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `color` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sponsor
-- ----------------------------
INSERT INTO `sponsor` VALUES ('1', 'eazy社区', '做最好的社区', '/', '2017-12-26 16:32:39', '#5FB878;', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL,
  `balance` bigint(20) NOT NULL COMMENT '账户余额',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `reg_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `status` int(11) DEFAULT NULL COMMENT '状态 [1:"正常",0:"未验证",2:"禁言",3:"封禁"]',
  `auth` varchar(255) DEFAULT NULL,
  `vip` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `active_code` varchar(255) DEFAULT NULL,
  `read_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '352050321@qq.com', 'lyusantu', '9cbf8a4dcb8e30682b927f352d6559a0', '2147483214', 'http://oih7sazbd.bkt.clouddn.com/FivibFbr6k8GqJBdZBea56zq0S4X', '0', '随便', '2018-01-12 11:43:41', '天安门', '1', 'eazy作者', '9', 'admin', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('10', '52781380@qq.com', 'superman', '9cbf8a4dcb8e30682b927f352d6559a0', '105', '/res/images/avatar/11.jpg', '0', null, '2018-01-09 10:33:35', null, '1', null, '0', 'user', 'F2D6FA858D8AC8AA89878D6F2D978AD11514273872960', '0');
INSERT INTO `user` VALUES ('11', '12345@qq.com', 'tiger', '9cbf8a4dcb8e30682b927f352d6559a0', '80', '/res/images/avatar/5.jpg', '0', null, '2018-01-09 10:33:36', null, '1', null, '0', 'user', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('12', '123456@qq.com', 'ryan', '9cbf8a4dcb8e30682b927f352d6559a0', '80', '/res/images/avatar/1.jpg', '0', null, '2018-01-09 10:33:37', null, '1', null, '0', 'user', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('13', '1234567@qq.com', 'bourne', '9cbf8a4dcb8e30682b927f352d6559a0', '85', '/res/images/avatar/2.jpg', '0', null, '2018-01-09 10:33:37', null, '1', null, '0', 'user', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('14', '12345678@qq.com', 'steven', '9cbf8a4dcb8e30682b927f352d6559a0', '60', '/res/images/avatar/3.jpg', '0', null, '2018-01-09 10:33:38', null, '1', null, '0', 'user', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('15', '123456789@qq.com', 'jack', '9cbf8a4dcb8e30682b927f352d6559a0', '85', '/res/images/avatar/4.jpg', '0', null, '2018-01-09 10:33:39', null, '1', null, '0', 'user', 'C434AA33570E628123B677EDE16DF4ED1514342445053', '0');
INSERT INTO `user` VALUES ('17', '10010@qq.com', '10010', 'e10adc3949ba59abbe56e057f20f883e', '100', '/res/images/avatar/0.jpg', '0', null, '2018-01-09 11:14:51', null, '0', null, '0', 'user', '1BBF4702077F13E52CDCEB560859ACBF1515467691312', '0');

-- ----------------------------
-- Table structure for `verify`
-- ----------------------------
DROP TABLE IF EXISTS `verify`;
CREATE TABLE `verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verify
-- ----------------------------
INSERT INTO `verify` VALUES ('1', '请输入so eazy', 'so eazy');
INSERT INTO `verify` VALUES ('2', '2 + 2 * 2 = ?', '6');
INSERT INTO `verify` VALUES ('3', '12月25日是什么节日？', '圣诞节');
INSERT INTO `verify` VALUES ('4', '中国的首都是哪座城市？', '北京');
INSERT INTO `verify` VALUES ('5', 'Java和JavaScript有关系吗', '没关系');
INSERT INTO `verify` VALUES ('6', '\"100\" > \"2\" 的结果是 true 还是 false？', 'false');
