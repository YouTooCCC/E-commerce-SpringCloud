package com.atweibo.ecommerce.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Description 实现事件推送 Aware：动态更新路由网关 Service。
 *             将从nacos上拿到的配置，动态的更改路由信息
 * @Author weibo
 * @Data 2022/11/15 19:30
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {


    /*写路由定义的工具，gateway中的路由定义，相当于使用@Autowirde注解注入*/
    private final RouteDefinitionWriter routeDefinitionWriter;
    /*获取路由定义*/
    private final RouteDefinitionLocator routeDefinitionLocator;
    /*初始化对象*/
    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter
            , RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    /** 事件发布    路由通过该对象发布*/
    private ApplicationEventPublisher publisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        /*完成事件推送句柄的初始化*/
        this.publisher = applicationEventPublisher;
    }


   /**
    * @return java.lang.String
    * @Describe:  增加路由定义 ：RouteDefinition就是路由的类，里面有相关属性
    * @param  definition  传递一个路由对象
    * @author Weibo
    * @date 2022/11/21 16:13
    */

    public String addRouteDefinition(RouteDefinition definition) {

        log.info("网关添加路由配置: [{}]", definition);

        /*保存路由配置并发布    sbuscribe 刷新*/
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        /*发布事件通知给gateway，同步新增的路由定义*/
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /*根据路由id去删除路由配置*/
    private String deleteById(String id) {
        try {
            log.info("网关删除路由的id: [{}]", id);
            /*把id传进去，根据id删除*/
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            /*发布事件通知给gateway 更新路由定义*/
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception ex) {
            log.error("网关删除路由出错:[{}]", ex.getMessage(), ex);
            return "delete fail";
        }
    }





    /**
     * @return java.lang.String
     * @Describe:       批量更新路由
     * @author Weibo
     * @date 2022/11/21 16:21
     */
    public String updateList(List<RouteDefinition> definitions) {

        log.info("网关更新路由: [{}]", definitions);

        /*首先拿到当前Gateway存储的路由定义*/
        List<RouteDefinition> routeDefinitionsExits =
                routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();

        /*如果当前的路由定义为空，！不为空*/
        if (!CollectionUtil.isEmpty(routeDefinitionsExits)) {
            /*清除之前所有的“旧的”路由定义*/
            routeDefinitionsExits.forEach(rd -> {
                log.info("删除路由 difinition: [{}]", rd);
                deleteById(rd.getId());
            });
        }

        /*把更新的路由定义同步到Gateway中*/
        definitions.forEach(definition -> updateByRouteDefinition(definition));
        return "success";
    }

    /*更新路由 ： 删除+新增 = 更新*/
    private String updateByRouteDefinition(RouteDefinition definition) {

        /*删除*/
        try {
            log.info("网关更新路由: [{}]", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));

        } catch (Exception ex) {
            return "更新失败,不能找到路由id:" + definition.getId();
        }
        /*新增*/
        try {
            this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception ex) {
            return "更新路由失败";
        }


    }
}
