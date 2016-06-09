package tiddle.javafx;

import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import javafx.application.Platform;
import javafx.scene.control.Control;
import org.apache.log4j.Logger;
import tiddle.TiddleProperties;

import javax.swing.*;

/**
 * User: ben
 * Date: 10/05/2016
 * Time: 6:53 AM
 */
public class ShortcutInstaller {
    private final static Logger LOG = Logger.getLogger(ShortcutInstaller.class);

    public static void install(final WindowService windowService, final Control componentToFocus) {
        Provider provider = Provider.getCurrentProvider(false);
        final HotKeyListener hotKeyListener = hotKey -> Platform.runLater(() -> {
            windowService.restore();
            if (componentToFocus != null) {
                componentToFocus.requestFocus();
            }
        });

        final String hotkeyCombinations[] = TiddleProperties.getInstance().getHotkeysShow();
        for (String hotkeyCombination : hotkeyCombinations) {
            try {
                LOG.info("Registering hotkey " + hotkeyCombination + " for window restore.");
                provider.register(KeyStroke.getKeyStroke(hotkeyCombination), hotKeyListener);
            } catch (Throwable t) {
                LOG.info("Unable to assign hotkey: " + hotkeyCombination + " " + t.getMessage());
            }
        }
    }
}
