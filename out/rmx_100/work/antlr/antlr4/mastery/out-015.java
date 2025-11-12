package org.antlr.v4.codegen;

import org.antlr.v4.codegen.model.RuleFunction;
import org.antlr.v4.codegen.model.SerializedATN;
import org.antlr.v4.misc.Utils;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.ErrorType;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.antlr.v4.tool.ast.GrammarAST;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;
import org.stringtemplate.v4.misc.STMessage;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.misc.NotNull;

public abstract class Target {

    protected String[] targetCharValueEscape = new String[255];

    private final CodeGenerator gen;

    private final String language;

    private STGroup templates;

    protected Target(CodeGenerator gen, String language) {
        targetCharValueEscape['\n'] = "\\n";
        targetCharValueEscape['\r'] = "\\r";
        targetCharValueEscape['\t'] = "\\t";
        targetCharValueEscape['\b'] = "\\b";
        targetCharValueEscape['\f'] = "\\f";
        targetCharValueEscape['\\'] = "\\\\";
        targetCharValueEscape['\''] = "\\'";
        targetCharValueEscape['"'] = "\\\"";
        this.gen = gen;
        this.language = language;
    }

    public CodeGenerator getCodeGenerator() {
        return gen;
    }

    public String getLanguage() {
        return language;
    }

    @NotNull
    public STGroup getTemplates() {
        if (templates == null) {
            templates = loadTemplates();
        }
        return templates;
    }

    protected void genFile(Grammar g, ST outputFileST, String fileName) {
        getCodeGenerator().write(outputFileST, fileName);
    }

    protected void genListenerFile(Grammar g, ST outputFileST) {
        String fileName = getCodeGenerator().getListenerFileName();
        getCodeGenerator().write(outputFileST, fileName);
    }

    protected void genRecognizerHeaderFile(Grammar g, ST headerFileST, String extName) {
    }

    public String getTokenTypeAsTargetLabel(Grammar g, int ttype) {
        String name = g.getTokenName(ttype);
        if (Grammar.INVALID_TOKEN_NAME.equals(name)) {
            return String.valueOf(ttype);
        }
        return name;
    }

    public String[] getTokenTypesAsTargetLabels(Grammar g, int[] ttypes) {
        String[] labels = new String[ttypes.length];
        for (int i = 0; i < ttypes.length; i++) {
            labels[i] = getTokenTypeAsTargetLabel(g, ttypes[i]);
        }
        return labels;
    }

    public String getTargetStringLiteralFromString(String s, boolean quoted) {
        if (s == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        if (quoted) {
            buf.append('"');
        }
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if (c != '\'' && c < targetCharValueEscape.length && targetCharValueEscape[c] != null) {
                buf.append(targetCharValueEscape[c]);
            } else {
                buf.append((char) c);
            }
        }
        if (quoted) {
            buf.append('"');
        }
        return buf.toString();
    }

    public String getTargetStringLiteralFromString(String s) {
        return getTargetStringLiteralFromString(s, true);
    }

    public abstract String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal, boolean addQuotes);

    public abstract String encodeIntAsCharEscape(int v);

    public String getLoopLabel(GrammarAST ast) {
        return "loop" + ast.token.getTokenIndex();
    }

    public String getLoopCounter(GrammarAST ast) {
        return "cnt" + ast.token.getTokenIndex();
    }

    public String getListLabel(String label) {
        ST st = getTemplates().getInstanceOf("ListLabelName");
        st.add("label", label);
        return st.render();
    }

    
<<<<<<< commits-rmx_100/antlr/antlr4/fd3686e08b5d422367721f0cd7a93dc908c4f377/Target-97397d6.java

=======
public String getRuleFunctionContextStructName(Rule r) {
        if (r.g.isLexer()) {
            return getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = r.getBaseContext();
        return Utils.capitalize(baseName) + getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }
>>>>>>> commits-rmx_100/antlr/antlr4/1ceb2794b622f9fbec9608f540dc753d8b044e40/Target-520f3e9.java


    public String getAltLabelContextStructName(String label) {
        return Utils.capitalize(label) + getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }

    
<<<<<<< commits-rmx_100/antlr/antlr4/fd3686e08b5d422367721f0cd7a93dc908c4f377/Target-97397d6.java

=======
public String getRuleFunctionContextStructName(RuleFunction function) {
        Rule r = function.rule;
        if (r.g.isLexer()) {
            return getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = r.getBaseContext();
        return Utils.capitalize(baseName) + getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }
>>>>>>> commits-rmx_100/antlr/antlr4/1ceb2794b622f9fbec9608f540dc753d8b044e40/Target-520f3e9.java


    public String getImplicitTokenLabel(String tokenName) {
        ST st = getTemplates().getInstanceOf("ImplicitTokenLabel");
        int ttype = getCodeGenerator().g.getTokenType(tokenName);
        if (tokenName.startsWith("'")) {
            return "s" + ttype;
        }
        String text = getTokenTypeAsTargetLabel(getCodeGenerator().g, ttype);
        st.add("tokenName", text);
        return st.render();
    }

    public String getImplicitSetLabel(String id) {
        ST st = getTemplates().getInstanceOf("ImplicitSetLabel");
        st.add("id", id);
        return st.render();
    }

    public String getImplicitRuleLabel(String ruleName) {
        ST st = getTemplates().getInstanceOf("ImplicitRuleLabel");
        st.add("ruleName", ruleName);
        return st.render();
    }

    public String getElementListName(String name) {
        ST st = getTemplates().getInstanceOf("ElementListName");
        st.add("elemName", getElementName(name));
        return st.render();
    }

    public String getElementName(String name) {
        if (".".equals(name)) {
            return "_wild";
        }
        if (getCodeGenerator().g.getRule(name) != null)
            return name;
        int ttype = getCodeGenerator().g.getTokenType(name);
        if (ttype == Token.INVALID_TYPE)
            return name;
        return getTokenTypeAsTargetLabel(getCodeGenerator().g, ttype);
    }

    public int getSerializedATNSegmentLimit() {
        return Integer.MAX_VALUE;
    }

    public boolean grammarSymbolCausesIssueInGeneratedCode(GrammarAST idNode) {
        switch(idNode.getParent().getType()) {
            case ANTLRParser.ASSIGN:
                switch(idNode.getParent().getParent().getType()) {
                    case ANTLRParser.ELEMENT_OPTIONS:
                    case ANTLRParser.OPTIONS:
                        return false;
                    default:
                        break;
                }
                break;
            case ANTLRParser.AT:
            case ANTLRParser.ELEMENT_OPTIONS:
                return false;
            case ANTLRParser.LEXER_ACTION_CALL:
                if (idNode.getChildIndex() == 0) {
                    return false;
                }
                break;
            default:
                break;
        }
        return visibleGrammarSymbolCausesIssueInGeneratedCode(idNode);
    }

    protected abstract boolean visibleGrammarSymbolCausesIssueInGeneratedCode(GrammarAST idNode);

    @NotNull
    protected STGroup loadTemplates() {
        STGroup result = new STGroupFile(CodeGenerator.TEMPLATE_ROOT + "/" + getLanguage() + "/" + getLanguage() + STGroup.GROUP_FILE_EXTENSION);
        result.registerRenderer(Integer.class, new NumberRenderer());
        result.registerRenderer(String.class, new StringRenderer());
        result.setListener(new STErrorListener() {

            @Override
            public void compileTimeError(STMessage msg) {
                reportError(msg);
            }

            @Override
            public void runTimeError(STMessage msg) {
                reportError(msg);
            }

            @Override
            public void IOError(STMessage msg) {
                reportError(msg);
            }

            @Override
            public void internalError(STMessage msg) {
                reportError(msg);
            }

            private void reportError(STMessage msg) {
                getCodeGenerator().tool.errMgr.toolError(ErrorType.STRING_TEMPLATE_WARNING, msg.cause, msg.toString());
            }
        });
        return result;
    }

    public boolean wantsBaseListener() {
        return true;
    }

    public boolean wantsBaseVisitor() {
        return true;
    }

    public boolean supportsOverloadedMethods() {
        return true;
    }

    public String getRuleFunctionContextStructName(Rule r) {
        if (r.g.isLexer()) {
            return getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = r.name;
        int lfIndex = baseName.indexOf(ATNSimulator.RULE_VARIANT_DELIMITER);
        if (lfIndex >= 0) {
            baseName = baseName.substring(0, lfIndex);
        }
        return Utils.capitalize(baseName) + getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }

    public String getRuleFunctionContextStructName(RuleFunction function) {
        Rule r = function.rule;
        if (r.g.isLexer()) {
            return getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = function.variantOf != null ? function.variantOf : function.name;
        return Utils.capitalize(baseName) + getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }
}
