package org.antlr.v4.test;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATNConfig;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPerformance extends BaseTest {

    private static final String TOP_PACKAGE = "java.lang";

    private static final boolean RECURSIVE = true;

    private static final boolean USE_LR_GRAMMAR = true;

    private static final boolean FORCE_ATN = false;

    private static final boolean EXPORT_ATN_GRAPHS = true;

    private static final boolean DELETE_TEMP_FILES = true;

    private static final boolean PAUSE_FOR_HEAP_DUMP = false;

    private static final boolean RUN_PARSER = true;

    private static final boolean BAIL_ON_ERROR = true;

    private static final boolean BUILD_PARSE_TREES = false;

    private static final boolean BLANK_LISTENER = false;

    private static final boolean SHOW_DFA_STATE_STATS = true;

    private static final boolean SHOW_CONFIG_STATS = false;

    private static final boolean REUSE_LEXER = true;

    private static final boolean REUSE_PARSER = true;

    private static final boolean CLEAR_DFA = false;

    private static final int PASSES = 4;

    private static Lexer sharedLexer;

    private static Parser sharedParser;

    @SuppressWarnings({ "FieldCanBeLocal" })
    private static ParseTreeListener<Token> sharedListener;

    private int tokenCount;

    private int currentPass;

    @Test
    public void compileJdk() throws IOException {
        String jdkSourceRoot = getSourceRoot("JDK");
        assertTrue("The JDK_SOURCE_ROOT environment variable must be set for performance testing.", jdkSourceRoot != null && !jdkSourceRoot.isEmpty());
        compileJavaParser(USE_LR_GRAMMAR);
        final String lexerName = "JavaLexer";
        final String parserName = "JavaParser";
        final String listenerName = "JavaBaseListener";
        final String entryPoint = "compilationUnit";
        ParserFactory factory = getParserFactory(lexerName, parserName, listenerName, entryPoint);
        if (!TOP_PACKAGE.isEmpty()) {
            jdkSourceRoot = jdkSourceRoot + '/' + TOP_PACKAGE.replace('.', '/');
        }
        File directory = new File(jdkSourceRoot);
        assertTrue(directory.isDirectory());
        Collection<CharStream> sources = loadSources(directory, new FileExtensionFilenameFilter(".java"), RECURSIVE);
        System.out.print(getOptionsDescription(TOP_PACKAGE));
        currentPass = 0;
        parse1(factory, sources);
        for (int i = 0; i < PASSES - 1; i++) {
            currentPass = i + 1;
            if (CLEAR_DFA) {
                sharedLexer = null;
                sharedParser = null;
            }
            parse2(factory, sources);
        }
        sources.clear();
        if (PAUSE_FOR_HEAP_DUMP) {
            System.gc();
            System.out.println("Pausing before application exit.");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestPerformance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String getSourceRoot(String prefix) {
        String sourceRoot = System.getenv(prefix + "_SOURCE_ROOT");
        if (sourceRoot == null) {
            sourceRoot = System.getProperty(prefix + "_SOURCE_ROOT");
        }
        return sourceRoot;
    }

    @Override
    protected void eraseTempDir() {
        if (DELETE_TEMP_FILES) {
            super.eraseTempDir();
        }
    }

    static public String getOptionsDescription(String topPackage) {
        StringBuilder builder = new StringBuilder();
        builder.append("Input=");
        if (topPackage.isEmpty()) {
            builder.append("*");
        } else {
            builder.append(topPackage).append(".*");
        }
        builder.append(", Grammar=").append(USE_LR_GRAMMAR ? "LR" : "Standard");
        builder.append(", ForceAtn=").append(FORCE_ATN);
        builder.append('\n');
        builder.append("Op=Lex").append(RUN_PARSER ? "+Parse" : " only");
        builder.append(", Strategy=").append(BAIL_ON_ERROR ? BailErrorStrategy.class.getSimpleName() : DefaultErrorStrategy.class.getSimpleName());
        builder.append(", BuildParseTree=").append(BUILD_PARSE_TREES);
        builder.append(", WalkBlankListener=").append(BLANK_LISTENER);
        builder.append('\n');
        builder.append("Lexer=").append(REUSE_LEXER ? "setInputStream" : "newInstance");
        builder.append(", Parser=").append(REUSE_PARSER ? "setInputStream" : "newInstance");
        builder.append(", AfterPass=").append(CLEAR_DFA ? "newInstance" : "setInputStream");
        builder.append('\n');
        return builder.toString();
    }

    protected void parse1(ParserFactory factory, Collection<CharStream> sources) {
        System.gc();
        parseSources(factory, sources);
    }

    protected void parse2(ParserFactory factory, Collection<CharStream> sources) {
        System.gc();
        parseSources(factory, sources);
    }

    protected Collection<CharStream> loadSources(File directory, FilenameFilter filter, boolean recursive) {
        return loadSources(directory, filter, null, recursive);
    }

    protected Collection<CharStream> loadSources(File directory, FilenameFilter filter, String encoding, boolean recursive) {
        Collection<CharStream> result = new ArrayList<CharStream>();
        loadSources(directory, filter, encoding, recursive, result);
        return result;
    }

    protected void loadSources(File directory, FilenameFilter filter, String encoding, boolean recursive, Collection<CharStream> result) {
        assert directory.isDirectory();
        File[] sources = directory.listFiles(filter);
        for (File file : sources) {
            try {
                CharStream input = new ANTLRFileStream(file.getAbsolutePath(), encoding);
                result.add(input);
            } catch (IOException ex) {
            }
        }
        if (recursive) {
            File[] children = directory.listFiles();
            for (File child : children) {
                if (child.isDirectory()) {
                    loadSources(child, filter, encoding, true, result);
                }
            }
        }
    }

    int configOutputSize = 0;

    protected void parseSources(ParserFactory factory, Collection<CharStream> sources) {
        long startTime = System.currentTimeMillis();
        tokenCount = 0;
        int inputSize = 0;
        for (CharStream input : sources) {
            input.seek(0);
            inputSize += input.size();
            try {
                factory.parseFile(input);
            } catch (IllegalStateException ex) {
                ex.printStackTrace(System.out);
            }
        }
        System.out.format("Total parse time for %d files (%d KB, %d tokens): %dms\n", sources.size(), inputSize / 1024, tokenCount, System.currentTimeMillis() - startTime);
        final LexerATNSimulator lexerInterpreter = sharedLexer.getInterpreter();
        final DFA[] modeToDFA = lexerInterpreter.decisionToDFA;
        if (SHOW_DFA_STATE_STATS) {
            int states = 0;
            int configs = 0;
            Set<ATNConfig> uniqueConfigs = new HashSet<ATNConfig>();
            for (int i = 0; i < modeToDFA.length; i++) {
                DFA dfa = modeToDFA[i];
                if (dfa == null || dfa.states == null) {
                    continue;
                }
                states += dfa.states.size();
                for (DFAState state : dfa.states.values()) {
                    configs += state.configs.size();
                    uniqueConfigs.addAll(state.configs);
                }
            }
            System.out.format("There are %d lexer DFAState instances, %d configs (%d unique).\n", states, configs, uniqueConfigs.size());
        }
        if (RUN_PARSER) {
            final ParserATNSimulator interpreter = sharedParser.getInterpreter();
            final DFA[] decisionToDFA = interpreter.decisionToDFA;
            if (SHOW_DFA_STATE_STATS) {
                int states = 0;
                int configs = 0;
                Set<ATNConfig> uniqueConfigs = new HashSet<ATNConfig>();
                for (int i = 0; i < decisionToDFA.length; i++) {
                    DFA dfa = decisionToDFA[i];
                    if (dfa == null || dfa.states == null) {
                        continue;
                    }
                    states += dfa.states.size();
                    for (DFAState state : dfa.states.values()) {
                        configs += state.configs.size();
                        uniqueConfigs.addAll(state.configs);
                    }
                }
                System.out.format("There are %d parser DFAState instances, %d configs (%d unique).\n", states, configs, uniqueConfigs.size());
            }
            int localDfaCount = 0;
            int globalDfaCount = 0;
            int localConfigCount = 0;
            int globalConfigCount = 0;
            int[] contextsInDFAState = new int[0];
            for (int i = 0; i < decisionToDFA.length; i++) {
                DFA dfa = decisionToDFA[i];
                if (dfa == null || dfa.states == null) {
                    continue;
                }
                if (SHOW_CONFIG_STATS) {
                    for (DFAState state : dfa.states.keySet()) {
                        if (state.configs.size() >= contextsInDFAState.length) {
                            contextsInDFAState = Arrays.copyOf(contextsInDFAState, state.configs.size() + 1);
                        }
                        if (state.isAcceptState) {
                            boolean hasGlobal = false;
                            for (ATNConfig config : state.configs) {
                                if (config.reachesIntoOuterContext > 0) {
                                    globalConfigCount++;
                                    hasGlobal = true;
                                } else {
                                    localConfigCount++;
                                }
                            }
                            if (hasGlobal) {
                                globalDfaCount++;
                            } else {
                                localDfaCount++;
                            }
                        }
                        contextsInDFAState[state.configs.size()]++;
                    }
                }
            }
            if (SHOW_CONFIG_STATS && currentPass == 0) {
                System.out.format("  DFA accept states: %d total, %d with only local context, %d with a global context\n", localDfaCount + globalDfaCount, localDfaCount, globalDfaCount);
                System.out.format("  Config stats: %d total, %d local, %d global\n", localConfigCount + globalConfigCount, localConfigCount, globalConfigCount);
                if (SHOW_DFA_STATE_STATS) {
                    for (int i = 0; i < contextsInDFAState.length; i++) {
                        if (contextsInDFAState[i] != 0) {
                            System.out.format("  %d configs = %d\n", i, contextsInDFAState[i]);
                        }
                    }
                }
            }
        }
    }

    protected void compileJavaParser(boolean leftRecursive) throws IOException {
        String grammarFileName = "Java.g4";
        String sourceName = leftRecursive ? "Java-LR.g4" : "Java.g4";
        String body = load(sourceName, null);
        @SuppressWarnings({ "ConstantConditions" })
        List<String> extraOptions = new ArrayList<String>();
        extraOptions.add("-Werror");
        if (FORCE_ATN) {
            extraOptions.add("-Xforce-atn");
        }
        if (EXPORT_ATN_GRAPHS) {
            extraOptions.add("-atn");
        }
        String[] extraOptionsArray = extraOptions.toArray(new String[extraOptions.size()]);
        boolean success = rawGenerateAndBuildRecognizer(grammarFileName, body, "JavaParser", "JavaLexer", true, extraOptionsArray);
        assertTrue(success);
    }

    protected String load(String fileName, @Nullable String encoding) throws IOException {
        if (fileName == null) {
            return null;
        }
        String fullFileName = getClass().getPackage().getName().replace('.', '/') + '/' + fileName;
        int size = 65000;
        InputStreamReader isr;
        InputStream fis = getClass().getClassLoader().getResourceAsStream(fullFileName);
        if (encoding != null) {
            isr = new InputStreamReader(fis, encoding);
        } else {
            isr = new InputStreamReader(fis);
        }
        try {
            char[] data = new char[size];
            int n = isr.read(data);
            return new String(data, 0, n);
        } finally {
            isr.close();
        }
    }

    protected ParserFactory getParserFactory(String lexerName, String parserName, String listenerName, final String entryPoint) {
        try {
            ClassLoader loader = new URLClassLoader(new URL[] { new File(tmpdir).toURI().toURL() }, ClassLoader.getSystemClassLoader());
            final Class<? extends Lexer> lexerClass = loader.loadClass(lexerName).asSubclass(Lexer.class);
            final Class<? extends Parser> parserClass = loader.loadClass(parserName).asSubclass(Parser.class);
            @SuppressWarnings({ "unchecked" })
            final Class<? extends ParseTreeListener<Token>> listenerClass = (Class<? extends ParseTreeListener<Token>>) loader.loadClass(listenerName);
            TestPerformance.sharedListener = listenerClass.newInstance();
            final Constructor<? extends Lexer> lexerCtor = lexerClass.getConstructor(CharStream.class);
            final Constructor<? extends Parser> parserCtor = parserClass.getConstructor(TokenStream.class);
            lexerCtor.newInstance(new ANTLRInputStream(""));
            parserCtor.newInstance(new CommonTokenStream());
            return new ParserFactory() {

                @SuppressWarnings({ "PointlessBooleanExpression" })
                @Override
                public void parseFile(CharStream input) {
                    try {
                        if (REUSE_LEXER && sharedLexer != null) {
                            sharedLexer.setInputStream(input);
                        } else {
                            sharedLexer = lexerCtor.newInstance(input);
                        }
                        CommonTokenStream tokens = new CommonTokenStream(sharedLexer);
                        tokens.fill();
                        tokenCount += tokens.size();
                        if (!RUN_PARSER) {
                            return;
                        }
                        if (REUSE_PARSER && sharedParser != null) {
                            sharedParser.setInputStream(tokens);
                        } else {
                            sharedParser = parserCtor.newInstance(tokens);
                            sharedParser.addErrorListener(DescriptiveErrorListener.INSTANCE);
                            sharedParser.setBuildParseTree(BUILD_PARSE_TREES);
                            if (!BUILD_PARSE_TREES && BLANK_LISTENER) {
                            }
                            if (BAIL_ON_ERROR) {
                                sharedParser.setErrorHandler(new BailErrorStrategy());
                            }
                        }
                        Method parseMethod = parserClass.getMethod(entryPoint);
                        Object parseResult = parseMethod.invoke(sharedParser);
                        Assert.assertTrue(parseResult instanceof ParseTree);
                        if (BUILD_PARSE_TREES && BLANK_LISTENER) {
                            ParseTreeWalker.DEFAULT.walk(sharedListener, (ParseTree) parseResult);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                        throw new IllegalStateException(e);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace(System.out);
            lastTestFailed = true;
            Assert.fail(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    protected interface ParserFactory {

        void parseFile(CharStream input);
    }

    private static class DescriptiveErrorListener extends BaseErrorListener {

        static public DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            String sourceName = recognizer.getInputStream().getSourceName();
            sourceName = sourceName != null && !sourceName.isEmpty() ? sourceName + ": " : "";
            System.err.println(sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
        }
    }

    static protected class FileExtensionFilenameFilter implements FilenameFilter {

        private final String extension;

        public FileExtensionFilenameFilter(String extension) {
            if (!extension.startsWith(".")) {
                extension = '.' + extension;
            }
            this.extension = extension;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(extension);
        }
    }
}
