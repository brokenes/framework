package com.github.framework.sensitive.test.model.sensitive.system;

import com.github.framework.sensitive.test.annotation.SensitiveErrorSystemBuiltIn;

/**
 * 模拟错误的注解使用
 */
public class SensitiveErrorSystemBuiltInModel {

    @SensitiveErrorSystemBuiltIn
    private String errorField;

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }

    @Override
    public String toString() {
        return "SensitiveErrorSystemBuiltInModel{" +
                "errorField='" + errorField + '\'' +
                '}';
    }

}
