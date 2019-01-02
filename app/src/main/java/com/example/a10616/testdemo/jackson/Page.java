package com.example.a10616.testdemo.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, // 指定 类型识别码 是一个名称
        include = JsonTypeInfo.As.PROPERTY, // 指定识别码是如何被包含进去的 ,property :作为数据的兄弟属性
        property = "type", // 指定识别码的名称
        visible = true // 识别码是否可见,默认是false,如果是false,则jackson会从JSON内容中处理和删除类型标识符再传递给JsonDeserializer。
)
//用来表明这个父类的子类型有哪些。
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputPage.class, name = "input"),
        @JsonSubTypes.Type(value = NumberPage.class, name = "number")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    private String type;
    private String name;
    private String uiType;
    private String label;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
