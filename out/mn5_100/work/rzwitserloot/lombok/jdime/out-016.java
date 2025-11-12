package lombok.javac.handlers;
import static lombok.javac.handlers.PKG.*;
import lombok.EqualsAndHashCode;
import lombok.core.AnnotationValues;
import lombok.core.AST.Kind;
import lombok.javac.Javac;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacAST.Node;
import org.mangosdk.spi.ProviderFor;
import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCArrayTypeTree;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

/**
 * Handles the <code>lombok.EqualsAndHashCode</code> annotation for javac.
 */
@ProviderFor(value = JavacAnnotationHandler.class) public class HandleEqualsAndHashCode implements JavacAnnotationHandler<EqualsAndHashCode> {
  private void checkForBogusExcludes(Node type, AnnotationValues<EqualsAndHashCode> annotation) {
    List<String> list = List.from(annotation.getInstance().exclude());
    boolean[] matched = new boolean[list.size()];
    for (Node child : type.down()) {
      if (list.isEmpty()) {
        break;
      }
      if (child.getKind() != Kind.FIELD) {
        continue;
      }
      if ((((JCVariableDecl) child.get()).mods.flags & Flags.STATIC) != 0) {
        continue;
      }
      if ((((JCVariableDecl) child.get()).mods.flags & Flags.TRANSIENT) != 0) {
        continue;
      }
      int idx = list.indexOf(child.getName());
      if (idx > -1) {
        matched[idx] = true;
      }
    }
    for (int i = 0; i < list.size(); i++) {
      if (!matched[i]) {
        annotation.setWarning("exclude", "This field does not exist, or would have been excluded anyway.", i);
      }
    }
  }

  @Override public boolean handle(AnnotationValues<EqualsAndHashCode> annotation, JCAnnotation ast, Node annotationNode) {
    EqualsAndHashCode ann = annotation.getInstance();
    List<String> excludes = List.from(ann.exclude());
    Node typeNode = annotationNode.up();
    checkForBogusExcludes(typeNode, annotation);
    return generateMethods(typeNode, annotationNode, excludes, ann.callSuper(), annotation.getRawExpression("callSuper") == null, true);
  }

  public void generateEqualsAndHashCodeForType(Node typeNode, Node errorNode) {
    for (Node child : typeNode.down()) {
      if (child.getKind() == Kind.ANNOTATION) {
        if (Javac.annotationTypeMatches(EqualsAndHashCode.class, child)) {
          return;
        }
      }
    }
    boolean callSuper = false;
    try {
      callSuper = ((Boolean) EqualsAndHashCode.class.getMethod("callSuper").getDefaultValue()).booleanValue();
    } catch (Exception ignore) {
    }
    generateMethods(typeNode, errorNode, List.<String>nil(), callSuper, true, false);
  }

  private boolean generateMethods(Node typeNode, Node errorNode, List<String> excludes, boolean callSuper, boolean implicit, boolean whineIfExists) {
    boolean notAClass = true;
    if (typeNode.get() instanceof JCClassDecl) {
      long flags = ((JCClassDecl) typeNode.get()).mods.flags;
      notAClass = (flags & (Flags.INTERFACE | Flags.ANNOTATION | Flags.ENUM)) != 0;
    }
    if (notAClass) {
      errorNode.addError("@EqualsAndHashCode is only supported on a class.");
      return false;
    }
    boolean isDirectDescendantOfObject = true;
    JCTree extending = ((JCClassDecl) typeNode.get()).extending;
    if (extending != null) {
      String p = extending.toString();
      isDirectDescendantOfObject = p.equals("Object") || p.equals("java.lang.Object");
    }
    if (isDirectDescendantOfObject && callSuper) {
      errorNode.addError("Generating equals/hashCode with a supercall to java.lang.Object is pointless.");
      return true;
    }
    if (
<<<<<<< commits-mn5_100/rzwitserloot/lombok/0221e460b2e648b142284c6c462d5798f33a3ff7/HandleEqualsAndHashCode-efc154f.java
    !isDirectDescendentOfObject && !callSuper
=======
    !isDirectDescendantOfObject
>>>>>>> commits-mn5_100/rzwitserloot/lombok/fe0da3f53f1e88b704e21463cc5fea3d998e394a/HandleEqualsAndHashCode-e3e7c1a.java
     && implicit) {
      errorNode.addWarning("Generating equals/hashCode implementation but without a call to superclass, even though this class does not extend java.lang.Object. If this is intentional, add \'@EqualsAndHashCode(callSuper=false)\' to your type.");
    }
    List<Node> nodesForEquality = List.nil();
    for (Node child : typeNode.down()) {
      if (child.getKind() != Kind.FIELD) {
        continue;
      }
      JCVariableDecl fieldDecl = (JCVariableDecl) child.get();
      if ((fieldDecl.mods.flags & Flags.STATIC) != 0) {
        continue;
      }
      if ((fieldDecl.mods.flags & Flags.TRANSIENT) != 0) {
        continue;
      }
      if (excludes.contains(fieldDecl.name.toString())) {
        continue;
      }
      nodesForEquality = nodesForEquality.append(child);
    }
    switch (methodExists("hashCode", typeNode)) {
      case NOT_EXISTS:
      JCMethodDecl method = createHashCode(typeNode, nodesForEquality, callSuper);
      injectMethod(typeNode, method);
      break;
      case EXISTS_BY_LOMBOK:
      break;
      default:
      case EXISTS_BY_USER:
      if (whineIfExists) {
        errorNode.addWarning("Not generating hashCode(): A method with that name already exists");
      }
      break;
    }
    switch (methodExists("equals", typeNode)) {
      case NOT_EXISTS:
      JCMethodDecl method = createEquals(typeNode, nodesForEquality, callSuper);
      injectMethod(typeNode, method);
      break;
      case EXISTS_BY_LOMBOK:
      break;
      default:
      case EXISTS_BY_USER:
      if (whineIfExists) {
        errorNode.addWarning("Not generating equals(Object other): A method with that name already exists");
      }
      break;
    }
    return true;
  }

  private JCMethodDecl createHashCode(Node typeNode, List<Node> fields, boolean callSuper) {
    TreeMaker maker = typeNode.getTreeMaker();
    JCAnnotation overrideAnnotation = maker.Annotation(chainDots(maker, typeNode, "java", "lang", "Override"), List.<JCExpression>nil());
    JCModifiers mods = maker.Modifiers(Flags.PUBLIC, List.of(overrideAnnotation));
    JCExpression returnType = maker.TypeIdent(TypeTags.INT);
    List<JCStatement> statements = List.nil();
    Name primeName = typeNode.toName("PRIME");
    Name resultName = typeNode.toName("result");
    {
      if (!fields.isEmpty() || callSuper) {
        statements = statements.append(maker.VarDef(maker.Modifiers(Flags.FINAL), primeName, maker.TypeIdent(TypeTags.INT), maker.Literal(31)));
      }
    }
    {
      statements = statements.append(maker.VarDef(maker.Modifiers(0), resultName, maker.TypeIdent(TypeTags.INT), maker.Literal(1)));
    }
    List<JCExpression> intoResult = List.nil();
    if (callSuper) {
      JCMethodInvocation callToSuper = maker.Apply(List.<JCExpression>nil(), maker.Select(maker.Ident(typeNode.toName("super")), typeNode.toName("hashCode")), List.<JCExpression>nil());
      intoResult = intoResult.append(callToSuper);
    }
    int tempCounter = 0;
    for (Node fieldNode : fields) {
      JCVariableDecl field = (JCVariableDecl) fieldNode.get();
      JCExpression fType = field.vartype;
      JCExpression thisDotField = maker.Select(maker.Ident(typeNode.toName("this")), field.name);
      JCExpression thisDotFieldClone = maker.Select(maker.Ident(typeNode.toName("this")), field.name);
      if (fType instanceof JCPrimitiveTypeTree) {
        switch (((JCPrimitiveTypeTree) fType).getPrimitiveTypeKind()) {
          case BOOLEAN:
          intoResult = intoResult.append(maker.Conditional(thisDotField, maker.Literal(1231), maker.Literal(1237)));
          break;
          case LONG:
          intoResult = intoResult.append(longToIntForHashCode(maker, thisDotField, thisDotFieldClone));
          break;
          case FLOAT:
          intoResult = intoResult.append(maker.Apply(List.<JCExpression>nil(), chainDots(maker, typeNode, "java", "lang", "Float", "floatToIntBits"), List.of(thisDotField)));
          break;
          case DOUBLE:
          Name tempVar = typeNode.toName("temp" + (++tempCounter));
          JCExpression init = maker.Apply(List.<JCExpression>nil(), chainDots(maker, typeNode, "java", "lang", "Double", "doubleToLongBits"), List.of(thisDotField));
          statements = statements.append(maker.VarDef(maker.Modifiers(Flags.FINAL), tempVar, maker.TypeIdent(TypeTags.LONG), init));
          intoResult = intoResult.append(longToIntForHashCode(maker, maker.Ident(tempVar), maker.Ident(tempVar)));
          break;
          default:
          case BYTE:
          case SHORT:
          case INT:
          case CHAR:
          intoResult = intoResult.append(thisDotField);
          break;
        }
      } else {
        if (fType instanceof JCArrayTypeTree) {
          boolean multiDim = ((JCArrayTypeTree) fType).elemtype instanceof JCArrayTypeTree;
          boolean primitiveArray = ((JCArrayTypeTree) fType).elemtype instanceof JCPrimitiveTypeTree;
          boolean useDeepHC = multiDim || !primitiveArray;
          JCExpression hcMethod = chainDots(maker, typeNode, "java", "util", "Arrays", useDeepHC ? "deepHashCode" : "hashCode");
          intoResult = intoResult.append(maker.Apply(List.<JCExpression>nil(), hcMethod, List.of(thisDotField)));
        } else {
          JCExpression hcCall = maker.Apply(List.<JCExpression>nil(), maker.Select(thisDotField, typeNode.toName("hashCode")), List.<JCExpression>nil());
          JCExpression thisEqualsNull = maker.Binary(JCTree.EQ, thisDotField, maker.Literal(TypeTags.BOT, null));
          intoResult = intoResult.append(maker.Conditional(thisEqualsNull, maker.Literal(0), hcCall));
        }
      }
    }
    for (JCExpression expr : intoResult) {
      JCExpression mult = maker.Binary(JCTree.MUL, maker.Ident(resultName), maker.Ident(primeName));
      JCExpression add = maker.Binary(JCTree.PLUS, mult, expr);
      statements = statements.append(maker.Exec(maker.Assign(maker.Ident(resultName), add)));
    }
    {
      statements = statements.append(maker.Return(maker.Ident(resultName)));
    }
    JCBlock body = maker.Block(0, statements);
    return maker.MethodDef(mods, typeNode.toName("hashCode"), returnType, List.<JCTypeParameter>nil(), List.<JCVariableDecl>nil(), List.<JCExpression>nil(), body, null);
  }

  /** The 2 references must be clones of each other. */
  private JCExpression longToIntForHashCode(TreeMaker maker, JCExpression ref1, JCExpression ref2) {
    JCExpression shift = maker.Binary(JCTree.USR, ref1, maker.Literal(32));
    JCExpression xorBits = maker.Binary(JCTree.BITXOR, shift, ref2);
    return maker.TypeCast(maker.TypeIdent(TypeTags.INT), xorBits);
  }

  private JCMethodDecl createEquals(Node typeNode, List<Node> fields, boolean callSuper) {
    TreeMaker maker = typeNode.getTreeMaker();
    JCClassDecl type = (JCClassDecl) typeNode.get();
    Name oName = typeNode.toName("o");
    Name otherName = typeNode.toName("other");
    Name thisName = typeNode.toName("this");
    JCAnnotation overrideAnnotation = maker.Annotation(chainDots(maker, typeNode, "java", "lang", "Override"), List.<JCExpression>nil());
    JCModifiers mods = maker.Modifiers(Flags.PUBLIC, List.of(overrideAnnotation));
    JCExpression objectType = maker.Type(typeNode.getSymbolTable().objectType);
    JCExpression returnType = maker.TypeIdent(TypeTags.BOOLEAN);
    List<JCStatement> statements = List.nil();
    List<JCVariableDecl> params = List.of(maker.VarDef(maker.Modifiers(Flags.FINAL), oName, objectType, null));
    {
      statements = statements.append(maker.If(maker.Binary(JCTree.EQ, maker.Ident(oName), maker.Ident(thisName)), returnBool(maker, true), null));
    }
    {
      statements = statements.append(maker.If(maker.Binary(JCTree.EQ, maker.Ident(oName), maker.Literal(TypeTags.BOT, null)), returnBool(maker, false), null));
    }
    {
      Name getClass = typeNode.toName("getClass");
      List<JCExpression> exprNil = List.nil();
      JCExpression oGetClass = maker.Apply(exprNil, maker.Select(maker.Ident(oName), getClass), exprNil);
      JCExpression thisGetClass = maker.Apply(exprNil, maker.Select(maker.Ident(thisName), getClass), exprNil);
      statements = statements.append(maker.If(maker.Binary(JCTree.NE, oGetClass, thisGetClass), returnBool(maker, false), null));
    }
    if (callSuper) {
      JCMethodInvocation callToSuper = maker.Apply(List.<JCExpression>nil(), maker.Select(maker.Ident(typeNode.toName("super")), typeNode.toName("equals")), List.<JCExpression>of(maker.Ident(oName)));
      JCUnary superNotEqual = maker.Unary(JCTree.NOT, callToSuper);
      statements = statements.append(maker.If(superNotEqual, returnBool(maker, false), null));
    }
    {
      final JCExpression selfType1, selfType2;
      List<JCExpression> wildcards1 = List.nil();
      List<JCExpression> wildcards2 = List.nil();
      for (int i = 0; i < type.typarams.length(); i++) {
        wildcards1 = wildcards1.append(maker.Wildcard(maker.TypeBoundKind(BoundKind.UNBOUND), null));
        wildcards2 = wildcards2.append(maker.Wildcard(maker.TypeBoundKind(BoundKind.UNBOUND), null));
      }
      if (type.typarams.isEmpty()) {
        selfType1 = maker.Ident(type.name);
        selfType2 = maker.Ident(type.name);
      } else {
        selfType1 = maker.TypeApply(maker.Ident(type.name), wildcards1);
        selfType2 = maker.TypeApply(maker.Ident(type.name), wildcards2);
      }
      statements = statements.append(maker.VarDef(maker.Modifiers(Flags.FINAL), otherName, selfType1, maker.TypeCast(selfType2, maker.Ident(oName))));
    }
    for (Node fieldNode : fields) {
      JCVariableDecl field = (JCVariableDecl) fieldNode.get();
      JCExpression fType = field.vartype;
      JCExpression thisDotField = maker.Select(maker.Ident(thisName), field.name);
      JCExpression otherDotField = maker.Select(maker.Ident(otherName), field.name);
      if (fType instanceof JCPrimitiveTypeTree) {
        switch (((JCPrimitiveTypeTree) fType).getPrimitiveTypeKind()) {
          case FLOAT:
          statements = statements.append(generateCompareFloatOrDouble(thisDotField, otherDotField, maker, typeNode, false));
          break;
          case DOUBLE:
          statements = statements.append(generateCompareFloatOrDouble(thisDotField, otherDotField, maker, typeNode, true));
          break;
          default:
          statements = statements.append(maker.If(maker.Binary(JCTree.NE, thisDotField, otherDotField), returnBool(maker, false), null));
          break;
        }
      } else {
        if (fType instanceof JCArrayTypeTree) {
          boolean multiDim = ((JCArrayTypeTree) fType).elemtype instanceof JCArrayTypeTree;
          boolean primitiveArray = ((JCArrayTypeTree) fType).elemtype instanceof JCPrimitiveTypeTree;
          boolean useDeepEquals = multiDim || !primitiveArray;
          JCExpression eqMethod = chainDots(maker, typeNode, "java", "util", "Arrays", useDeepEquals ? "deepEquals" : "equals");
          List<JCExpression> args = List.of(thisDotField, otherDotField);
          statements = statements.append(maker.If(maker.Unary(JCTree.NOT, maker.Apply(List.<JCExpression>nil(), eqMethod, args)), returnBool(maker, false), null));
        } else {
          JCExpression thisEqualsNull = maker.Binary(JCTree.EQ, thisDotField, maker.Literal(TypeTags.BOT, null));
          JCExpression otherNotEqualsNull = maker.Binary(JCTree.NE, otherDotField, maker.Literal(TypeTags.BOT, null));
          JCExpression thisEqualsThat = maker.Apply(List.<JCExpression>nil(), maker.Select(thisDotField, typeNode.toName("equals")), List.of(otherDotField));
          JCExpression fieldsAreNotEqual = maker.Conditional(thisEqualsNull, otherNotEqualsNull, maker.Unary(JCTree.NOT, thisEqualsThat));
          statements = statements.append(maker.If(fieldsAreNotEqual, returnBool(maker, false), null));
        }
      }
    }
    {
      statements = statements.append(returnBool(maker, true));
    }
    JCBlock body = maker.Block(0, statements);
    return maker.MethodDef(mods, typeNode.toName("equals"), returnType, List.<JCTypeParameter>nil(), params, List.<JCExpression>nil(), body, null);
  }

  private JCStatement generateCompareFloatOrDouble(JCExpression thisDotField, JCExpression otherDotField, TreeMaker maker, Node node, boolean isDouble) {
    JCExpression clazz = chainDots(maker, node, "java", "lang", isDouble ? "Double" : "Float");
    List<JCExpression> args = List.of(thisDotField, otherDotField);
    JCBinary compareCallEquals0 = maker.Binary(JCTree.NE, maker.Apply(List.<JCExpression>nil(), maker.Select(clazz, node.toName("compare")), args), maker.Literal(0));
    return maker.If(compareCallEquals0, returnBool(maker, false), null);
  }

  private JCStatement returnBool(TreeMaker maker, boolean bool) {
    return maker.Return(maker.Literal(TypeTags.BOOLEAN, bool ? 1 : 0));
  }
}