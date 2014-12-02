package com.dxs.common;

/**
 * 常量类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-4]
 */
public interface Constants
{   
    /**
     * web主页
     */
    String SERVER_MIAN_PATH = "http://192.168.11.39:8080/WallPaperWeb";
    
    //String SERVER_MIAN_PATH = "http://www.mvpod.com:8080/WallPaperWeb";
    
    String APK_DOWNLOAD_ACTION = SERVER_MIAN_PATH+"/DownLoadApkAction?wallPaperId=";
     
    /**
     * 生成短链接的ACCESS_TOKEN
     */
    String ACCESS_TOKEN ="2.001myFfF7zEzEBe023c193ffJEvjHB";
    
    /**
     * 短链接生成地址
     */
    String SHORTRQU="https://api.weibo.com/2/short_url/shorten.json?url_long=";
    
    /**
     * 配置文件常量类
     */
    public interface ConstantsConfigFile
    {   
        /**
         * 系统全局配置文件
         */
        String COMMON_CONFIG_FILENAME = "const_config.properties";
        
        /**
         * 市场配置项
         */
        String MARKET_CONFIG_PROPERTY  ="market";
        
        /**
         * 服务器地址配置项
         */
        String SERVER_ADDRESS_PROPERTY = "serverAddress";
    }
    
    /**
     * 编译
     */
    public interface constantsCompile
    {
        /**
         * web服务主目录
         */
        String WEB_MAIN_PATH = "/home/environment/workspace";
        
        /**
         * 壁纸的工程目录
         */
        String WEB_APP_PATH = WEB_MAIN_PATH + "/CoolSpeedWallpapers";
        
        /**
         * 壁纸的工程编译目录
         */
        
        String WEB_APP_COMPILE_PATH = WEB_MAIN_PATH + "/CoolSpeedWallpapersNew";
        
        String WEB_MAIN = "/home/web";
        
        String WEB_WALLPAPER_RESOURCES_APK = WEB_MAIN + "/wallpaper/resources/apk";
        
        /**
         * 壁纸编译资源文件目录
         */
        String WAllPAPER_BUILD_RESOURCCES_MAIN_DIR = WEB_MAIN + "/wallpaper/resources/picture_resources_compile";
        
        /**
         * 编译资源文件备份目录
         */
        String WAllPAPER_BUILD_RESOURCCES_BACKUPDIR = WEB_MAIN + "/wallpaper/resources/picture_resources_bakup_zip";
        
        /**
         * apk打包目录
         */
        String WALLPAPER_BUILD_RESOURCES_PACKAGE = WEB_MAIN + "/wallpaper/resources/apkpackage";
        
        /**
         * 编译资源的临时目录
         */
        String WAllPAPER_BUILD_RESOURCCES_COMPILE_TEMPDIR = WEB_MAIN
            + "/wallpaper/resources/picture_resources_compile_temp";
        
        /**
         * 壁纸资源的上传目录
         */
        String wallPaperUploadDir = "/home/ftp/wallpaperupload";
        
        /**
         * 编译生成的APK路径
         */
        String FILE_GEN_APK_PATH = WEB_APP_COMPILE_PATH + "/bin/CPActivity.apk";
        
        /**
         * release APK
         */
        String FILE_RELEASE_APK = WEB_APP_COMPILE_PATH + "/bin/CPActivity-release-unsigned.apk";
        
        /**
         * 优化后的apk
         */
        String FILE_GEN_ZIPALIGN_TOOLS = WEB_APP_COMPILE_PATH + "/bin/zipalign.apk";
        
        /**
         * 壁纸AndroidManifest.xml文件
         */
        String ANDROIDMANIFEST_WALLPAPER_PATH = WEB_APP_COMPILE_PATH + "/AndroidManifest.xml";
        
        /**
         * strings.xml
         */
        String ANDROID_STRING_XML_PATH = WEB_APP_COMPILE_PATH + "/res/values/strings.xml";
        
        /**
         * 中文strings.xml
         */
        String ANDROID_STRING_XML_CN_PATH = WEB_APP_COMPILE_PATH + "/res/values-zh-rCN/strings.xml";
        
        /**
         * 资源文件目录drawable
         */
        String ANDROID_DRAWABLE_DIR = WEB_APP_COMPILE_PATH + "/res/drawable";
        
        /**
         * 资源文件目录drawable-hdpi
         */
        String ANDROID_DRAWABLE_HDPI_DIR = WEB_APP_COMPILE_PATH + "/res/drawable-hdpi";
        
        /**
         * 资源文件目录drawable-ldpi
         */
        String ANDROID_DRAWABLE_LDPI_DIR = WEB_APP_COMPILE_PATH + "/res/drawable-ldpi";
        
        /**
         * 资源文件目录drawable-mdpi
         */
        String ANDROID_DRAWABLE_MDPI_DIR = WEB_APP_COMPILE_PATH + "/res/drawable-mdpi";
        
        /**
         * 壁纸资源路径
         */
        String ANDROID_ASSETS_DIR = WEB_APP_COMPILE_PATH + "/assets";
        
        /**
         * 壁纸资源路径
         */
        String ANDROID_ASSETS_WALLPAPER_DIR = ANDROID_ASSETS_DIR + "/bizhi";
        
        /**
         * app_name
         */
        String APP_NAME_MATCH_KEY = "app_name";
        
        /**
         * app_name 匹配值
         */
        String APP_NAME_MATCH_VALUE = "Shareactivity";
        
        /**
         * app_name
         */
        String APP_NAME_REPLACE_KEY = "replace.appname";
        
        /**
         * 市场类型
         */
        String MARKET_TYPE_MATCH_KEY = "market_type";
        
        /**
         * 市场类型
         */
        String MARKET_TYPE_MATCH_VALUE = "markettype";
        
        /**
         * 市场类型
         */
        String MARKET_TYPE_REPLACE_KEY = "market.type";
        
        /**
         * AndroidManifest.xml package key
         */
        String ANDROIDMANIFEST_PACKAGE_REPLACE_KEY = "package.replacename";
        
        /**
         * AndroidManifest.xml package key
         */
        String ANDROIDMANIFEST_VERSIONCODE_REPLACE_KEY = "version.value";
        
        /**
         * 友盟的key
         */
        String ANDROIDMANIFEST_UMCHANNEL_KEY_REPLACE_KEY = "pkg.umappkey";
        
        /**
         * 友盟的key
         */
        String ANDROIDMANIFEST_UMCHANNEL_KEY_REPLACE_KEY_VALUE = "5046b1845270156be90001fd";
        
        /**
         * 友盟
         */
        String ANDROIDMANIFEST_UMCHANNEL_REPLACE_KEY = "umchannel.name";
        
        /**
         * 友盟
         */
        String PACEAGE_REPLACENAME_KEY = "umchannel";
        
        /**
         * 替换的包名称
         */
        String PACKAGE_REPLACE_NAME = "com.iss.cp";
        
        /**
         * 替换包名的key
         */
        String PACKAGE_MATCH_KEY = "package.name";
        
        /**
         * 编译状态为编译中
         */
        String COMPILE_STATUS_COMPILEING = "1";
        
        /**
         * 编译状态为等待
         */
        String COMPILE_STATUS_WAIT = "2";
        
        /**
         * 编译状态为结束
         */
        String COMPILE_STATUS_FINISH = "3";
        
        /**
         * 移动APK完成，可以下载
         */
        String DOWNLOAD_STATUS = "4";
        
        /**
         * 编译出错状态
         */
        String COMPILE_ERROR = "5";
    }
}
