package tiddle.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.apache.lucene.document.Document;
import tiddle.search.Search;
import tiddle.search.WikiSearchService;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * User: ben
 * Date: 8/01/15
 * Time: 7:14 AM
 */
public class TiddlePresenter implements Initializable {
    @FXML
    public Label textTitle;
    @FXML
    public Label textTags;
    @FXML
    public Label labelLogo;
    @FXML
    public BorderPane listViewResultsParentPane;
    @FXML
    public Region resultDetailsScrollPane;
    @FXML
    public SplitPane splitPane;
    @FXML
    public VBox mainPane;
    @FXML
    public BorderPane textSearchPane;
    @FXML
    public Pane whiteSpaceCover;
    @FXML
    private TextField textSearchBox;
    @FXML
    private ListView<ResultsListItem> listViewResults;
    @FXML
    private HTMLEditor htmlResultDetails;
    @FXML
    private Pane contentPane;

    @Inject
    private WikiSearchService wikiSearchService;

    @Inject
    private WindowService windowService;

    private ObservableList<ResultsListItem> listViewItems;

    private boolean contentVisible = false;
    private boolean toolbarHidden = false;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        try {
            ShortcutInstaller.install(windowService, textSearchBox);
            labelLogo.setText(">");

            listViewItems = FXCollections.observableArrayList();
            listViewResults.setItems(listViewItems);
            listViewResults.setCellFactory(listView -> new ResultsListCell(listViewResultsParentPane));
            setExpandedMode(false);
            hideToolbar();
            final String pathToHtmlResultStylesheet = getClass().getResource("/tiddle/javafx/result-style.css").toString();
            final String pathToFxStylesheet = getClass().getResource("/tiddle/javafx/tiddle.css").toString();

            htmlResultDetails.getStylesheets().add(pathToHtmlResultStylesheet);

            textSearchBox.setOnKeyReleased((KeyEvent) -> {
                hideToolbar();
                if (textSearchBox.getText() != null && textSearchBox.getText().length() > 0) {
                    final Search results = wikiSearchService.search(textSearchBox.getText());
                    if (results.getHits().length > 0) {
                        listViewItems.clear();
                        if (!contentVisible) {
                            setExpandedMode(true);
                        }
                        for (int i = 0; i < results.getHits().length; ++i) {
                            int docId = results.getHits()[i].doc;
                            Document d;
                            try {
                                d = results.getSearcher().doc(docId);
                                final String title = d.get("title");
                                final String tags = d.get("tags");
                                final String htmlContent = d.get("htmlContent");
                                final String textContent = d.get("textContent");
                                listViewItems.add(new ResultsListItem(title, tags, htmlContent, textContent));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        listViewResults.getSelectionModel().selectFirst();
                        listViewResults.getStylesheets().addAll(pathToFxStylesheet);
                    }
                }
            });


            textSearchBox.setOnKeyPressed((KeyEvent e) -> {
                if (e.getCode() == KeyCode.DOWN) {
                    listViewResults.requestFocus();
                    listViewResults.getSelectionModel().selectNext();

                } else if (e.getCode() == KeyCode.UP) {
                    listViewResults.requestFocus();
                    listViewResults.getSelectionModel().selectNext();

                } else if (e.getCode() == KeyCode.ENTER) {
                    //TODO possibly launch browser

                } else if (e.getCode() == KeyCode.ESCAPE) {
                    escapeKeyPressed();

                } else if (e.isControlDown() && !e.isShiftDown()) {
                    if (e.getCode() == KeyCode.R) {
                        wikiSearchService = wikiSearchService.rebuild();

                    } else if (e.getCode() == KeyCode.Q) {
                        windowService.exit();
                    }

                } else if (e.isControlDown() && e.isShiftDown()) {
                    if (e.getCode() == KeyCode.D) {
                        debugGui(mainPane);
                    } else if (e.getCode() == KeyCode.M) {
                        installDebugClickEventHandlerRecursivelyOnNode(mainPane);
                    }
                }
            });


            listViewResults.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    final String content = newValue.htmlContent;
                    textTitle.setText(newValue.title);
                    if (newValue.tags != null && newValue.tags.length() > 0) {
                        textTags.setText("Tags: " + String.join(" ", newValue.tags));
                    } else {
                        textTags.setText("");
                    }
                    htmlResultDetails.setHtmlText(content);
                } else {
                    htmlResultDetails.setHtmlText("");
                }
            });

            listViewResults.setOnKeyPressed((KeyEvent e) -> {
                if (e.getCode() == KeyCode.RIGHT) {
                    resultDetailsScrollPane.requestFocus();

                } else if (e.getCode() == KeyCode.ESCAPE) {
                    escapeKeyPressed();
                }
            });

            htmlResultDetails.setOnKeyPressed((KeyEvent e) -> {
                if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.ESCAPE) {
                    listViewResults.requestFocus();
                }
            });


            final WebView webView = (WebView) htmlResultDetails.lookupAll(".web-view").iterator().next();
            webView.getEngine().setUserStyleSheetLocation(pathToHtmlResultStylesheet);


            final Set<KeyCode> allowableKeyCodes = new HashSet<>();
            allowableKeyCodes.add(KeyCode.UP);
            allowableKeyCodes.add(KeyCode.LEFT);
            allowableKeyCodes.add(KeyCode.RIGHT);
            allowableKeyCodes.add(KeyCode.DOWN);
            allowableKeyCodes.add(KeyCode.PAGE_UP);
            allowableKeyCodes.add(KeyCode.PAGE_DOWN);
            allowableKeyCodes.add(KeyCode.CONTROL);
            allowableKeyCodes.add(KeyCode.ESCAPE);
            allowableKeyCodes.add(KeyCode.SHIFT);

            final Set<KeyCode> allowableControlCodes = new HashSet<>();
            allowableControlCodes.add(KeyCode.C);

            htmlResultDetails.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if (allowableKeyCodes.contains(e.getCode())
                        || (e.isControlDown() && allowableControlCodes.contains(e.getCode()))) {
                    //Allow through
                } else {
                    e.consume();
                }
            });

            htmlResultDetails.addEventFilter(KeyEvent.KEY_TYPED, e -> {
                if (allowableKeyCodes.contains(e.getCode())
                        || (e.isControlDown() && allowableControlCodes.contains(e.getCode()))) {
                    //Allow through
                } else {
                    e.consume();
                }
            });

            webView.setOnDragDropped(Event::consume);


            whiteSpaceCover.toFront();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void installDebugClickEventHandlerRecursivelyOnNode(final Node node) {
        System.out.println("Installing mouseClicked event handler on node: " + node);
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> System.out.println(node.getClass().getSimpleName()
                + " id:" + node.getId()
                + " styleClass:" + node.getStyleClass()
                + " style:" + node.getStyle()));
        if (node instanceof Parent) {
            final Parent parent = (Parent) node;
            parent.getChildrenUnmodifiable().forEach(this::installDebugClickEventHandlerRecursivelyOnNode);
        }
    }

    private void debugGui(final Node node) {
        debugGui(node, "");
    }

    private void debugGui(final Node node, String currentIndentLevel) {
        System.out.println(currentIndentLevel
                + node.getClass().getSimpleName()
                + " id:" + node.getId()
                + " styleClass:" + node.getStyleClass()
                + " style:" + node.getStyle());
        if (node instanceof Parent) {
            final Parent parent = (Parent) node;
            parent.getChildrenUnmodifiable().forEach((child) -> debugGui(child, currentIndentLevel + "    "));
        }
    }

    private void hideToolbar() {
        if (!toolbarHidden) {
            Node[] nodes = htmlResultDetails.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
            toolbarHidden = true;
        }
    }

    private void escapeKeyPressed() {
        if (contentVisible) {
            clearAndReset();
            textSearchBox.clear();
            textSearchBox.requestFocus();
        } else {
            windowService.hide();
        }
    }


    private void clearAndReset() {
        listViewItems.clear();
        htmlResultDetails.setHtmlText("");
        setExpandedMode(false);
    }

    private void setExpandedMode(final boolean visible) {
        if (visible) {
            windowService.setWidth(600);
            windowService.setHeight(500);
            textSearchPane.getStyleClass().add("expandedMode");

        } else {
            windowService.setWidth(600);
            windowService.setHeight(80);
            textSearchPane.getStyleClass().removeAll("expandedMode");

        }
        contentPane.setVisible(visible);
        contentVisible = visible;

    }
}
