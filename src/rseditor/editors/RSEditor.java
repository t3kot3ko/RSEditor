package rseditor.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class RSEditor extends TextEditor {

	private ColorManager colorManager;

	public RSEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new RSLConfiguration(colorManager));
		setDocumentProvider(new RSLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
