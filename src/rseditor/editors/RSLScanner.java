package rseditor.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;

public class RSLScanner extends RuleBasedScanner {
	public RSLScanner(ColorManager manager) {
		IToken stringLiteral = new Token(new TextAttribute(
				manager.getColor(IRSLColorConstants.STRING)));

		IToken collection = new Token(new TextAttribute(
				manager.getColor(IRSLColorConstants.COLLECTION)));
		
		IToken bool = new Token(new TextAttribute(
				manager.getColor(IRSLColorConstants.BOOL_STR)));

		IRule[] rules = new IRule[4];
		// Add rule for processing instructions
		rules[0] = new SingleLineRule("\"", "\"", stringLiteral);
		rules[1] = new SingleLineRule("'", "'", stringLiteral);
		
		String[] boolKeywords = {"true", "false", "nil"};
		rules[2] = new RSLWordRule(bool, boolKeywords);

		String[] collectionKeywords = {"methods", "classes", "projects", "fields", "members", "parameters"};
		rules[3] = new RSLWordRule(collection, collectionKeywords);
		
		setRules(rules);
	}
}
