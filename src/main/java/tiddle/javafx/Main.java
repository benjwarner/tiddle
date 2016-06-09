package tiddle.javafx;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tiddle.TiddleProperties;
import tiddle.search.WikiSearchService;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String wikiFilePaths[] = TiddleProperties.getInstance().getWikiFilePaths();
        WikiSearchService wikiSearchService = WikiSearchService.build(wikiFilePaths);
        Injector.setModelOrService(WikiSearchService.class, wikiSearchService);
        Injector.setModelOrService(WindowService.class, new WindowService(primaryStage));

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        final TiddleView mainView = new TiddleView();
        final Scene scene = new Scene( mainView.getView() );
        scene.getStylesheets().add("tiddle/javafx/tiddle.css");
        scene.setFill(Color.TRANSPARENT);

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tiddle.png")));
        primaryStage.setTitle("tiddle");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                primaryStage.requestFocus();
            }
        });
    }
}
