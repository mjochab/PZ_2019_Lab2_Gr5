package ur.inf.lab2.pz.servicemanmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

@Component
public class StageConfig {

    private String appName;
    private ViewType initialView;

    private Double minWidth;
    private Double minHeight;

    private Boolean isMaximized;
    private Boolean fullScreenEnabled;


    public StageConfig(@Value("${stage.appName:APP}") String appName,
                       @Value("${stage.initialView:Dashboard}") ViewType initialView,
                       @Value("${stage.minWidth:1024}") Double minWidth,
                       @Value("${stage.minHeight:768}") Double minHeight,
                       @Value("${stage.isMaximized:true}") Boolean isMaximized,
                       @Value("${stage.fullScreenEnabled:false}") Boolean fullScreenEnabled) {
        this.appName = appName;
        this.initialView = initialView;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.isMaximized = isMaximized;
        this.fullScreenEnabled = fullScreenEnabled;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
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

    public Double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Double minWidth) {
        this.minWidth = minWidth;
    }

    public Double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }


}
