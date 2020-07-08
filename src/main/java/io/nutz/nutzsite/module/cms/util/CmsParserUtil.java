package io.nutz.nutzsite.module.cms.util;

import cn.hutool.core.io.FileUtil;
import io.nutz.nutzsite.module.cms.models.Category;
import org.apache.commons.lang3.StringUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class CmsParserUtil  extends ParserUtil{


    public CmsParserUtil() {
    }

    /**
     * 指定模板，指定路径进行生成静态页面，会自定识别pc与移动端
     *
     * @param templatePath
     *            模板路径
     * @param targetPath
     *            生成后的路径，默认生成的html文件，所以不能带.html后缀，
     * @throws IOException
     */
    public static void generate(String templatePath, String targetPath) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(IS_DO, false);
        Category column = new Category();
        //内容管理栏目编码
        map.put(COLUMN, column);
        String content = CmsParserUtil.generate(templatePath, map, false);
        FileUtil.writeString(content, ParserUtil.buildHtmlPath(targetPath), "utf-8");

        // 生成移动页面
        if (ParserUtil.hasMobileFile(templatePath)) {
            // 手机端m
            map.put(ParserUtil.MOBILE, BasicUtil.getSite().getMobileTheme());
            content = CmsParserUtil.generate(templatePath, map, true);
            FileUtil.writeString(content, ParserUtil.buildMobileHtmlPath(targetPath), "utf-8");
        }
    }




}
