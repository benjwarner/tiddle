package tiddle.javafx;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * User: ben
 * Date: 28/04/2016
 * Time: 5:37 PM
 */
public class ResultsListCell extends ListCell<ResultsListItem> {
    private final Region parent;

    public ResultsListCell(final BorderPane parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(final ResultsListItem item, final boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null){
            setText("");
        } else {
            setText(item.title);
            setTextOverrun(OverrunStyle.ELLIPSIS);
            prefWidthProperty().bind(parent.widthProperty().subtract(2));
            setMaxWidth(Control.USE_PREF_SIZE);
        }
    }
}
