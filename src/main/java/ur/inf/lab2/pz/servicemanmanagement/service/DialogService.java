package ur.inf.lab2.pz.servicemanmanagement.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Service;

/**
 * Klasa obsługująca pojawianie się Dialogu
 */
@Service
public class DialogService {

    /**
     * Metoda obsługująca zdarzenie kliknięcia w przycisk
     */
    public void loadDialog(StackPane stackPane, Text title, Text dialogContent) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(title);
        content.setBody(dialogContent);
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}
