package ur.inf.lab2.pz.servicemanmanagement.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ur.inf.lab2.pz.servicemanmanagement.config.StageConfig;

import java.io.IOException;

@Component
public class ViewManager {

    private ApplicationContext context;
    private StageConfig stageConfig;
    private Stage stage;

    public void init(Stage stage) throws IOException {
        this.stage = stage;

		Scene scene = new Scene(
		        getView(stageConfig.getInitialView().getFXMLName()),
                        stageConfig.getMinWidth(),
                        stageConfig.getMinHeight());
		this.stage.setScene(scene);

        this.stage.setTitle(stageConfig.getAppName());
        this.stage.setMinWidth(stageConfig.getMinWidth());
        this.stage.setMinHeight(stageConfig.getMinHeight());
        this.stage.setMaximized(stageConfig.getMaximized());
        this.stage.setResizable(false);
		this.stage.setFullScreen(stageConfig.getFullScreenEnabled());

		this.stage.show();
    }

    public void show(ViewType viewType) throws IOException {
        Parent view = getView(viewType.getFXMLName());
        stage.getScene().setRoot(view);
    }

    private Parent getView(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource("/fxml/"+viewName+".fxml"));

        return (Parent) loader.load();
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setStageConfig(StageConfig stageConfig) {
        this.stageConfig = stageConfig;
    }
}
