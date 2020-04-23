/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.basicdata;



 /**
  * 模板表状态枚举型
  *     配置态
  *     运行态
  *     停止态
  *     升级态_重命名
  *     升级态_创建新表
  *     升级态_数据迁移：向新表中迁移旧表数据
  *     ->配置态：模板表尚未在数据库中创建真实的表
  *     ->运行态：模板表已经在数据库中真实创建
  *     ->停止态：
  *     ->升级态：模板表如果添加字段等操作，实际过程中先是将表状态转换为停止态
  *         然后运行相关数据表变更语句：重命名旧表名称->创建新表->新旧表的数据迁移
  *         变更过程，需要把同名表不同的模板类型的一并进行变更
  *         如果迁移过程中出现问题，表将停止在停止态;
  * @author  brady
  * @version  [版本号, 2013-10-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum TemplateTableStatusEnum{
    /**
     * 新加入模板表状态
     * 模板表尚未在数据库中创建真实的表
     */
    配置态,
    /**
     * 模板表已经在数据库中进行创建
     */
    运行态,
    /**
     * 模板表状态为停止态
     */
    停止态;
}