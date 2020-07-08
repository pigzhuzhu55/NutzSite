package io.nutz.nutzsite.module.cms.util;

import io.nutz.nutzsite.module.cms.models.Site;
import io.nutz.nutzsite.module.cms.services.SiteService;
import io.nutz.nutzsite.module.cms.services.impl.SiteServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BasicUtil {

    @Deprecated
    private static final String PAGE_NO = "pageNo";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";
    private static final String PAGE = "page";
    private static final String IDS = "ids";

    public static String getSiteId() {
        Site site = getSite();
        return site != null ? site.getId() : "0";
    }

    public static Site getSite() {
        SiteService siteService = (SiteService)Mvcs.getIoc().get(SiteServiceImpl.class);
        Site website = siteService.getByUrl(getDomain());
        website.setDomain(getUrl());
        return website;
    }

    public static String getUrl() {
        HttpServletRequest request = Mvcs.getReq();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() == 80) {
            basePath = basePath + path;
        } else {
            basePath = basePath + ":" + request.getServerPort() + path;
        }

        return basePath;
    }

    public static String getDomain() {
        String path = Mvcs.getReq().getContextPath();
        String domain = Mvcs.getReq().getServerName();
        if (Mvcs.getReq().getServerPort() == 80) {
            domain = domain + path;
        } else {
            domain = domain + ":" + Mvcs.getReq().getServerPort() + path;
        }

        return domain;
    }

    public static String getIp() {
        HttpServletRequest request = Mvcs.getReq();
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException var4) {
                    var4.printStackTrace();
                }

                ipAddress = inet.getHostAddress();
            }
        }

        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return "0:0:0:0:0:0:0:1".equals(ipAddress) ? "127.0.0.1" : ipAddress;
    }


    public static String getRealPath(String filePath) {
        String classPath = getClassPath(filePath);
        if (classPath.startsWith("file")) {
            return System.getProperty("user.dir") + File.separator + filePath;
        } else {
            HttpServletRequest request = Mvcs.getReq();
            String path = request.getServletContext().getRealPath("/");
            if (!StringUtils.isEmpty(filePath)) {
                String last = path.charAt(path.length() - 1) + "";
                String frist = filePath.charAt(0) + "";
                if (last.equals(File.separator)) {
                    if (!frist.equals("\\") && !frist.equals("/")) {
                        path = path + filePath;
                    } else {
                        path = path + filePath.substring(1);
                    }
                } else if (!frist.equals("\\") && !frist.equals("/")) {
                    path = path + File.separator + filePath;
                } else {
                    path = path + filePath;
                }
            }

            return path;
        }
    }

    public static String getClassPath(String filePath) {
        String os = System.getProperty("os.name");
        String temp = null;

        try {
            temp = Class.forName("io.nutz.nutzsite.MainLauncher").getResource("").getPath() + filePath;
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        }

        return os.toLowerCase().startsWith("win") ? temp.replace("/", "\\") : temp;
    }
}
