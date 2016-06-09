package tiddle.util;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;


public class CompositeAction extends AbstractAction {
	private static final long serialVersionUID = -2342710083066232067L;
	private List<Action> actions;
	
	public CompositeAction(Action ... actions){
		this.actions = Arrays.asList(actions);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(Action action: actions){
			action.actionPerformed(e);
		}
	}
}
