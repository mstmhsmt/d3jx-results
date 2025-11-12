package lombok.javac;

import java.util.regex.Pattern;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import java.util.regex.Matcher;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import java.util.Objects;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.main.JavaCompiler;

public class Javac {

    private Javac() {
    }

    private static final Pattern PRIMITIVE_TYPE_NAME_PATTERN = Pattern.compile("^(boolean|byte|short|int|long|float|double|char)$");

    static public boolean isPrimitive(JCExpression ref) {
        String typeName = ref.toString();
        return PRIMITIVE_TYPE_NAME_PATTERN.matcher(typeName).matches();
    }

    static public Object calculateGuess(JCExpression expr) {
        if (expr instanceof JCLiteral) {
            JCLiteral lit = (JCLiteral) expr;
            if (lit.getKind() == com.sun.source.tree.Tree.Kind.BOOLEAN_LITERAL) {
                return ((Number) lit.value).intValue() == 0 ? false : true;
            }
            return lit.value;
        } else if (expr instanceof JCIdent || expr instanceof JCFieldAccess) {
            String x = expr.toString();
            if (x.endsWith(".class"))
                x = x.substring(0, x.length() - 6);
            else {
                int idx = x.lastIndexOf('.');
                if (idx > -1)
                    x = x.substring(idx + 1);
            }
            return x;
        } else
            return null;
    }

    static final public Object CTC_EQUAL = getTreeTag("EQ");

    static final public Object CTC_PLUS = getTreeTag("PLUS");

    static public JCClassDecl ClassDef(TreeMaker maker, JCModifiers mods, Name name, List<JCTypeParameter> typarams, JCExpression extending, List<JCExpression> implementing, List<JCTree> defs) {
        if (method == null)
            try {
                method = TreeMaker.class.getDeclaredMethod("ClassDef", JCModifiers.class, Name.class, List.class, JCExpression.class, List.class, List.class);
            } catch (NoSuchMethodException ignore) {
            }
        if (method == null)
            try {
                method = TreeMaker.class.getDeclaredMethod("ClassDef", JCModifiers.class, Name.class, List.class, JCTree.class, List.class, List.class);
            } catch (NoSuchMethodException ignore) {
            }
        if (method == null)
            throw new IllegalStateException("Lombok bug #20130617-1310: ClassDef doesn't look like anything we thought it would look like.");
        if (!Modifier.isPublic(method.getModifiers()) && !method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            return (JCClassDecl) method.invoke(maker, mods, name, typarams, extending, implementing, defs);
        } catch (InvocationTargetException e) {
            throw sneakyThrow(e.getCause());
        } catch (IllegalAccessException e) {
            throw sneakyThrow(e.getCause());
        }
    }

    static final public Object CTC_MUL = getTreeTag("MUL");

    static final public Object CTC_UNSIGNED_SHIFT_RIGHT = getTreeTag("USR");

    static final public Object CTC_BITXOR = getTreeTag("BITXOR");

    static final public Object CTC_NOT = getTreeTag("NOT");

    static final public Object CTC_NOT_EQUAL = getTreeTag("NE");

    private static final Pattern VERSION_PARSER = Pattern.compile("^(\\d{1,6})\\.(\\d{1,6}).*$");

    private static final Method JCTREE_GETTAG;

    static final public Object CTC_CLASS = getTypeTag("CLASS");

    private static final Field JCTREE_TAG;

    static final public Object CTC_BOT = getTypeTag("BOT");

    private static Method method;

    static final public Object CTC_NONE = getTypeTag("NONE");

    static final public Object CTC_VOID = getTypeTag("VOID");

    static public int getTag(JCTree node) {
        if (JCTREE_GETTAG != null) {
            try {
                return (Integer) JCTREE_GETTAG.invoke(node);
            } catch (Exception e) {
            }
        }
        try {
            return (Integer) JCTREE_TAG.get(node);
        } catch (Exception e) {
            throw new IllegalStateException("Can't get node tag");
        }
    }

    static final public Object CTC_CHAR = getTypeTag("CHAR");

    static final public Object CTC_LONG = getTypeTag("LONG");

    static final public Object CTC_BYTE = getTypeTag("BYTE");

    private static RuntimeException sneakyThrow(Throwable t) {
        if (t == null)
            throw new NullPointerException("t");
        Javac.<RuntimeException>sneakyThrow0(t);
        return null;
    }

    static final public Object CTC_SHORT = getTypeTag("SHORT");

    static public Object getTreeTypeTag(JCLiteral tree) {
        return tree.typetag;
    }

    static public JCExpression makeTypeIdent(TreeMaker maker, Object ctc) {
        try {
            Method createIdent;
            if (JavaCompiler.version().startsWith("1.8")) {
                createIdent = TreeMaker.class.getMethod("TypeIdent", Class.forName("com.sun.tools.javac.code.TypeTag"));
            } else {
                createIdent = TreeMaker.class.getMethod("TypeIdent", Integer.TYPE);
            }
            return (JCExpression) createIdent.invoke(maker, ctc);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static final public Object CTC_FLOAT = getTypeTag("FLOAT");

    static public Object getTreeTypeTag(JCPrimitiveTypeTree tree) {
        return tree.typetag;
    }

    static public JCLiteral makeLiteral(TreeMaker maker, Object ctc, Object argument) {
        try {
            Method createLiteral;
            if (JavaCompiler.version().startsWith("1.8")) {
                createLiteral = TreeMaker.class.getMethod("Literal", Class.forName("com.sun.tools.javac.code.TypeTag"), Object.class);
            } else {
                createLiteral = TreeMaker.class.getMethod("Literal", Integer.TYPE, Object.class);
            }
            return (JCLiteral) createLiteral.invoke(maker, ctc, argument);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static final public Object CTC_DOUBLE = getTypeTag("DOUBLE");

    static public JCUnary makeUnary(TreeMaker maker, Object ctc, JCExpression argument) {
        try {
            Method createUnary;
            if (JavaCompiler.version().startsWith("1.8")) {
                createUnary = TreeMaker.class.getMethod("Unary", Class.forName("com.sun.tools.javac.code.TypeTag"), JCExpression.class);
            } else {
                createUnary = TreeMaker.class.getMethod("Unary", Integer.TYPE, JCExpression.class);
            }
            return (JCUnary) createUnary.invoke(maker, ctc, argument);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static final public Object CTC_INT = getTypeTag("INT");

    static public JCBinary makeBinary(TreeMaker maker, Object ctc, JCExpression rhsArgument, JCExpression lhsArgument) {
        try {
            Method createUnary;
            if (JavaCompiler.version().startsWith("1.8")) {
                createUnary = TreeMaker.class.getMethod("Binary", Class.forName("com.sun.tools.javac.code.TypeTag"), JCExpression.class, JCExpression.class);
            } else {
                createUnary = TreeMaker.class.getMethod("Binary", Integer.TYPE, JCExpression.class, JCExpression.class);
            }
            return (JCBinary) createUnary.invoke(maker, ctc, rhsArgument, lhsArgument);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static final public Object CTC_BOOLEAN = getTypeTag("BOOLEAN");

    static public int getJavaCompilerVersion() {
        Matcher m = VERSION_PARSER.matcher(JavaCompiler.version());
        if (m.matches()) {
            int major = Integer.parseInt(m.group(1));
            int minor = Integer.parseInt(m.group(2));
            if (major == 1)
                return minor;
        }
        return 6;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrow0(Throwable t) throws T {
        throw (T) t;
    }

    static public Object getTypeTag(String identifier) {
        try {
            if (JavaCompiler.version().startsWith("1.8")) {
                return Class.forName("com.sun.tools.javac.code.TypeTag").getField(identifier).get(null);
            } else {
                return Class.forName("com.sun.tools.javac.code.TypeTags").getField(identifier).get(null);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static public Object getTreeTag(String identifier) {
        try {
            if (JavaCompiler.version().startsWith("1.8")) {
                return Class.forName("com.sun.tools.javac.tree.JCTree$Tag").getField(identifier).get(null);
            } else {
                return Class.forName("com.sun.tools.javac.tree.JCTree").getField(identifier).get(null);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    static {
        Field f = null;
        try {
            f = JCTree.class.getDeclaredField("tag");
        } catch (NoSuchFieldException e) {
        }
        JCTREE_TAG = f;
        Method m = null;
        try {
            m = JCTree.class.getDeclaredMethod("getTag");
        } catch (NoSuchMethodException e) {
        }
        JCTREE_GETTAG = m;
    }

    static public boolean compareCTC(Object ctc1, Object ctc2) {
        return Objects.equals(ctc1, ctc2);
    }
}
