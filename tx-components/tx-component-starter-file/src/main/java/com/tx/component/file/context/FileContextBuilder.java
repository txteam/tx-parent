/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月11日
 * <修改描述:>
 */
package com.tx.component.file.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.tx.component.file.config.FileContextConfig;
import com.tx.component.file.config.FileModuleCfg;
import com.tx.component.file.driver.FileDefinitionResourceDriver;
import com.tx.component.file.driver.FileDefinitionResourceDriverRegistry;
import com.tx.component.file.driver.impl.SystemFileDefinitionResourceDriver;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileModule;
import com.tx.component.file.model.ReadWritePermissionEnum;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.service.FileDefinitionPersistService;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutorFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.UUIDUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 文件容器构建器<br/>
 *
 * @author Administrator
 * @version [版本号, 2014年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileContextBuilder extends FileContextConfigurator implements
        BeanNameAware, ResourceLoaderAware {

    /**
     * beanName
     */
    protected static String beanName;
    /**
     * 模块映射
     */
    protected final Map<String, FileModule> moduleMap = new HashMap<String, FileModule>();
    /**
     * 文件容器配置
     */
    protected final XStream fileModuleCfgXstream = XstreamUtils.getXstream(FileContextConfig.class);
    /**
     * 默认文件模块
     */
    protected FileModule defaultFileModule;
    /**
     * 资源加载器
     */
    protected ResourceLoader resourceLoader;

    /**
     * 文件定义业务层
     */
    private FileDefinitionService fileDefinitionService;

    /**
     * 文件定义持久层
     */
    private FileDefinitionPersistService fileDefinitionPersistService;

    /**
     * 获取文件定义业务层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionService [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileDefinitionService getFileDefinitionService() {
        if (this.fileDefinitionService != null) {
            return this.fileDefinitionService;
        }
        this.fileDefinitionService = applicationContext.getBean("fileContext.fileDefinitionService",
                FileDefinitionService.class);
        return this.fileDefinitionService;
    }

    /**
     * 获取文件定义业务层<br/>
     * <功能详细描述>
     *
     * @return FileDefinitionService [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileDefinitionPersistService getFileDefinitionPersistService() {
        if (this.fileDefinitionPersistService != null) {
            return this.fileDefinitionPersistService;
        }
        this.fileDefinitionPersistService = applicationContext.getBean("fileContext.fileDefinitionPersistService",
                FileDefinitionPersistService.class);
        return this.fileDefinitionPersistService;
    }

    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        FileContextBuilder.beanName = name;
    }

    /**
     * @throws Exception
     */
    @Override
    protected final void doBuild() throws Exception {
        this.tableDDLExecutor = TableDDLExecutorFactory.buildTableDDLExecutor(dataSourceType,
                dataSource);
        initTables();//初始化表定义

        parseFileModuleConfig();

        AssertUtils.notEmpty(this.location, "location is empty.");
        initDefaultLocation();
        this.defaultFileModule = new FileModule("default",
                ReadWritePermissionEnum.PUBLIC_READ,
                new SystemFileDefinitionResourceDriver(this.location));
    }

    /**
     * 初始化默认存储路径<br/>
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws Exception [参数说明]
     * @throws throws    [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void initDefaultLocation() throws Exception {
        AssertUtils.notEmpty(this.location, "location is empty.");
        this.location = StringUtils.cleanPath(this.location);//整理path中"\\"为"/"
        while (this.location.endsWith("/")) {
            //去除path中尾部存在的"/"
            this.location = this.location.substring(0, location.length() - 1);
        }
        AssertUtils.notEmpty(location, "location is empty.");
        if (!this.location.endsWith("/")) {
            //追加"/"
            this.location = this.location + "/";
        }

        File file = new File(location);
        if (!file.exists() && !file.isDirectory()) {
            try {
                FileUtils.forceMkdir(new File(location));
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        e.getMessage());
            }
        }
    }

    /**
     * 解析文件模块配置<br/>
     * <功能详细描述>
     *
     * @return void [返回类型说明]
     * @throws IOException [参数说明]
     * @throws throws      [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void parseFileModuleConfig() throws IOException {
        Resource configResource = this.resourceLoader.getResource(this.configLocation);

        InputStream inputStream = configResource.getInputStream();
        Object o = fileModuleCfgXstream.fromXML(configResource.getFile());
        FileContextConfig fcc = new FileContextConfig();// = //(FileContextConfig) fileModuleCfgXstream.fromXML(configResource.getFile());

        if (o instanceof FileContextConfig) {
            fcc = (FileContextConfig) o;
        }

        if (fcc.getFileModuleConfig() == null) {
            return;
        }
        if (CollectionUtils.isEmpty(fcc.getFileModuleConfig()
                .getFileModuleCfg())) {
            return;
        }
        for (FileModuleCfg fmcfg : fcc.getFileModuleConfig().getFileModuleCfg()) {
            if (StringUtils.isEmpty(fmcfg.getModule())) {
                continue;
            }
            FileModule fm = new FileModule();
            fm.setDriver(FileDefinitionResourceDriverRegistry.parseDriver(fmcfg.getDriver()));
            fm.setModule(fmcfg.getModule());
            fm.setPermission(fmcfg.getPermission());
            moduleMap.put(fmcfg.getModule(), fm);
        }
    }

    /**
     * 整理传入的relativePath的值<br/>
     * relativePath不能为空
     * relativePath不应以"/"开始
     * <功能详细描述>
     *
     * @param relativePath
     * @return String [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String cleanRelativePath(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        relativePath = StringUtils.cleanPath(relativePath);//整理path中"\\"为"/"
        while (relativePath.startsWith("/")) {
            //去除path中尾部存在的"/"
            relativePath = relativePath.substring(1, relativePath.length());
        }
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        return relativePath;
    }

    /**
     * 获取模块对应的文件文件模块对象<br/>
     * <功能详细描述>
     *
     * @param module
     * @return FileModule [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileModule getFileModule(String module) {
        AssertUtils.notEmpty(module, "module is empty.");

        if (!moduleMap.containsKey(module)) {
            return defaultFileModule;
        }

        FileModule fm = moduleMap.get(module);
        return fm;
    }

    /**
     * 根据文件定义获取文件资源<br/>
     * <功能详细描述>
     *
     * @param fileDefinition
     * @return FileResource [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected FileResource doGetFileResource(FileDefinition fileDefinition) {
        if (fileDefinition == null) {
            return null;
        }

        String module = fileDefinition.getModule();
        FileModule fm = getFileModule(module);
        FileDefinitionResourceDriver driver = fm.getDriver();
        FileResource resource = driver.getResource(fileDefinition);

        return resource;
    }

    /**
     * 返回文件定义对象<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativeFolder
     * @param filenameExtensions
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return PagedList<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected PagedList<FileDefinition> doQueryPagedList(String module,
                                                        String relativeFolder, String[] filenameExtensions,
                                                        Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(module, "module is empty.");

        PagedList<FileDefinition> resPagedList = getFileDefinitionService().queryPagedList(module,
                relativeFolder,
                filenameExtensions,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }

    /**
     * 返回文件定义对象<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativeFolder
     * @param filenameExtensions
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected List<FileDefinition> doQueryList(String module,
                                               String relativeFolder, String[] filenameExtensions,
                                               Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");

        List<FileDefinition> resList = getFileDefinitionService().queryList(module,
                relativeFolder,
                filenameExtensions,
                params);
        return resList;
    }

    /**
     * 返回文件定义对象<br/>
     *
     * @param fileId 文件定义id
     * @return FileDefinition 文件定义
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected FileDefinition doFindById(String fileId) {
        AssertUtils.notEmpty(fileId, "fileId is empty.");

        FileDefinition fileDefinition = getFileDefinitionPersistService().findById(fileId);

        return fileDefinition;
    }

    /**
     * 返回文件定义对象<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativePath
     * @return FileDefinition [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected FileDefinition doFindByRelativePath(String module,
                                                  String relativePath) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");

        FileDefinition fileDefinition = getFileDefinitionService().findByRelativePath(module,
                relativePath);

        return fileDefinition;
    }

    /**
     * 删除对应的文件，并将对应文件的相关记录移除到历史表中<br/>
     *
     * @param fileId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean doDeleteById(String fileId) {
        AssertUtils.notEmpty(fileId, "fileId is empty.");

        FileDefinition fileDefinition = getFileDefinitionPersistService().findById(fileId);
        if (fileDefinition == null) {
            return false;
        }
        getFileDefinitionPersistService().evict(fileId);
        getFileDefinitionService().moveToHis(fileDefinition);//删除

        String module = fileDefinition.getModule();
        FileModule fm = getFileModule(module);
        FileDefinitionResourceDriver driver = fm.getDriver();
        FileResource resource = driver.getResource(fileDefinition);

        resource.delete();
        return true;
    }

    /**
     * 保存文件，允许存在的文件写入<br/>
     *
     * @param relativePath 存储路径
     * @param filename     文件名
     * @param input        文件流
     * @return FileDefinition 文件定义的实体
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected FileDefinition doSaveFile(String module, String relativePath,
                                        InputStream input) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notNull(input, "input is empty.");

        relativePath = cleanRelativePath(relativePath);//相对路径
        //持久化对应的文件对象
        FileDefinition fileDefinition = getFileDefinitionService().findByRelativePath(module,
                relativePath);
        if (fileDefinition != null) {
            getFileDefinitionPersistService().evict(fileDefinition.getId());

            FileModule fm = getFileModule(module);
            FileDefinitionResourceDriver driver = fm.getDriver();
            FileResource resource = driver.getResource(fileDefinition);
            resource.save(input);

            fileDefinition.setViewUrl(resource.getViewUrl());
            fileDefinition.setResource(resource);

            getFileDefinitionService().updateById(fileDefinition);
        } else {
            fileDefinition = buildFileDefinition(module, relativePath);
            FileModule fm = getFileModule(module);
            FileDefinitionResourceDriver driver = fm.getDriver();
            FileResource resource = driver.getResource(fileDefinition);
            resource.save(input);

            fileDefinition.setViewUrl(resource.getViewUrl());
            fileDefinition.setResource(resource);

            getFileDefinitionService().insert(fileDefinition);
        }

        return fileDefinition;
    }

    /**
     * 新增文件，如果文件已经存在，将会抛出异常<br/>
     *
     * @param relativePath 存储路径
     * @param filename     文件名
     * @param input        文件流
     * @return FileDefinition 文件定义
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected FileDefinition doAddFile(String module, String relativePath,
                                       InputStream input) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notNull(input, "input is empty.");

        relativePath = cleanRelativePath(relativePath);//相对路径
        //持久化对应的文件对象
        FileDefinition fileDefinition = buildFileDefinition(module,
                relativePath);

        FileModule fm = getFileModule(module);
        FileDefinitionResourceDriver driver = fm.getDriver();
        FileResource resource = driver.getResource(fileDefinition);
        resource.add(input);

        fileDefinition.setViewUrl(resource.getViewUrl());
        fileDefinition.setResource(resource);

        getFileDefinitionService().insert(fileDefinition);

        return fileDefinition;
    }

    /**
     * 创建fileDefinition并持久化对应的文件定义<br/>
     *
     * @param relativePath 存储路径
     * @param filename     文件名
     * @return FileDefinition 文件定义
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileDefinition buildFileDefinition(String module,
                                               String relativePath) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        String filename = StringUtils.getFilename(relativePath);
        String filenameExtension = StringUtils.getFilenameExtension(relativePath);

        relativePath = cleanRelativePath(relativePath);//相对路径
        Date now = new Date();

        FileDefinition fileDefinition = new FileDefinition();
        fileDefinition.setId(UUIDUtils.generateUUID());
        fileDefinition.setCreateDate(now);
        fileDefinition.setLastUpdateDate(now);

        fileDefinition.setModule(module);
        fileDefinition.setRelativePath(relativePath);
        fileDefinition.setFilename(filename);
        fileDefinition.setFilenameExtension(filenameExtension);

        fileDefinition.setSystem(this.system);

        return fileDefinition;
    }

    /**
     * 为容器创建或升级表
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void initTables() {
        table_core_file_definition();
    }

    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void table_core_file_definition() {
        String tableName = "core_file_definition";
        String hisTableName = "core_file_definition_his";

        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        CreateTableDDLBuilder hisCreateDDLBuilder = null;
        AlterTableDDLBuilder hisAlterDDLBuilder = null;
        DDLBuilder<?> hisDDLBuilder = null;

        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor.generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor.generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }

        if (this.tableDDLExecutor.exists(hisTableName)) {
            hisAlterDDLBuilder = this.tableDDLExecutor.generateAlterTableDDLBuilder(hisTableName);
            hisDDLBuilder = hisAlterDDLBuilder;
        } else {
            hisCreateDDLBuilder = this.tableDDLExecutor.generateCreateTableDDLBuilder(hisTableName);
            hisDDLBuilder = hisCreateDDLBuilder;
        }

        core_file_definition(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(true,
                "idx_file_definition_00",
                "relativePath,module");

//        if (alterDDLBuilder != null
//                && alterDDLBuilder.al(false, false)) {
//            this.tableDDLExecutor.alter(alterDDLBuilder, false, false);
//        } else if (createDDLBuilder != null) {
//            this.tableDDLExecutor.create(createDDLBuilder);
//        }
//
//        core_file_definition(hisDDLBuilder);//写入表结构
//        if (hisAlterDDLBuilder != null
//                && hisAlterDDLBuilder.isNeedAlter(false, false)) {
//            this.tableDDLExecutor.alter(hisAlterDDLBuilder, false, false);
//        } else if (hisCreateDDLBuilder != null) {
//            this.tableDDLExecutor.create(hisCreateDDLBuilder);
//        }
    }

    /**
     * dt_table的构建器<br/>
     * <功能详细描述>
     *
     * @param ddlBuilder [参数说明]
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void core_file_definition(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("system", 64, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfVarchar("relativePath", 256, true, null)
                .newColumnOfVarchar("filename", 64, true, null)
                .newColumnOfVarchar("filenameExtension", 64, false, null)
                .newColumnOfVarchar("viewUrl", 256, false, null)
                .newColumnOfDate("deleteDate", false, false)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
    }
}
