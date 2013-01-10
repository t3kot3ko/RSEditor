package rseditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class RSLConfiguration extends SourceViewerConfiguration {
	private RSLDoubleClickStrategy doubleClickStrategy;
	private RSLTagScanner tagScanner;
	private RSLScanner scanner;
	private ColorManager colorManager;

	public RSLConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			RSLPartitionScanner.XML_COMMENT,
			RSLPartitionScanner.XML_TAG };
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new RSLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected RSLScanner getXMLScanner() {
		if (scanner == null) {
			scanner = new RSLScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IRSLColorConstants.DEFAULT))));
		}
		return scanner;
	}
	protected RSLTagScanner getXMLTagScanner() {
		if (tagScanner == null) {
			tagScanner = new RSLTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IRSLColorConstants.TAG))));
		}
		return tagScanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, RSLPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, RSLPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IRSLColorConstants.COMMENT)));
		reconciler.setDamager(ndr, RSLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, RSLPartitionScanner.XML_COMMENT);

		return reconciler;
	}

}