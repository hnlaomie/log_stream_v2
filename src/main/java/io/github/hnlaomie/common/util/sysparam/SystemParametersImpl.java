package io.github.hnlaomie.common.util.sysparam;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:laomie@163.com">laomie</a>
 * @date 2018/02/07
 */
public class SystemParametersImpl implements SystemParameters {
    // 资源管理
    private ResourceBundle bundle;

    public SystemParametersImpl(String config) {
        Locale locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle(config, locale);
    }

    @Override
    public String getStringParameter(String key) {
        Object obj = null;
        if (bundle != null) {
            try {
                obj = bundle.getObject(key);
            } catch (Exception e) {
                obj = null;
            }
        }
        return obj == null ? null : obj.toString();
    }

    @Override
    public int getIntParameter(String key) {
        String obj = getStringParameter(key);
        return obj == null ? null : Integer.parseInt(obj);
    }
}
