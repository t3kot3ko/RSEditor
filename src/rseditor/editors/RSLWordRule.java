package rseditor.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

public class RSLWordRule extends WordRule implements IPredicateRule {
	private IToken fSuccessToken;

	public RSLWordRule(IToken successToken, String[] keywords) {
		super(new RSLWordDetector());
		fSuccessToken = successToken;
		for (String keyword : keywords) {
			addWord(keyword, successToken);
		}
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}
	
	@Override
	public IToken getSuccessToken() {
		return fSuccessToken;
	}

	static class RSLWordDetector implements IWordDetector {

		@Override
		public boolean isWordStart(char c) {
			return Character.isLetter(c);
		}

		@Override
		public boolean isWordPart(char c) {
			return Character.isLetter(c);
		}

	}

}
