package ur.inf.lab2.pz.servicemanmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

@Component
public class StageConfig {

    private String appName;
    private ViewType initialView;

    private Double defaultWidth;
    private Double maxWidth;

    private Double defaultHeight;
    private Double maxHeight;

    private Boolean isMaximized;
    private Boolean fullScreenEnabled;


    public StageConfig(@Value("${stage.appName:APP}") String appName,
                       @Value("${stage.initialView:MAIN}") ViewType initialView,
                       @Value("${stage.defaultWidth:800}") Double defaultWidth,
                       @Value("${stage.maxWidth:1920}") Double maxWidth,
                       @Value("${stage.defaultHeight:600}") Double defaultHeight,
                       @Value("${stage.maxHeight:1080}") Double maxHeight,
                       @Value("${stage.isMaximized:true}") Boolean isMaximized,
                       @Value("${stage.fullScreenEnabled:false}") Boolean fullScreenEnabled) {
        this.appName = appName;
        this.initialView = initialView;
        this.defaultWidth = defaultWidth;
        this.maxWidth = maxWidth;
        this.defaultHeight = defaultHeight;
        this.maxHeight = maxHeight;
        this.isMaximized = isMaximized;
        this.fullScreenEnabled = fullScreenEnabled;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ViewType getInitialView() {
        return initialView;
    }

    public void setInitialView(ViewType initialView) {
        this.initialView = initialView;
    }

    public Double getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(Double defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public Double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Double getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(Double defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Boolean getMaximized() {
        return isMaximized;
    }

    public void setMaximized(Boolean maximized) {
        isMaximized = maximized;
    }

    public Boolean getFullScreenEnabled() {
        return fullScreenEnabled;
    }

    public void setFullScreenEnabled(Boolean fullScreenEnabled) {
        this.fullScreenEnabled = fullScreenEnabled;
    }
}
