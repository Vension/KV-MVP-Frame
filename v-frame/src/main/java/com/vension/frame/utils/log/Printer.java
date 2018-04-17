package com.vension.frame.utils.log;

import java.util.List;
import java.util.Map;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:16
 * 描  述：
 * ========================================================
 */

public interface Printer {

    VLogConfig init();

    String getFormatLog();

    void d(String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void xml(String xml);

    void map(Map map);

    void list(List list);
}
