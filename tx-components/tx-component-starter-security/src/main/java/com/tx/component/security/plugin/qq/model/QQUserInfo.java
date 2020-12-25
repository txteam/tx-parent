/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月25日
 * <修改描述:>
 */
package com.tx.component.security.plugin.qq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * QQ用户信息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QQUserInfo {
    
    private int ret;
    
    private String msg;
    
    private String nickname;
    
    private String figureurl;
    
    @JSONField(name="figureurl_1")
    private String figureurl1;
    
    @JSONField(name="figureurl_2")
    private String figureurl2;
    
    @JSONField(name="figureurl_qq_1")
    private String figureurlQQ1;
    
    @JSONField(name="figureurl_qq_2")
    private String figureurlQQ2;
    
    private String gender;
    
    /**
     * @return 返回 ret
     */
    public int getRet() {
        return ret;
    }
    
    /**
     * @param 对ret进行赋值
     */
    public void setRet(int ret) {
        this.ret = ret;
    }
    
    /**
     * @return 返回 msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * @param 对msg进行赋值
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    /**
     * @return 返回 nickname
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * @param 对nickname进行赋值
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    /**
     * @return 返回 figureurl
     */
    public String getFigureurl() {
        return figureurl;
    }
    
    /**
     * @param 对figureurl进行赋值
     */
    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }
    
    /**
     * @return 返回 figureurl1
     */
    public String getFigureurl1() {
        return figureurl1;
    }
    
    /**
     * @param 对figureurl1进行赋值
     */
    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }
    
    /**
     * @return 返回 figureurl2
     */
    public String getFigureurl2() {
        return figureurl2;
    }
    
    /**
     * @param 对figureurl2进行赋值
     */
    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }
    
    /**
     * @return 返回 figureurlQQ1
     */
    public String getFigureurlQQ1() {
        return figureurlQQ1;
    }
    
    /**
     * @param 对figureurlQQ1进行赋值
     */
    public void setFigureurlQQ1(String figureurlQQ1) {
        this.figureurlQQ1 = figureurlQQ1;
    }
    
    /**
     * @return 返回 figureurlQQ2
     */
    public String getFigureurlQQ2() {
        return figureurlQQ2;
    }
    
    /**
     * @param 对figureurlQQ2进行赋值
     */
    public void setFigureurlQQ2(String figureurlQQ2) {
        this.figureurlQQ2 = figureurlQQ2;
    }
    
    /**
     * @return 返回 gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @param 对gender进行赋值
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
