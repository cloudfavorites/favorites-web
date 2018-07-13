![](http://favorites.ren/img/icon.ico)
![](http://favorites.ren/index/img/profile.png)

云收藏 - 让收藏更简单
=========================

![Spring Boot 2.0](https://img.shields.io/badge/Spring%20Boot-2.0-brightgreen.svg)
![Thymeleaf 3.0](https://img.shields.io/badge/Thymeleaf-3.0-yellow.svg)
![Mysql 5.6](https://img.shields.io/badge/Mysql-5.6-blue.svg)
![JDK 1.8](https://img.shields.io/badge/JDK-1.8-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.5.0-yellowgreen.svg)
![license](https://img.shields.io/badge/license-MPL--2.0-blue.svg)
 

云收藏是一个使用 Spring Boot 构建的开源网站，可以让用户在线随时随地收藏的一个网站，在网站上分类整理收藏的网站或者文章，可以作为稍后阅读的一个临时存放。作为一个开放开源的软件，可以让用户从浏览器将收藏夹内容导入到云收藏，也支持随时将云收藏收集的文章导出去做备份。 

网站可以查看别人公开收藏的内容，了解同行业内的大拿都在学习什么，方便学习、整理、检索，根据共同的收藏内容我们可以帮您找到**最熟悉的陌生人** :)。


[官方主页](http://favorites.ren) | [github地址](https://github.com/cloudfavorites/favorites-web) | [码云地址](https://gitee.com/ityouknow/favorites-web) | [中文说明](http://www.ityouknow.com/springboot/2016/09/26/springboot%E5%AE%9E%E6%88%98-%E6%88%91%E4%BB%AC%E7%9A%84%E7%AC%AC%E4%B8%80%E6%AC%BE%E5%BC%80%E6%BA%90%E8%BD%AF%E4%BB%B6.html) | [文档手册](https://github.com/cloudfavorites/favorites-web/wiki/%E5%A6%82%E4%BD%95%E6%9E%84%E5%BB%BA%E9%A1%B9%E7%9B%AE).

核心功能点：

- 收藏、分类、检索文章
- 导出、导出（包活从浏览器中）
- 可以点赞、分享、讨论
- 注册、登录、个人账户
- 临时收藏、查看别人收藏
- 其它...


项目使用技术
------------

* Vue
* Bootstrap
* jQuery
* Thymeleaf
* Spring Data Jpa
* Spring Boot Mail
* WebJars
* Mysql
* Tomcat
* ~~Redis~~


Screenshots
------------

主页  
![favorites_chrome](http://www.ityouknow.com/assets/images/2016/favorites_index.png)  

注册  
![favorites_chrome](http://www.ityouknow.com/assets/images/2016/favorites_register.png)  

首页  
![favorites_chrome](http://www.ityouknow.com/assets/images/2016/favorites_home.png)  

收藏  
![favorites_chrome](http://www.ityouknow.com/assets/images/2016/favorites_collect.png)  


How to use
------------

![How to use](http://favorites.ren/img/useTool.gif)


Docker Deploy
----------

推荐使用 Docker 部署此项目，需要提前安装 Docker 和 Docker compose 环境，[可以参考这里](http://www.ityouknow.com/docker.html)

下载最新发布版本

``` sh
wget https://github.com/cloudfavorites/favorites-web/archive/favorites-1.3.0.zip
```

解压

``` sh
unzip favorites-1.1.1.zip
```

进入目录

``` sh
cd favorites-web-favorites-1.1.1/
```

修改文件`application-docker.properties`

``` sh
vi app/src/main/resources/application-docker.properties
```

修改内容如下
``` sh
favorites.base.path=http://xx.xxx.xx.xx/ 
```
>地址为部署服务器的地址

配置完成后，后台启动

``` sh
[root@~]# docker-compose up -d
Creating network "favoriteswebfavorites111_default" with the default driver
Creating favorites-nginx                  ... done
Creating favoriteswebfavorites111_mysql_1 ... done
Creating favoriteswebfavorites111_app_1   ... done
```

启动完成后，浏览器访问上面配置地址：`http://xx.xxx.xx.xx/`，就可以看到云收藏的首页了。


Discussing
----------
- [submit issue](https://github.com/cloudfavorites/favorites-web/issues/new)
- email: ityouknow@126.com


勾搭下
--------
关注公众号：纯洁的微笑，回复"springboot"进群交流

![](http://www.ityouknow.com/assets/images/keeppuresmile_430.jpg)