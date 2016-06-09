package tiddle.javafx;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * User: ben
 * Date: 4/05/2016
 * Time: 7:17 PM
 */
public class WindowService {
    private final Stage stage;

    public WindowService(final Stage stage) {
        this.stage = stage;
    }

    public void setWidth(final double width) {
        stage.setWidth(width);
    }

    public void setHeight(final double height) {
        stage.setHeight(height);
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }

    public void hide() {
        stage.setIconified(true);
    }

    public void restore() {
        stage.setIconified(false);
        stage.requestFocus();
    }
}
