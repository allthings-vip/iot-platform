package allthings.iot.dos.web.xss;

import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author :  luhao
 * @FileName :  CustomStringEditor
 * @CreateDate :  2018-9-26
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class CustomStringEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "";
        }
        // xss过滤，表单提交时封装参数，String类型会经过此处
        text = HtmlUtils.htmlEscape(text, "utf-8");
        setValue(text);
    }
}