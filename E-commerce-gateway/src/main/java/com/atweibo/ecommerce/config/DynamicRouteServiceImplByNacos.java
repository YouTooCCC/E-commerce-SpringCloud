package com.atweibo.ecommerce.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Description 通过nacos 下发动态路由配置，监听Nacos中路由配置变更
 * @Author weibo
 * @Data 2022/11/15 20:22
 */
@Slf4j
@Component
@DependsOn({"gatewayConfig"})//GatewayConfig初始化后再初始化当前
public class DynamicRouteServiceImplByNacos {
    /*nacos配置服务,读取相关配置*/
    private ConfigService configService;

    private final DynamicRouteServiceImpl dynamicRouteService;
    public DynamicRouteServiceImplByNacos(DynamicRouteServiceImpl dynamicRouteServiceImpl) {
        this.dynamicRouteService = dynamicRouteServiceImpl;
    }

    /**
     * @return void
     * @Describe:  刚开始链接nacos，使用init方法读取nacos的相关配置
     * @author Weibo
     * @date 2022/11/21 16:33
     */

    @PostConstruct  //Bean在容器中构造完成之后会立即执行init方法
    public void init(){
        log.info("网关路由初始化......");

        try {
            /*初始化 nacos 配置客户端：链接nacos，初始化相关信息*/
            configService = initConfigService();
            if (configService == null){
                log.error("初始化配置服务出错......");
                return;
            }

            /*通过Nacos Config b并指定路由配置路径去获取路由配置*/
            String configInfo = configService.getConfig(
                    GatewayConfig.NACOS_ROUTE_DATA_ID,
                    GatewayConfig.NACOS_ROUTE_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT
            );

            log.info("获取网关配置:[{}]",configInfo);
            List<RouteDefinition>  definitionList = JSON.parseArray(configInfo, RouteDefinition.class);

            if (CollectionUtils.isNotEmpty(definitionList)){
                for (RouteDefinition definition : definitionList){
                    log.info("初始化网关 :[{}]",definition.toString());
                    dynamicRouteService.addRouteDefinition(definition);
                }
            }

        } catch (Exception e) {
            log.error("网关路由出现错误: [{}]",e.getMessage(),e);
        }

        /*设置监听器*/
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID
                ,GatewayConfig.NACOS_ROUTE_GROUP );
    }


    /*初始化Nacos Config*/
    private ConfigService initConfigService()  {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
           // return  NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            log.info("初始化网关 Nacos出错: [{}]",e.getMessage(),e);
            return null;
        }
    }

    /*实现对nacos的监听，对nacos下发的动态路由配置*/
    private void dynamicRouteByNacosListener(String dataId,String group){
        /*给nacos Config客户端增加一个监听器*/
        try {
            configService.addListener(dataId, group, new Listener() {

                /*自己提供线程池执行操作*/
                @Override
                public Executor getExecutor() {
                    return null;
                }
                /*
                *监听器接收到配置更新
                *nacos 中最新的配置定义
                * */
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("开始更新网关配置:[{}]",configInfo);

                    List<RouteDefinition> definitionList =
                            JSON.parseArray(configInfo, RouteDefinition.class);
                    log.info("更新路由: [{}]",definitionList.toString());
                    dynamicRouteService.updateList(definitionList);
                }
            });
        } catch (NacosException e) {
            log.info("动态更新网关配置错误: [{}]",e.getMessage(),e);
        }
    }
}
