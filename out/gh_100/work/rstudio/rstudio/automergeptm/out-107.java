package org.rstudio.studio.client.workbench.views.source.editors.text.visualmode;
import java.util.ArrayList;
import java.util.List;
import org.rstudio.core.client.Debug;
import org.rstudio.studio.client.panmirror.ui.PanmirrorUIChunks;
import org.rstudio.studio.client.workbench.views.source.editors.text.DocDisplay;
import org.rstudio.studio.client.workbench.views.source.editors.text.Scope;
import org.rstudio.studio.client.workbench.views.source.editors.text.ScopeList;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTarget;
import org.rstudio.studio.client.workbench.views.source.editors.text.TextEditingTargetScopeHelper;
import org.rstudio.studio.client.workbench.views.source.editors.text.ace.Position;
import org.rstudio.studio.client.workbench.views.source.editors.text.rmd.ChunkDefinition;
import org.rstudio.studio.client.workbench.views.source.editors.text.rmd.TextEditingTargetNotebook;
import org.rstudio.studio.client.workbench.views.source.model.DocUpdateSentinel;
import com.google.gwt.core.client.JsArray;
import org.rstudio.core.client.CommandWithArg;
import com.google.gwt.user.client.Command;

public class VisualModeChunks implements ChunkDefinition.Provider {
  public VisualModeChunks(DocUpdateSentinel sentinel, DocDisplay display, TextEditingTarget target, VisualModeEditorSync sync) {
    target_ = target;
    sentinel_ = sentinel;
    parent_ = display;
    sync_ = sync;
    chunks_ = new ArrayList<VisualModeChunk>();
  }

  public PanmirrorUIChunks uiChunks() {
    PanmirrorUIChunks chunks = new PanmirrorUIChunks();
    chunks.createChunkEditor = (type, index, callbacks) -> {
      if (!type.equals("ace")) {
        Debug.logToConsole("Unknown chunk editor type: " + type);
        return null;
      }
      VisualModeChunk chunk = new VisualModeChunk(index, callbacks, sentinel_, target_, sync_);
      chunks_.add(chunk);
      chunk.addDestroyHandler(() -> {
        chunks_.remove(chunk);
      });
      return chunk.getEditor();
    };
    return chunks;
  }

  /**
    * Finds the visual mode chunk editor corresponding to a given document row.
    * 
    * @param row The document row.
    * @return A visual mode chunk editor at the given row, or null if one was
    *   not found.
    */
  public VisualModeChunk getChunkAtRow(int row) {
    for (VisualModeChunk chunk : chunks_) {
      Scope scope = chunk.getScope();
      if (scope == null) {
        continue;
      }
      if (row >= scope.getPreamble().getRow() && row <= scope.getEnd().getRow()) {
        return chunk;
      }
    }
    return null;
  }

  /**
    * Find the visual mode chunk editor corresponding to the given visual
    * position.
    * 
    * @param pos The visual position (as reported by prosemirror)
    * 
    * @return A visual mode chunk editor at the given position, or null if one
    *   was not found.
    */
  public VisualModeChunk getChunkAtVisualPosition(int pos) {
    for (VisualModeChunk chunk : chunks_) {
      if (chunk.getVisualPosition() == pos) {
        return chunk;
      }
    }
    return null;
  }

  /**
    * Executes the currently active chunk
    */
  public void executeCurrentChunk() {
    withActiveChunk((chunk) -> {
      chunk.execute();
    });
  }

  /**
    * Executes all previous chunks
    */
  public void executePreviousChunks() {
    withActiveChunk((chunk) -> {
      target_.executeChunks(Position.create(chunk.getScope().getBodyStart().getRow(), 0), TextEditingTargetScopeHelper.PREVIOUS_CHUNKS);
    });
  }

  /**
    * Make a list of the chunk definitions known in visual mode.
    */
  public JsArray<ChunkDefinition> getChunkDefs() {
    JsArray<ChunkDefinition> defs = JsArray.createArray().cast();
    ScopeList scopes = new ScopeList(parent_);
    for (VisualModeChunk chunk : chunks_) {
      ChunkDefinition def = chunk.getDefinition();
      Scope scope = chunk.getScope();
      if (def != null && scope != null) {
        int row = scope.getEnd().getRow();
        defs.push(def.with(row, TextEditingTargetNotebook.getKnitrChunkLabel(row, parent_, scopes)));
      }
    }
    return defs;
  }

  /**
    * Sets the execution state of a range of lines in the visual editor.
    * 
    * @param start The first line
    * @param end The last line
    * @param state The execution state
    */
  public void setChunkLineExecState(int start, int end, int state) {
    for (VisualModeChunk chunk : chunks_) {
      Scope scope = chunk.getScope();
      if (scope == null) {
        continue;
      }
      if (start >= scope.getPreamble().getRow() && end <= scope.getEnd().getRow()) {
        int offset = scope.getPreamble().getRow();
        chunk.setLineExecState(start - offset, end - offset, state);
        break;
      }
    }
  }

  /**
    * Sets the execution state of a single chunk.
    * 
    * @param target The location of the chunk
    * @param state The chunk's new state
    */
  public void setChunkState(Scope target, int state) {
    for (VisualModeChunk chunk : chunks_) {
      Scope scope = chunk.getScope();
      if (scope != null && scope.getPreamble().getRow() == target.getPreamble().getRow()) {
        chunk.setState(state);
        break;
      }
    }
  }

  private final VisualModeEditorSync sync_;

  private final List<VisualModeChunk> chunks_;

  private final DocUpdateSentinel sentinel_;

  private final DocDisplay parent_;

  private final TextEditingTarget target_;

  /**
    * Executes the next chunk
    */
  public void executeNextChunk() {
    boolean next = false;
    for (VisualModeChunk chunk : chunks_) {
      if (next) {
        chunk.focus();
        chunk.execute();
        break;
      }
      if (chunk.isActive()) {
        next = true;
      }
    }
  }

  /**
    * Performs an arbitrary command after synchronizing the selection state of
    * the child editor to the parent.
    * 
    * @param command The command to perform.
    */
  public void performWithSelection(Command command) {
    withActiveChunk((chunk) -> {
      chunk.performWithSelection(command);
    });
  }

  private void withActiveChunk(CommandWithArg<VisualModeChunk> command) {
    for (VisualModeChunk chunk : chunks_) {
      if (chunk.isActive()) {
        command.execute(chunk);
        break;
      }
    }
  }
}