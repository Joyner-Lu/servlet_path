package com.joyner.entity;

import java.util.Arrays;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 陆清云 luqingyun@foresee.cn
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
public class FileItemEntity {

    private String contentType;
    private boolean isFormField = true;
    private String fieldName;
    private byte[] itemBytes;
    private String fileName;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isFormField() {
        return isFormField;
    }

    public void setFormField(boolean formField) {
        isFormField = formField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getItemString() {
        if (itemBytes != null && itemBytes.length > 0) {
            return new String(itemBytes);
        }
        return null;
    }


    public byte[] getItemBytes() {
        return itemBytes;
    }


    public void setItemBytes(byte[] itemBytes) {
        this.itemBytes = itemBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileItemEntity{" +
                "contentType='" + contentType + '\'' +
                ", isFormField=" + isFormField +
                ", fieldName='" + fieldName + '\'' +
                ", itemBytes=" + Arrays.toString(itemBytes) +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
