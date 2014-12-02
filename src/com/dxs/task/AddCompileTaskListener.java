package com.dxs.task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 创建通用查询索引文件监听器
 */
public class AddCompileTaskListener implements ServletContextListener
{
    
    private java.util.Timer timer = null;
    
    public void contextInitialized(ServletContextEvent event)
    {
        
        timer = new java.util.Timer(true);
        event.getServletContext().log("定时器已启动");
        
        // 定时器调度语句,第三个参数表示每小时(即60*60*1000毫秒)被触发一次,中间参数0表示无延迟
        timer.schedule(new AddCompileTask(event.getServletContext()), 0, 30 * 1000);
        
        // 编译task
        timer.schedule(new GetAndCompileTask(event.getServletContext()), 30 * 1000, 30 * 1000);
        
        // 3秒钟执行1次
        timer.schedule(new ShortLinkComplementTask(event.getServletContext()), 30 * 1000, 10 * 1000);
        event.getServletContext().log("已经添加任务调度表");
    }
    
    public void contextDestroyed(ServletContextEvent event)
    {
        timer.cancel();
        event.getServletContext().log("定时器销毁");
    }
}
