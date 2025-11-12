package org.androidannotations.processing;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.processing.EBeansHolder.Classes;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCase;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JSwitch;
import com.sun.codemodel.JVar;
import javax.lang.model.element.Element;

public class EBeanHolder {

    public final JDefinedClass generatedClass;

    public JVar beforeCreateSavedInstanceStateParam;

    public JMethod init;

    public JMethod afterSetContentView;

    public JBlock extrasNotNullBlock;

    public JVar extras;

    public JVar resources;

    public JMethod cast;

    public JFieldVar handler;

    public JBlock onOptionsItemSelectedIfElseBlock;

    public JVar onOptionsItemSelectedItemId;

    public JVar onOptionsItemSelectedItem;

    public JMethod restoreSavedInstanceStateMethod;

    public JBlock saveInstanceStateBlock;

    public JExpression contextRef;

    public JBlock initIfActivityBody;

    public JExpression initActivityRef;

    public JDefinedClass intentBuilderClass;

    public JFieldVar intentField;

    public NonConfigurationHolder nonConfigurationHolder;

    public final HashMap<String, TextWatcherHolder> textWatchers = new HashMap<String, TextWatcherHolder>();

    public final HashMap<Integer, JCase> onActivityResultCases = new HashMap<Integer, JCase>();

    public JSwitch onActivityResultSwitch;

    public JMethod onActivityResultMethod;

    public final HashMap<String, OnSeekBarChangeListenerHolder> onSeekBarChangeListeners = new HashMap<String, OnSeekBarChangeListenerHolder>();

    public JVar fragmentArguments;

    public JFieldVar fragmentArgumentsBuilderField;

    public JMethod fragmentArgumentsInjectMethod;

    public JBlock fragmentArgumentsNotNullBlock;

    public JDefinedClass fragmentBuilderClass;

    public JMethod findNativeFragmentById;

    public JMethod findSupportFragmentById;

    public JMethod findNativeFragmentByTag;

    public JMethod findSupportFragmentByTag;

    final private EBeansHolder eBeansHolder;

    public final Class<? extends Annotation> eBeanAnnotation;

    public EBeanHolder(EBeansHolder eBeansHolder, Class<? extends Annotation> eBeanAnnotation, JDefinedClass generatedClass) {
        this.eBeansHolder = eBeansHolder;
        this.eBeanAnnotation = eBeanAnnotation;
        this.generatedClass = generatedClass;
    }

    public Classes classes() {
        return eBeansHolder.classes();
    }

    public JCodeModel codeModel() {
        return eBeansHolder.codeModel();
    }

    public JClass refClass(String fullyQualifiedClassName) {
        return eBeansHolder.refClass(fullyQualifiedClassName);
    }

    public JClass refClass(Class<?> clazz) {
        return eBeansHolder.refClass(clazz);
    }

    public void generateApiClass(Element originatingElement, Class<?> apiClass) {
        eBeansHolder.generateApiClass(originatingElement, apiClass);
    }

    public JDefinedClass definedClass(String fullyQualifiedClassName) {
        return eBeansHolder.definedClass(fullyQualifiedClassName);
    }
}
