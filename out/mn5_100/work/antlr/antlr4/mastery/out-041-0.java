package org.antlr.v4.analysis;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.v4.Tool;
import org.antlr.v4.codegen.CodeGenerator;
import org.antlr.v4.parse.GrammarASTAdaptor;
import org.antlr.v4.parse.LeftRecursiveRuleWalker;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.tool.ErrorType;
import org.antlr.v4.tool.ast.AltAST;
import org.antlr.v4.tool.ast.GrammarAST;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.misc.IntervalSet;

public class LeftRecursiveRuleAnalyzer extends LeftRecursiveRuleWalker {

    public static enum ASSOC {

        left, right
    }

    public Tool tool;

    public String ruleName;

    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> binaryAlts = new LinkedHashMap<Integer, LeftRecursiveRuleAltInfo>();

    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> ternaryAlts = new LinkedHashMap<Integer, LeftRecursiveRuleAltInfo>();

    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> suffixAlts = new LinkedHashMap<Integer, LeftRecursiveRuleAltInfo>();

    public List<LeftRecursiveRuleAltInfo> prefixAlts = new ArrayList<LeftRecursiveRuleAltInfo>();

    public List<LeftRecursiveRuleAltInfo> otherAlts = new ArrayList<LeftRecursiveRuleAltInfo>();

    public List<Pair<GrammarAST, String>> leftRecursiveRuleRefLabels = new ArrayList<Pair<GrammarAST, String>>();

    public final TokenStream tokenStream;

    public GrammarAST retvals;

    public STGroup recRuleTemplates;

    public STGroup codegenTemplates;

    public String language;

    public Map<Integer, ASSOC> altAssociativity = new HashMap<Integer, ASSOC>();

    public LeftRecursiveRuleAnalyzer(GrammarAST ruleAST, Tool tool, String ruleName, String language) {
        super(new CommonTreeNodeStream(new GrammarASTAdaptor(ruleAST.token.getInputStream()), ruleAST));
        this.tool = tool;
        this.ruleName = ruleName;
        this.language = language;
        this.tokenStream = ruleAST.g.tokenStream;
        if (this.tokenStream == null) {
            throw new NullPointerException("grammar must have a token stream");
        }
        loadPrecRuleTemplates();
    }

    public void loadPrecRuleTemplates() {
        String templateGroupFile = "org/antlr/v4/tool/templates/LeftRecursiveRules.stg";
        recRuleTemplates = new STGroupFile(templateGroupFile);
        if (!recRuleTemplates.isDefined("recRule")) {
            tool.errMgr.toolError(ErrorType.MISSING_CODE_GEN_TEMPLATES, "LeftRecursiveRules");
        }
        CodeGenerator gen = new CodeGenerator(tool, null, language);
        codegenTemplates = gen.getTemplates();
    }

    @Override
    public void setReturnValues(GrammarAST t) {
        retvals = t;
    }

    @Override
    public void setAltAssoc(AltAST t, int alt) {
        ASSOC assoc = ASSOC.left;
        if (t.getOptions() != null) {
            String a = t.getOptionString("assoc");
            if (a != null) {
                if (a.equals(ASSOC.right.toString())) {
                    assoc = ASSOC.right;
                } else if (a.equals(ASSOC.left.toString())) {
                    assoc = ASSOC.left;
                } else {
                    tool.errMgr.toolError(ErrorType.ILLEGAL_OPTION_VALUE, "assoc", assoc);
                }
            }
        }
        if (altAssociativity.get(alt) != null && altAssociativity.get(alt) != assoc) {
            tool.errMgr.toolError(ErrorType.ALL_OPS_NEED_SAME_ASSOC, alt);
        }
        altAssociativity.put(alt, assoc);
    }

    @Override
    public void binaryAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST) originalAltTree.dupTree();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        GrammarAST lrlabel = stripLeftRecursion(altTree);
        String label = lrlabel != null ? lrlabel.getText() : null;
        if (lrlabel != null) {
            leftRecursiveRuleRefLabels.add(new Pair<GrammarAST, String>(lrlabel, altLabel));
        }
        stripAssocOptions(altTree);
        stripAltLabel(altTree);
        int nextPrec = nextPrecedence(alt);
        altTree = addPrecedenceArgToRules(altTree, nextPrec);
        stripAltLabel(altTree);
        String altText = text(altTree);
        altText = altText.trim();
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, label, altLabel, originalAltTree);
        a.nextPrec = nextPrec;
        binaryAlts.put(alt, a);
    }

    @Override
    public void prefixAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST) originalAltTree.dupTree();
        stripAltLabel(altTree);
        int nextPrec = precedence(alt);
        altTree = addPrecedenceArgToRules(altTree, nextPrec);
        String altText = text(altTree);
        altText = altText.trim();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, null, altLabel, originalAltTree);
        a.nextPrec = nextPrec;
        prefixAlts.add(a);
    }

    @Override
    public void suffixAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST) originalAltTree.dupTree();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        GrammarAST lrlabel = stripLeftRecursion(altTree);
        String label = lrlabel != null ? lrlabel.getText() : null;
        if (lrlabel != null) {
            leftRecursiveRuleRefLabels.add(new Pair<GrammarAST, String>(lrlabel, altLabel));
        }
        stripAltLabel(altTree);
        String altText = text(altTree);
        altText = altText.trim();
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, label, altLabel, originalAltTree);
        suffixAlts.put(alt, a);
    }

    @Override
    public void otherAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST) originalAltTree.dupTree();
        stripAltLabel(altTree);
        String altText = text(altTree);
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, null, altLabel, originalAltTree);
        otherAlts.add(a);
    }

    public String getArtificialOpPrecRule() {
        ST ruleST = recRuleTemplates.getInstanceOf("recRule");
        ruleST.add("ruleName", ruleName);
        ST ruleArgST = codegenTemplates.getInstanceOf("recRuleArg");
        ruleST.add("argName", ruleArgST);
        ST setResultST = codegenTemplates.getInstanceOf("recRuleSetResultAction");
        ruleST.add("setResultAction", setResultST);
        ruleST.add("userRetvals", retvals);
        LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> opPrecRuleAlts = new LinkedHashMap<Integer, LeftRecursiveRuleAltInfo>();
        opPrecRuleAlts.putAll(binaryAlts);
        opPrecRuleAlts.putAll(ternaryAlts);
        opPrecRuleAlts.putAll(suffixAlts);
        for (int alt : opPrecRuleAlts.keySet()) {
            LeftRecursiveRuleAltInfo altInfo = opPrecRuleAlts.get(alt);
            ST altST = recRuleTemplates.getInstanceOf("recRuleAlt");
            ST predST = codegenTemplates.getInstanceOf("recRuleAltPredicate");
            predST.add("opPrec", precedence(alt));
            predST.add("ruleName", ruleName);
            altST.add("pred", predST);
            altST.add("alt", altInfo);
            altST.add("precOption", LeftRecursiveRuleTransformer.PRECEDENCE_OPTION_NAME);
            altST.add("opPrec", precedence(alt));
            ruleST.add("opAlts", altST);
        }
        ruleST.add("primaryAlts", prefixAlts);
        ruleST.add("primaryAlts", otherAlts);
        tool.log("left-recursion", ruleST.render());
        return ruleST.render();
    }

    public AltAST addPrecedenceArgToRules(AltAST t, int prec) {
        if (t == null)
            return null;
        List<GrammarAST> outerAltRuleRefs = t.getNodesWithTypePreorderDFS(IntervalSet.of(RULE_REF));
        for (GrammarAST rref : outerAltRuleRefs) {
            
if (rref.getText().equals(ruleName)) {
                rref.setText(ruleName + "<" + LeftRecursiveRuleTransformer.PRECEDENCE_OPTION_NAME + "=" + prec + ">");
            }

            boolean rightmost = rref == outerAltRuleRefs.get(outerAltRuleRefs.size() - 1);
            if (recursive && rightmost) {
                rref.setText(ruleName + "[" + prec + "]");
            }
        }
        return t;
    }

    
public AltAST addPrecedenceArgToLastRule(AltAST t, int prec) {
        if (t == null)
            return null;
        GrammarAST last = null;
        for (GrammarAST rref : t.getNodesWithType(RULE_REF)) {
            last = rref;
        }
        if (last != null && last.getText().equals(ruleName)) {
            last.setText(ruleName + "<" + LeftRecursiveRuleTransformer.PRECEDENCE_OPTION_NAME + "=" + prec + ">");
        }
        return t;
    }


    public void stripAssocOptions(GrammarAST t) {
        if (t == null)
            return;
        for (GrammarAST options : t.getNodesWithType(ELEMENT_OPTIONS)) {
            int i = 0;
            while (i < options.getChildCount()) {
                GrammarAST c = (GrammarAST) options.getChild(i);
                if (c.getChild(0).getText().equals("assoc")) {
                    options.deleteChild(i);
                } else {
                    i++;
                }
            }
            if (options.getChildCount() == 0) {
                Tree parent = options.getParent();
                parent.deleteChild(options.getChildIndex());
                return;
            }
        }
    }

    public static boolean hasImmediateRecursiveRuleRefs(GrammarAST t, String ruleName) {
        if (t == null)
            return false;
        GrammarAST blk = (GrammarAST) t.getFirstChildWithType(BLOCK);
        if (blk == null)
            return false;
        int n = blk.getChildren().size();
        for (int i = 0; i < n; i++) {
            GrammarAST alt = (GrammarAST) blk.getChildren().get(i);
            Tree first = alt.getChild(0);
            if (first == null)
                continue;
            if (first.getType() == RULE_REF && first.getText().equals(ruleName))
                return true;
            Tree rref = first.getChild(1);
            if (rref != null && rref.getType() == RULE_REF && rref.getText().equals(ruleName))
                return true;
        }
        return false;
    }

    public GrammarAST stripLeftRecursion(GrammarAST altAST) {
        GrammarAST lrlabel = null;
        GrammarAST first = (GrammarAST) altAST.getChild(0);
        int leftRecurRuleIndex = 0;
        if (first.getType() == ELEMENT_OPTIONS) {
            first = (GrammarAST) altAST.getChild(1);
            leftRecurRuleIndex = 1;
        }
        Tree rref = first.getChild(1);
        if ((first.getType() == RULE_REF && first.getText().equals(ruleName)) || (rref != null && rref.getType() == RULE_REF && rref.getText().equals(ruleName))) {
            if (first.getType() == ASSIGN)
                lrlabel = (GrammarAST) first.getChild(0);
            altAST.deleteChild(leftRecurRuleIndex);
            GrammarAST newFirstChild = (GrammarAST) altAST.getChild(leftRecurRuleIndex);
            altAST.setTokenStartIndex(newFirstChild.getTokenStartIndex());
        }
        return lrlabel;
    }

    public void stripAltLabel(GrammarAST altAST) {
        int start = altAST.getTokenStartIndex();
        int stop = altAST.getTokenStopIndex();
        for (int i = stop; i >= start; i--) {
            if (tokenStream.get(i).getType() == POUND) {
                altAST.setTokenStopIndex(i - 1);
                return;
            }
        }
    }

    public String text(GrammarAST t) {
        if (t == null)
            return "";
        CommonToken ta = (CommonToken) tokenStream.get(t.getTokenStartIndex());
        CommonToken tb = (CommonToken) tokenStream.get(t.getTokenStopIndex());
        return tokenStream.toString(ta, tb);
    }

    public int precedence(int alt) {
        return numAlts - alt + 1;
    }

    public int nextPrecedence(int alt) {
        int p = precedence(alt);
        if (altAssociativity.get(alt) == ASSOC.right)
            return p;
        return p + 1;
    }

    @Override
    public String toString() {
        return "PrecRuleOperatorCollector{" + "binaryAlts=" + binaryAlts + ", ternaryAlts=" + ternaryAlts + ", suffixAlts=" + suffixAlts + ", prefixAlts=" + prefixAlts + ", otherAlts=" + otherAlts + '}';
    }
}
