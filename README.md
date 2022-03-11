## 如果本项目对你有帮助，就请你Star一下吧!!

### 项目地址

GitHub地址：[a6678696/SecondHandTrade: 校园二手交易平台 (github.com)](https://github.com/a6678696/SecondHandTrade)

### 使用的技术

本项目核心技术采用Spring Boot+Mybatis-Plus；开发工具 idea；数据库MySQL5.6；模版引擎采用的是Thymeleaf；在线编辑器CKEditor；[基于 JavaMail 实现用 QQ 邮箱发送邮件](https://blog.zoutl.cn/64.html)；前台界面采用了Bootstrap4框架；后台界面采用了jQuery EasyUI框架；Redis存储用户购物车信息；Gson处理JSON数据；Quartz定时任务；

### 功能介绍

### 功能介绍

#### 普通用户

1. 添加商品到购物车、预订商品
2. 前台登录、通过邮箱接受验证码注册和找回密码
3. 查看和修改个人信息
4. 发布商品和管理自己发布的商品（修改、删除、查询、上架、下架、完成交易、取消商品被预订）
5. 管理自己的联系方式（添加、修改、删除、查询）
6. 查看预订记录以及取消预订
7. 查看自己的消息
8. 给管理员留言以及管理自己的留言（查询、修改、删除）

#### 管理员

1. 管理普通用户（添加、修改、删除、查询、封禁与取消封禁）
2. 商品类别管理（添加、修改、删除、查询）
3. 商品管理（查询、删除、审核商品、推荐商品）
4. 管理用户联系方式（修改、删除、查询）
5. 管理用户留言（答复用户的留言、删除、查询）
6. 管理首页的轮播图（添加、修改、删除、查询）
7. 管理公告（添加、修改、删除、查询）
8. 管理预订记录（查询、删除）
9. 管理用户消息（查询、删除）
10. 安全退出后台

#### 系统

1. 每隔60分钟将推荐时间过期的商品的推荐状态设置为不推荐

### 图片展示

#### 前台

##### 首页

![image-20220119003408138](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003408138.png)

![image-20220119003433066](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003433066.png)

![image-20220119003452725](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003452725.png)

##### 商品分类

![image-20220119003529165](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003529165.png)

##### 查看用户求购

![image-20220119003557519](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003557519.png)

##### 搜索商品

![image-20220119003624983](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003624983.png)

##### 查看商品详情

![image-20220119003653798](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003653798.png)

##### 登录

![image-20220119003714458](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003714458.png)

##### 注册

![image-20220119003733398](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003733398.png)

##### 找回密码

![image-20220119003801336](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003801336.png)

##### 查看公告详情

![image-20220119003832637](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003832637.png)

##### 个人中心

![image-20220119003907012](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003907012.png)

##### 查看个人信息

![image-20220119003931967](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003931967.png)

##### 修改个人信息

![image-20220119003950116](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119003950116.png)

##### 管理自己的联系方式

![image-20220119004014868](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004014868.png)

##### 发布商品

![image-20220119004034074](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004034074.png)

##### 管理我的商品

![image-20220119004059007](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004059007.png)

##### 查看自己的消息

![image-20220119004125370](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004125370.png)

##### 留言管理

![image-20220119004145010](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004145010.png)

##### 给管理员留言

![image-20220119004204044](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004204044.png)

##### 我的购物车

![image-20220119004254592](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004254592.png)

##### 注销登录

![image-20220119004312028](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004312028.png)

#### 后台

##### 首页

![image-20220119004452355](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004452355.png)

##### 用户管理

![image-20220119004522381](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004522381.png)

##### 商品类别管理

![image-20220119004537017](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004537017.png)

##### 商品管理

![image-20220119004553399](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004553399.png)

##### 用户联系方式管理

![image-20220119004631959](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004631959.png)

##### 用户留言管理

![image-20220119004656637](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004656637.png)

##### 轮播图管理

![image-20220119004715418](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004715418.png)

##### 公告管理

![image-20220119004735171](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004735171.png)

##### 预订记录管理

![image-20220119004754793](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004754793.png)

##### 用户消息管理

![image-20220119004815923](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004815923.png)

##### 安全退出登录

![image-20220119004835280](https://image.zoutl.cn/hexo-blog/blogImage/image-20220119004835280.png)

