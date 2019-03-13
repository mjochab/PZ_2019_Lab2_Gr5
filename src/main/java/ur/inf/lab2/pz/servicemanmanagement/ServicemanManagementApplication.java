package ur.inf.lab2.pz.servicemanmanagement;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

@SpringBootApplication
public class ServicemanManagementApplication extends Application {

	private ConfigurableApplicationContext springContext;

	@Autowired
	private ViewManager viewManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(ServicemanManagementApplication.class);
		springContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		viewManager.init(primaryStage);
	}

	@Override
	public void stop() {
		springContext.stop();
	}

}
