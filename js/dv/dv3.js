
function DiffViewer() {
  this.diff = false;
  this.dts = 3;
}

function handleError(xhr, mes, err) {
  console.log("[dv.DiffViewer] "+mes+" ("+err+")");
}

DiffViewer.prototype.show = function(urlb, url0, url1, urld0, urld1, elem, mode, lineb, line0, line1) {
  var self = this;

  console.log("[dv.DiffViewer] mode="+mode);

  console.log("[dv.DiffViewer] loading files...");

  $.ajax({
    type:"GET",
    mimeType:"text/plain",
    url:urlb,
    error:handleError
  }).done(function(original, status0, xhr0) {
    console.log("[dv.DiffViewer] original source file loaded.");
    $.ajax({
      type:"GET",
      mimeType:"text/plain",
      url:url0,
      error:handleError
    }).done(function(modified0, status1, xhr1) {
      console.log("[dv.DiffViewer] modified source file (0) loaded.");
      $.ajax({
        type:"GET",
        mimeType:"text/plain",
        url:url1,
        error:handleError
      }).done(function(modified1, status2, xhr2) {
        console.log("[dv.DiffViewer] modified source file (1) loaded.");
        $.ajax({
	  type:"GET",
          mimeType:"application/json",
	  url:urld0,
          error:handleError
        }).done(function(diff0, status3, xhr3) {
          console.log("[dv.DiffViewer] diff file (0) loaded: " + diff0);
          $.ajax({
	    type:"GET",
            mimeType:"application/json",
	    url:urld1,
            error:handleError
          }).done(function(diff1, status4, xhr4) {
            console.log("[dv.DiffViewer] diff file (1) loaded: " + diff1);

	    self.mv = CodeMirror.MergeView(elem,{
              revertButtons:false,
              diffLeft:diff0,
              diffRight:diff1,
              value:original,
	      origLeft:modified0,
	      origRight:modified1,
	      mode:mode,
              scrollbarStyle:"simple",
              foldGutter:true,
              gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
              lineNumbers:true,
              lineSeparator:'\n',
              line:lineb,
              lineLeft:line0,
              lineRight:line1,
              showDifferences:self.diff,
              showDts:self.dts,
	    });
            self.mv.init();

          })
        })
      })
    })
  })
}

DiffViewer.prototype.toggleDifferences = function() {
  this.mv.setShowDifferences(this.diff = !this.diff);
}

DiffViewer.prototype.toggleDts = function() {
  this.mv.setShowDts(this.dts = this.dts > 2 ? 0 : this.dts + 1);
}
