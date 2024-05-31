package org.carlfx.cognitive.test.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.carlfx.cognitive.loader.Config;
import org.carlfx.cognitive.loader.FXMLMvvmLoader;
import org.carlfx.cognitive.loader.JFXNode;
import org.carlfx.cognitive.loader.NamedVm;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Demo AccountViewModel");
        JFXNode<Pane, AccountCreateController> jfxNode = FXMLMvvmLoader.make(this.getClass().getResource("account-create.fxml"));
        Scene scene = new Scene(jfxNode.node());
        stage.setScene(scene);
        stage.show();
    }
}
