package tiddle.javafx;

import javafx.css.Styleable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


/**
 * User: ben
 * Date: 13/06/2016
 * Time: 5:56 PM
 */
public class ExpandCollapseHelper {
    private final Stage stage;
    private final Pane contentPane;
    private Styleable textSearchPane;
    private boolean contentVisible = false;
    private double expandedHeight = 500;
    private double collapsedHeight = 80;

    public boolean isContentVisible() {
        return contentVisible;
    }

    public ExpandCollapseHelper(final Stage stage, Styleable textSearchPane, Pane contentPane) {
        this.contentPane = contentPane;
        this.stage = stage;
        this.textSearchPane = textSearchPane;
    }

    protected void setExpandedMode(final boolean visible) {
        if (visible) {
            stage.setHeight(expandedHeight);
            textSearchPane.getStyleClass().add("expandedMode");

        } else {
            stage.setHeight(collapsedHeight);
            textSearchPane.getStyleClass().removeAll("expandedMode");

        }
        contentPane.setVisible(visible);
        contentVisible = visible;
    }
}
