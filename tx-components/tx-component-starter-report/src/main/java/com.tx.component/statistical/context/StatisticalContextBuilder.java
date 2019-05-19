/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年10月21日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import com.tx.component.statistical.component.conditionCompent.ConditionComponent;
import com.tx.component.statistical.component.reportRenderComponent.impl.AbstractReportRenderHandler;
import com.tx.component.statistical.component.viewComponent.ViewComponent;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.component.statistical.type.ConditionTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 统计容器构建器<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2015年10月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StatisticalContextBuilder extends StatisticalContextConfigurator {
    /**
     * 日志容器
     */
    protected Logger logger = LoggerFactory
            .getLogger(StatisticalContextBuilder.class);

    //页面渲染处理器
    private Collection<AbstractReportRenderHandler> abstractReportRenderHandlers;

    //资源注册器
    private Collection<StatisticalResourceRegister> statisticalResourceRegisters;

    private Map<ConditionTypeEnum, ConditionComponent> conditionComponentMap = new HashMap<>();

    private Collection<ViewComponent> viewComponents = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        loadBeans();
        logger.info("   开始加载统计报表配置文件...");
        registerResource();
        logger.info("   加载统计报表配置文件结束  ");
    }

    //加载基本的信息
    private void loadBeans() {
        //加载所有的资源注册器
        statisticalResourceRegisters = applicationContext
                .getBeansOfType(StatisticalResourceRegister.class).values();

        //加载页面渲染处理器
        abstractReportRenderHandlers = applicationContext
                .getBeansOfType(AbstractReportRenderHandler.class).values();

        //加载页面条件展示组件
        Collection<ConditionComponent> conditionComponents = applicationContext
                .getBeansOfType(ConditionComponent.class).values();
        for (ConditionComponent temp : conditionComponents) {
            ConditionTypeEnum key = temp.supportType();
            conditionComponentMap.put(key, temp);
        }

        viewComponents = applicationContext.getBeansOfType(ViewComponent.class).values();

    }

    private StatisticalResourceRegister resolveStatisticalResourceRegister(Resource resource) {
        for (StatisticalResourceRegister temp : statisticalResourceRegisters) {
            if (temp.supportRegister(resource)) {

                return temp;
            }
        }
        return null;
    }

    /**
     * 注册资源
     *
     * @throws IOException
     */
    private void registerResource() throws IOException {
        Resource[] resources = StatisticalReportConfigurator.getReportResources();
        if (resources == null) {
            logger.warn("没有报表文件");
            return;
        }
        for (Resource tempResource : resources) {
            ReportStatement reportStatement = null;
          /*  StatisticalResourceRegister statisticalResourceRegister = resolveStatisticalResourceRegister(tempResource);
            statisticalResourceRegister.registerResource(tempResource);*/
            for (StatisticalResourceRegister temp : statisticalResourceRegisters) {
                try {
                    if (temp.supportRegister(tempResource)) {
                        reportStatement = temp.registerResource(tempResource);
                        if (reportStatement != null) {
                            break;
                        }

                    }
                } catch (Exception e) {
                    logger.error("资源【" + tempResource.getURL().toString()
                                    + "】被注册器【" + temp.getClass().getName() + "】解析失败",
                            e);
                }
            }
            if (reportStatement == null) {
                logger.error(
                        "资源【" + tempResource.getURL().toString() + "】解析失败");
            }
        }
    }

    /**
     * 决定报表渲染处理器
     *
     * @param reportCode
     * @return
     */
    public AbstractReportRenderHandler resolveReportRenderHandler(
            String reportCode) {
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        AssertUtils.notEmpty(reportStatement, "reportStatement is empty.");
        String type = reportStatement.getType();
        AbstractReportRenderHandler abstractReportRenderHandler = null;
        for (AbstractReportRenderHandler reportRenderHandler : abstractReportRenderHandlers) {
            if (reportRenderHandler.isSupport(type)) {
                abstractReportRenderHandler = reportRenderHandler;
                break;
            }
        }
        AssertUtils.notEmpty(abstractReportRenderHandler, "abstractReportRenderHandler is empty.");
        return abstractReportRenderHandler;
    }

    /**
     * 重新加载报表
     *
     * @param reportCode
     */
    public void reloadReport(String reportCode) {
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        StatisticalResourceRegister statisticalResourceRegister = reportStatement.getStatisticalResourceRegister();
        if (statisticalResourceRegister == null) {
            statisticalResourceRegister = resolveStatisticalResourceRegister(reportStatement.getReportResource());
        }
        statisticalResourceRegister.reloadReport(reportStatement);
    }

    public void reRegisterResource() {
        Resource[] resources = StatisticalReportConfigurator.getReportResources();
        for (Resource tempResource : resources) {
            ReportStatement reportStatement = null;
            for (StatisticalResourceRegister temp : statisticalResourceRegisters) {
                String resourceUrl = null;
                try {
                    resourceUrl = tempResource.getURL().toString();
                    reportStatement = temp.registerResource(tempResource);
                } catch (Exception e) {
                    logger.error("资源【" + resourceUrl
                                    + "】被注册器【" + temp.getClass().getName() + "】解析失败",
                            e);

                }
            }
        }
    }

    public Map<ConditionTypeEnum, ConditionComponent> getConditionComponentMap() {
        return conditionComponentMap;
    }

    public Collection<ViewComponent> getViewComponents() {
        return viewComponents;
    }

    public void setViewComponents(Collection<ViewComponent> viewComponents) {
        this.viewComponents = viewComponents;
    }
}
