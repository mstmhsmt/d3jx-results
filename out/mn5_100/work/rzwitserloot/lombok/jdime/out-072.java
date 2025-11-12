package lombok.eclipse.handlers;
import static lombok.core.handlers.HandlerUtil.*;
import lombok.ConfigurationKeys;
import lombok.val;
import lombok.core.HandlerPriority;
import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseASTAdapter;
import lombok.eclipse.EclipseASTVisitor;
import lombok.eclipse.EclipseNode;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ForStatement;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.mangosdk.spi.ProviderFor;

@ProviderFor(value = EclipseASTVisitor.class) @DeferUntilPostDiet @HandlerPriority(value = 65536) public class HandleVal extends EclipseASTAdapter {
  @Override public void visitLocal(EclipseNode localNode, LocalDeclaration local) {
    if (!EclipseHandlerUtil.typeMatches(val.class, localNode, local.type)) {
      return;
    }
    handleFlagUsage(localNode, ConfigurationKeys.VAL_FLAG_USAGE, "val");
    boolean variableOfForEach = false;
    if (localNode.directUp().get() instanceof ForeachStatement) {
      ForeachStatement fs = (ForeachStatement) localNode.directUp().get();
      variableOfForEach = fs.elementVariable == local;
    }
    if (local.initialization == null && !variableOfForEach) {
      localNode.addError("\'val\' on a local variable requires an initializer expression");
      return;
    }
    if (local.initialization instanceof ArrayInitializer) {
      localNode.addError("\'val\' is not compatible with array initializer expressions. Use the full form (new int[] { ... } instead of just { ... })");
      return;
    }
    if (localNode.directUp().get() instanceof ForStatement) {
      localNode.addError("\'val\' is not allowed in old-style for loops");
      return;
    }
    if (local.initialization != null && local.initialization.getClass().getName().equals("org.eclipse.jdt.internal.compiler.ast.LambdaExpression")) {
      localNode.addError("\'val\' is not allowed with lambda expressions.");
    }
  }
}