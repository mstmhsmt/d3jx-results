package org.rstudio.studio.client.workbench.views.source.editors.text.visualmode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gwt.aria.client.ExpandedValue;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import org.rstudio.core.client.CommandWithArg;
import org.rstudio.core.client.Debug;
import org.rstudio.core.client.StringUtil;
import org.rstudio.core.client.a11y.A11y;
import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.core.client.regex.Pattern;
import org.rstudio.core.client.theme.ThemeFonts;
import org.rstudio.core.client.theme.res.ThemeStyles;
import org.rstudio.studio.client.RStudioGinjector;
import org.rstudio.studio.client.common.filetypes.FileTypeRegistry;
import org.rstudio.studio.client.common.rnw.RnwWeave;
import org.rstudio.studio.client.panmirror.ui.PanmirrorUIChunkCallbacks;
import org.rstudio.studio.client.panmirror.ui.PanmirrorUIChunkEditor;
import org.rstudio.studio.client.server.ServerRequestCallback;
import org.rstudio.studio.client.workbench.views.output.lint.LintManager;
import org.rstudio.studio.client.workbench.views.output.lint.model.LintItem;
import org.rstudio.studio.client.workbench.views.source.ViewsSourceConstants;
import org.rstudio.studio.client.workbench.views.source.editors.EditingTargetCodeExecution;
import org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor;
import org.rstudio.studio.client.workbench.views.source.editors.text.ChunkOutputWidget;
import org.rstudio.studio.client.workbench.views.source.editors.text.ChunkRowExecState;
import org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay;
import org.rstudio.studio.client.workbench.views.source.editors.text.FoldStyle;
import org.rstudio.studio.client.workbench.views.source.editors.text.Scope;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetCompilePdfHelper;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetPrefsHelper;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetQuartoHelper;
import org.rstudio.studio.client.workbench.views.source.editors.text.AceEditor.EditorBehavior;
import org.rstudio.studio.client.workbench.views.source.editors.text.ace.AceEditorNative;
import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position;
import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Range;
import org.rstudio.studio.client.workbench.views.source.editors.text.assist.RChunkHeaderParser;
import org.rstudio.studio.client.workbench.views.source.editors.text.rmd.ChunkContextPanmirrorUi;
import org.rstudio.studio.client.workbench.views.source.editors.text.rmd.ChunkDefinition;
import org.rstudio.studio.client.workbench.views.source.editors.text.rmd.ChunkOutputUi;
import org.rstudio.studio.client.workbench.views.source.editors.text.visualmode.VisualMode.SyncType;
import org.rstudio.studio.client.workbench.views.source.editors.text.visualmode.ui.VisualModeCollapseToggle;
import org.rstudio.studio.client.workbench.views.source.model.DocUpdateSentinel;
import org.rstudio.studio.client.workbench.views.source.model.RnwChunkOptions;
import org.rstudio.studio.client.workbench.views.source.model.RnwCompletionContext;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import jsinterop.base.Js;
import com.google.gwt.core.client.JsArrayString;

/**
 * Represents an R Markdown chunk in the visual editor, including its embedded
 * text editor, output, and execution tools. 
 */
public class VisualModeChunk {
  public interface ChunkStyle extends ClientBundle {
    @Source(value = "VisualModeChunk.css") VisualModeChunk.Styles style();
  }

  public interface Styles extends CssResource {
    String host();

    String gutter();

    String gutterIcon();

    String summary();

    String editor();

    String editorHost();

    String chunkHost();

    String toolbar();
  }

  public VisualModeChunk(Element element, int index, boolean isExpanded, JsArrayString classes, PanmirrorUIChunkCallbacks chunkCallbacks, DocUpdateSentinel sentinel, TextEditingTarget target, VisualModeEditorSync sync) {
    element_ = element;
    chunkCallbacks_ = chunkCallbacks;
    sync_ = sync;
    codeExecution_ = target.getCodeExecutor();
    parent_ = target.getDocDisplay();
    target_ = target;
    active_ = false;
    markdownIndex_ = index;
    releaseOnDismiss_ = new ArrayList<>();
    destroyHandlers_ = new ArrayList<>();
    lint_ = JsArray.createArray().cast();
    classes_ = classes;
    ChunkStyle style = GWT.create(ChunkStyle.class);
    style_ = style.style();
    style_.ensureInjected();
    outputHost_ = Document.get().createDivElement();
    outputHost_.getStyle().setPosition(com.google.gwt.dom.client.Style.Position.RELATIVE);
    ChunkOutputUi output = null;
    if (index > 0) {
      Position pos = parent_.positionFromIndex(index);
      scope_ = parent_.getScopeAtPosition(pos);
      if (scope_ != null) {
        output = target.getNotebook().migrateOutput(scope_, this);
      }
    } else {
      scope_ = null;
    }
    PanmirrorUIChunkEditor chunk = new PanmirrorUIChunkEditor();
    rowState_ = new HashMap<>();
    editor_ = new AceEditor();
    editor_.setEditorBehavior(EditorBehavior.AceBehaviorEmbedded);
    final AceEditorNative chunkEditor = editor_.getWidget().getEditor();
    chunk.editor = Js.uncheckedCast(chunkEditor);
    editor_.setRCompletionContext(target_.getRCompletionContext());
    editor_.setCppCompletionContext(target_.getCppCompletionContext());
    editor_.setRnwCompletionContext(wrapRnwCompletionContext(target_.getRnwCompletionContext()));
    editor_.setUseWrapMode(true);
    releaseOnDismiss_.add(editor_.addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent event) {
        NativeEvent ne = event.getNativeEvent();
        TextEditingTargetQuartoHelper.continueSpecialCommentOnNewline(editor_, ne);
      }
    }));
    releaseOnDismiss_.add(editor_.addFocusHandler((evt) -> {
      active_ = true;
      target_.getVisualMode().setActiveEditor(editor_);
    }));
    releaseOnDismiss_.add(editor_.addBlurHandler((evt) -> {
      active_ = false;
      target_.getVisualMode().setActiveEditor(null);
    }));
    releaseOnDismiss_.add(RStudioGinjector.INSTANCE.getUserPrefs().tabKeyMoveFocus().bind(new CommandWithArg<Boolean>() {
      @Override public void execute(Boolean movesFocus) {
        chunkEditor.setTabMovesFocus(movesFocus);
      }
    }));
    releaseOnDismiss_.add(RStudioGinjector.INSTANCE.getUserPrefs().visualMarkdownCodeEditorLineNumbers().bind(new CommandWithArg<Boolean>() {
      @Override public void execute(Boolean showLineNumbers) {
        showLint(lint_);
      }
    }));
    host_ = Document.get().createDivElement();
    host_.setClassName(style_.host());
    host_.setTabIndex(0);
    collapse_ = new VisualModeCollapseToggle(isExpanded);
    host_.appendChild(collapse_.getElement());
    summary_ = Document.get().createDivElement();
    summary_.setClassName(ThemeFonts.getFixedWidthClass() + " " + style_.summary());
    host_.appendChild(summary_);
    chunkHost_ = Document.get().createDivElement();
    chunkHost_.setClassName(style_.chunkHost());
    host_.appendChild(chunkHost_);
    editorHost_ = Document.get().createDivElement();
    editorHost_.setClassName(style_.editorHost());
    editorContainer_ = chunkEditor.getContainer();
    editorContainer_.addClassName(style_.editor());
    editorHost_.appendChild(editorContainer_);
    chunkHost_.appendChild(editorHost_);
    gutterHost_ = Document.get().createDivElement();
    gutterHost_.setClassName(style_.gutter());
    host_.appendChild(gutterHost_);
    if (output != null) {
      setDefinition(output.getDefinition());
      if (widget_ == null) {
        setOutputWidget(output.getOutputWidget());
      }
      Scheduler.get().scheduleDeferred(() -> {
        syncOutputClass();
      });
    }
    chunkHost_.appendChild(outputHost_);
    if (scope_ != null) {
      createToolbar();
    }
    chunk.element = host_;
    chunk.setMode = (String mode) -> {
      setMode(editor_, mode);
      editor_.setFoldStyle(FoldStyle.FOLD_MARK_MANUAL);
    };
    chunk.executeSelection = () -> {
      sync_.syncToEditor(SyncType.SyncTypeExecution, () -> {
        executeSelection();
      });
    };
    DOM.sinkEvents(host_, Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONCLICK | Event.ONFOCUS);
    DOM.setEventListener(host_, (evt) -> {
      switch (evt.getTypeInt()) {
        case Event.ONMOUSEOVER:
        collapse_.setShowToggle(true);
        break;
        case Event.ONMOUSEOUT:
        if (!editor_.isFocused()) {
          collapse_.setShowToggle(false);
        }
        break;
        case Event.ONFOCUS:
        case Event.ONCLICK:
        if (!getExpanded()) {
          editor_.focus();
        }
        break;
      }
    });
    releaseOnDismiss_.add(editor_.addFocusHandler((evt) -> {
      if (element_ != null) {
        element_.addClassName("pm-ace-focused");
      }
      collapse_.setShowToggle(true);
    }));
    releaseOnDismiss_.add(editor_.addBlurHandler((evt) -> {
      if (element_ != null) {
        element_.removeClassName("pm-ace-focused");
      }
      collapse_.setShowToggle(false);
    }));
    TextEditingTargetPrefsHelper.registerPrefs(releaseOnDismiss_, RStudioGinjector.INSTANCE.getUserPrefs(), null, editor_, new TextEditingTargetPrefsHelper.PrefsContext() {
      @Override public FileSystemItem getActiveFile() {
        String path = sentinel.getPath();
        if (path != null) {
          return FileSystemItem.createFile(path);
        } else {
          return null;
        }
      }
    }, TextEditingTargetPrefsHelper.PrefsSet.Embedded);
    chunk.destroy = () -> {
      for (HandlerRegistration reg : releaseOnDismiss_) {
        reg.removeHandler();
      }
      for (Command cmd : destroyHandlers_) {
        cmd.execute();
      }
    };
    chunk.setExpanded = (boolean expanded) -> {
      if (collapse_.expanded.getValue() != expanded) {
        collapse_.expanded.setValue(expanded, true);
      }
    };
    chunk.getExpanded = () -> {
      return collapse_.expanded.getValue();
    };
    setChunkExpanded(isExpanded);
    releaseOnDismiss_.add(collapse_.expanded.addValueChangeHandler((evt) -> {
      setChunkExpanded(evt.getValue());
    }));
    chunkEditor.getTextInputElement().setTabIndex(-1);
    chunkEditor.useBrowserInputFocus();
    chunkEditor.setMaxLines(1000);
    chunkEditor.setMinLines(1);
    lintManager_ = new LintManager(new VisualModeLintSource(this), releaseOnDismiss_);
    chunk_ = chunk;
  }

  public PanmirrorUIChunkEditor getEditor() {
    return chunk_;
  }

  public AceEditor getAceInstance() {
    return editor_;
  }

  /**
    * Add a callback to be invoked when the chunk editor is destroyed.
    * 
    * @param handler A callback to invoke on destruction
    * @return A registration object that can be used to unregister the callback
    */
  public HandlerRegistration addDestroyHandler(Command handler) {
    destroyHandlers_.add(handler);
    return () -> {
      destroyHandlers_.remove(handler);
    };
  }

  /**
    * Gets the scope of the code chunk (in the parent editor)
    * 
    * @return The scope, or null if unknown.
    */
  public Scope getScope() {
    return scope_;
  }

  /**
    * Updates the scope of the code chunk in the parent editor
    * 
    * @param scope
    */
  public void setScope(Scope scope) {
    scope_ = scope;
    if (toolbar_ == null) {
      createToolbar();
    } else {
      toolbar_.setScope(scope);
    }
    if (def_ != null) {
      def_.setRow(scope.getEnd().getRow());
    }
  }

  /**
    * Loads a chunk output widget into the chunk.
    * 
    * @param widget The chunk output widget
    */
  public void setOutputWidget(ChunkOutputWidget widget) {
    if (outputHost_ != null) {
      removeWidget();
      widget_ = widget;
      releaseOnDismiss_.add(widget.addVisibleChangeHandler((evt) -> {
        syncOutputClass();
      }));
      outputHost_.appendChild(widget.getElement());
      syncOutputClass();
    }
  }

  /**
    * Gets the chunk's raw definition.
    * 
    * @return The chunk definition
    */
  public ChunkDefinition getDefinition() {
    return def_;
  }

  /**
    * Sets the raw definition of the chunk.
    * 
    * @param def The chunk definition
    */
  public void setDefinition(ChunkDefinition def) {
    def_ = def;
  }

  /**
    * Gets the visual position of the chunk (from Prosemirror)
    * 
    * @return The chunk's visual position
    */
  public int getVisualPosition() {
    return chunkCallbacks_.getPos.getVisualPosition();
  }

  /**
    * Scrolls the cursor into view.
    */
  public void scrollCursorIntoView() {
    chunkCallbacks_.scrollCursorIntoView.scroll();
  }

  /**
    * Scroll the chunk's notebook output into view. 
    */
  public void scrollOutputIntoView() {
    Scheduler.get().scheduleDeferred(() -> {
      if (widget_ != null && widget_.isVisible()) {
        chunkCallbacks_.scrollIntoView.scrollIntoView(outputHost_);
      }
    });
  }

  public void reloadWidget() {
    setOutputWidget(widget_);
  }

  /**
    * Removes the output widget, if any, from the chunk.
    */
  public void removeWidget() {
    outputHost_.setInnerHTML("");
    widget_ = null;
    syncOutputClass();
  }

  /**
    * Sets the row state of a range of lines in the chunk.
    * 
    * @param start The first line of the range, counting from the first line in
    *   the chunk
    * @param end The last line of the range
    * @param state The state to apply
    * @param clazz The CSS class to use to display the state, if any
    *
    * @return A set of the row states that were created or modified
    */
  public List<VisualModeChunkRowState> setRowState(int start, int end, int state, String clazz) {
    ArrayList<VisualModeChunkRowState> states = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      if (state == ChunkRowExecState.LINE_NONE) {
        if (rowState_.containsKey(i)) {
          rowState_.get(i).detach();
          rowState_.remove(i);
        }
      } else {
        if (rowState_.containsKey(i) && rowState_.get(i).attached()) {
          rowState_.get(i).setState(state);
          states.add(rowState_.get(i));
        } else {
          if (state != ChunkRowExecState.LINE_RESTING) {
            VisualModeChunkRowState row = new VisualModeChunkRowState(state, editor_, i, clazz);
            row.attach(gutterHost_);
            states.add(row);
            rowState_.put(i, row);
          }
        }
      }
    }
    return states;
  }

  /**
    * Sets the execution state of this chunk.
    * 
    * @param state The new execution state.
    */
  public void setState(int state) {
    if (toolbar_ != null) {
      toolbar_.setState(state);
    }
  }

  /**
    * Is the editor currently active?
    * 
    * @return Whether the editor has focus.
    */
  public boolean isActive() {
    return active_;
  }

  /**
    * Sets focus to the editor instance inside the chunk.
    */
  public void focus() {
    editor_.focus();
  }

  /**
    * Returns the position/index of the chunk in the original Markdown document,
    * if known. Note that this value may not be correct if the Markdown document
    * has been mutated since the chunk was created.
    * 
    * @return The index of the chunk in Markdown
    */
  public int getMarkdownIndex() {
    return markdownIndex_;
  }

  /**
    * Sets the expansion state of the chunk. When collapsed, a chunk, and any output
    * it contains, is reduced to a one-line summary that can be expanded to reveal
    * the full chunk.
    *
    * @param expanded
    */
  public void setExpanded(boolean expanded) {
    if (expanded != collapse_.expanded.getValue()) {
      collapse_.expanded.setValue(expanded, true);
    }
  }

  /**
    * Gets the current expansion state of the chunk.
    *
    * @return The current expansion state; true if expanded, false otherwise
    */
  public boolean getExpanded() {
    return collapse_.expanded.getValue();
  }

  private void setMode(AceEditor editor, String mode) {
    switch (mode) {
      case "r":
      editor.setFileType(FileTypeRegistry.R);
      break;
      case "python":
      editor.setFileType(FileTypeRegistry.PYTHON);
      break;
      case "js":
      case "javascript":
      case "ojs":
      editor.setFileType(FileTypeRegistry.JS);
      break;
      case "tex":
      case "latex":
      editor.setFileType(FileTypeRegistry.TEX);
      break;
      case "c":
      editor.setFileType(FileTypeRegistry.C);
      break;
      case "cpp":
      editor.setFileType(FileTypeRegistry.CPP);
      break;
      case "sql":
      editor.setFileType(FileTypeRegistry.SQL);
      break;
      case "yaml-frontmatter":
      editor.setFileType(FileTypeRegistry.YAML);
      editor.getWidget().getEditor().setCompletionOptions(false, false, false, 0, 0);
      break;
      case "yaml":
      editor.setFileType(FileTypeRegistry.YAML);
      break;
      case "java":
      editor.setFileType(FileTypeRegistry.JAVA);
      break;
      case "html":
      editor.setFileType(FileTypeRegistry.HTML);
      break;
      case "shell":
      case "bash":
      editor.setFileType(FileTypeRegistry.SH);
      break;
      case "theorem":
      case "lemma":
      case "corollary":
      case "proposition":
      case "conjecture":
      case "definition":
      case "example":
      case "exercise":
      editor.setFileType(FileTypeRegistry.TEX);
      default:
      editor.setFileType(FileTypeRegistry.TEXT);
      break;
    }
  }

  /**
    * Executes the chunk via the parent editor
    */
  public void execute() {
    sync_.syncToEditor(SyncType.SyncTypeExecution, () -> {
      target_.executeChunk(Position.create(scope_.getBodyStart().getRow(), 0));
    });
  }

  /**
    * Executes the active selection via the parent editor
    */
  public void executeSelection() {
    performWithSelection((pos) -> {
      codeExecution_.executeSelection(false);
    });
  }

  /**
    * Performs an arbitrary command after synchronizing the selection state of
    * the child editor to the parent.
    * 
    * @param command The command to perform. The new position of the cursor in
    *    source mode is passed as an argument.
    */
  public void performWithSelection(CommandWithArg<Position> command) {
    sync_.syncToEditor(SyncType.SyncTypeExecution, () -> {
      performWithSyncedSelection(command);
    });
  }

  /**
    * Returns the parent editor.
    *
    * @return The parent text editing target.
    */
  public TextEditingTarget getParentEditingTarget() {
    return target_;
  }

  /**
    * Shows lint results in the chunk.
    *
    * @param lint The lint results to show.
    */
  public void showLint(JsArray<LintItem> lint) {
    lint_ = lint;
    editor_.showLint(lint);
    for (ChunkRowExecState state : rowState_.values()) {
      if (state.getState() != ChunkRowExecState.LINE_LINT && state.getState() != ChunkRowExecState.LINE_NONE) {
        return;
      }
    }
    for (ChunkRowExecState state : rowState_.values()) {
      state.detach();
    }
    rowState_.clear();
    if (!RStudioGinjector.INSTANCE.getUserPrefs().visualMarkdownCodeEditorLineNumbers().getValue()) {
      for (int i = 0; i < lint.length(); i++) {
        LintItem item = lint.get(i);
        String clazz = style_.gutterIcon() + " ";
        if (StringUtil.equals(item.getType(), "error")) {
          clazz += ThemeStyles.INSTANCE.gutterError();
        } else {
          if (StringUtil.equals(item.getType(), "info")) {
            clazz += ThemeStyles.INSTANCE.gutterInfo();
          } else {
            if (StringUtil.equals(item.getType(), "warning")) {
              clazz += ThemeStyles.INSTANCE.gutterWarning();
            }
          }
        }
        List<VisualModeChunkRowState> states = setRowState(item.getStartRow() + 1, item.getStartRow() + 1, ChunkRowExecState.LINE_LINT, clazz);
        for (VisualModeChunkRowState state : states) {
          state.setTitle(item.getText());
        }
      }
    }
  }

  private void performWithSyncedSelection(CommandWithArg<Position> command) {
    if (scope_ == null) {
      Debug.logWarning("Cannot execute selection; no selection scope available");
      return;
    }
    Range selectionRange = editor_.getSelectionRange();
    int offsetRow = scope_.getPreamble().getRow();
    selectionRange.getStart().setRow(selectionRange.getStart().getRow() + offsetRow);
    selectionRange.getEnd().setRow(selectionRange.getEnd().getRow() + offsetRow);
    String chunkPreamble = parent_.getTextForRange(Range.create(offsetRow, 0, offsetRow + 1, 0));
    int offsetCol = chunkPreamble.length() - StringUtil.trimLeft(chunkPreamble).length();
    selectionRange.getStart().setColumn(selectionRange.getStart().getColumn() + offsetCol);
    selectionRange.getEnd().setColumn(selectionRange.getEnd().getColumn() + offsetCol);
    parent_.setSelectionRange(selectionRange);
    command.execute(selectionRange.getStart());
    Scheduler.get().scheduleDeferred(() -> {
      Range postExecution = parent_.getSelectionRange();
      if (postExecution.isEqualTo(selectionRange)) {
        return;
      }
      postExecution.getStart().setRow(postExecution.getStart().getRow() - offsetRow);
      postExecution.getEnd().setRow(postExecution.getEnd().getRow() - offsetRow);
      postExecution.getStart().setColumn(postExecution.getStart().getColumn() - offsetCol);
      postExecution.getEnd().setColumn(postExecution.getEnd().getColumn() - offsetCol);
      editor_.setSelectionRange(postExecution);
    });
  }

  /**
    * Create the chunk toolbar, which hosts the execution controls (play chunk, etc.)
    */
  private void createToolbar() {
    toolbar_ = new ChunkContextPanmirrorUi(target_, scope_, editor_, false, sync_);
    if (toolbar_.getElement() != null) {
      toolbar_.getElement().addClassName(style_.toolbar());
    }
    host_.appendChild(toolbar_.getToolbar().getElement());
  }

  /**
    * Creates a wrapped version of the given completion context which adjusts
    * chunk options completion for the embedded editor.
    * 
    * @param inner The completion context to wrap
    * @return The wrapped completion context
    */
  private RnwCompletionContext wrapRnwCompletionContext(RnwCompletionContext inner) {
    return new RnwCompletionContext() {
      @Override public int getRnwOptionsStart(String line, int cursorPos) {
        int row = editor_.getSelectionStart().getRow();
        if (row > 1) {
          return -1;
        }
        return TextEditingTargetCompilePdfHelper.getRnwOptionsStart(line, cursorPos, Pattern.create("^\\s*\\{r"), null);
      }

      @Override public void getChunkOptions(ServerRequestCallback<RnwChunkOptions> requestCallback) {
        inner.getChunkOptions(requestCallback);
      }

      @Override public RnwWeave getActiveRnwWeave() {
        return inner.getActiveRnwWeave();
      }
    };
  }

  /**
    * Sets the expansion state of the code chunk
    *
    * @param expanded Whether the chunk is to be expanded.
    */
  private void setChunkExpanded(boolean expanded) {
    if (expanded) {
      if (element_ != null) {
        element_.removeClassName("pm-ace-collapsed");
      }
      summary_.setInnerHTML("");
      editor_.setReadOnly(false);
      Roles.getRegionRole().setAriaExpandedState(host_, ExpandedValue.TRUE);
    } else {
      if (element_ != null) {
        element_.addClassName("pm-ace-collapsed");
      }
      summary_.appendChild(createSummary());
      editor_.setReadOnly(true);
      A11y.setARIANotExpanded(host_);
    }
    target_.getVisualMode().nudgeSaveCollapseState();
    syncOutputClass();
  }

  /**
    * Creates a one-line summary of a chunk's contents, to be displayed when the chunk is collapsed.
    *
    * @return An HTML element summarizing the chunk.
    */
  private Element createSummary() {
    DivElement wrapper = Document.get().createDivElement();
    String contents = chunkCallbacks_.getTextContent.getTextContent();
    int lines = 0;
    String engine = "R";
    String label = "";
    String quartoLabel = "#| label:";
    for (String line : StringUtil.getLineIterator(contents)) {
      if (lines == 0) {
        if (StringUtil.equals(line.trim(), "---")) {
          engine = "YAML";
          label = "Metadata";
        } else {
          Map<String, String> options = new HashMap<>();
          if (classes_ != null && classes_.length() > 0 && !StringUtil.isNullOrEmpty(classes_.get(0))) {
            engine = classes_.get(0);
          }
          RChunkHeaderParser.parse("```" + line, engine, options);
          String optionEngine = options.get("engine");
          if (!StringUtil.isNullOrEmpty(optionEngine)) {
            engine = StringUtil.capitalize(StringUtil.stringValue(optionEngine));
          }
          String labelEngine = options.get("label");
          if (!StringUtil.isNullOrEmpty(labelEngine)) {
            label = StringUtil.stringValue(labelEngine);
          }
        }
      } else {
        if (line.startsWith(quartoLabel)) {
          label = line.substring(quartoLabel.length()).trim();
        }
      }
      lines++;
    }
    String summary = "";
    if (!StringUtil.isNullOrEmpty(label)) {
      SpanElement spanLabel = Document.get().createSpanElement();
      spanLabel.setInnerText(label + "");
      spanLabel.getStyle().setOpacity(1);
      wrapper.appendChild(spanLabel);
      summary += ": ";
    }
    summary += (lines > 1 ? constants_.visualModeChunkSummaryPlural(engine, lines) : constants_.visualModeChunkSummary(engine, lines));
    SpanElement spanSummary = Document.get().createSpanElement();
    spanSummary.setInnerText(summary);
    spanSummary.getStyle().setOpacity(0.6);
    wrapper.appendChild(spanSummary);
    return wrapper;
  }

  /**
    * Synchronize the CSS class indicating whether we have output with the output element.
    * When there's visible output the host needs to use less padding, so this must be
    * synchronized whenever visibility of the output element changes.
    */
  private void syncOutputClass() {
    if (host_ == null || element_ == null) {
      return;
    }
    String outputClass = "pm-ace-has-output";
    if (widget_ != null && widget_.isVisible()) {
      element_.addClassName(outputClass);
    } else {
      element_.removeClassName(outputClass);
    }
  }

  private ChunkDefinition def_;

  private ChunkOutputWidget widget_;

  private Scope scope_;

  private ChunkContextPanmirrorUi toolbar_;

  private boolean active_;

  private PanmirrorUIChunkCallbacks chunkCallbacks_;

  private Styles style_;

  private JsArray<LintItem> lint_;

  private final Element element_;

  private final DivElement outputHost_;

  private final DivElement host_;

  private final DivElement chunkHost_;

  private final DivElement gutterHost_;

  private final DivElement editorHost_;

  private final PanmirrorUIChunkEditor chunk_;

  private final AceEditor editor_;

  private final Element editorContainer_;

  private final DocDisplay parent_;

  private final List<Command> destroyHandlers_;

  private final ArrayList<HandlerRegistration> releaseOnDismiss_;

  private final VisualModeEditorSync sync_;

  private final EditingTargetCodeExecution codeExecution_;

  private final VisualModeCollapseToggle collapse_;

  @SuppressWarnings(value = { "unused" }) private final LintManager lintManager_;

  private final DivElement summary_;

  private final Map<Integer, VisualModeChunkRowState> rowState_;

  private final TextEditingTarget target_;

  private final int markdownIndex_;

  private static final 
  ViewsSourceConstants
   constants_ = GWT.create(ViewsSourceConstants.class);
}
