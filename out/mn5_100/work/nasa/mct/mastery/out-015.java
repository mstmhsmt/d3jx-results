package gov.nasa.arc.mct.fastplot.bridge;

import gov.nasa.arc.mct.components.FeedProvider;
import gov.nasa.arc.mct.fastplot.utils.AbbreviatingPlotLabelingAlgorithm;
import gov.nasa.arc.mct.fastplot.utils.TruncatingLabel;
import gov.nasa.arc.mct.fastplot.view.LegendEntryPopupMenuFactory;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plotter.xy.LinearXYPlotLine;
import gov.nasa.arc.mct.fastplot.bridge.PlotAbstraction.LineSettings;
import gov.nasa.arc.mct.util.LafColor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

@SuppressWarnings("serial")
public class LegendEntry extends JPanel implements MouseListener {

    private final static Logger logger = LoggerFactory.getLogger(LegendEntry.class);

    private final static int LEFT_PADDING = 5;

    private final static Border PANEL_PADDING = BorderFactory.createEmptyBorder(0, LEFT_PADDING, 0, 0);

    private LinearXYPlotLine linePlot;

    protected JLabel baseDisplayNameLabel = new TruncatingLabel();

    private Color backgroundColor;

    private Color foregroundColor;

    private Color originalPlotLineColor;

    private Stroke originalPlotLineStroke;

    private Font originalFont;

    private Font boldFont;

    private Font strikeThruFont;

    private Font boldStrikeThruFont;

    private String baseDisplayName = "";

    boolean selected = false;

    private String currentToolTipTxt = "";

    private ToolTipManager toolTipManager;

    private String thisBaseDisplayName = "";

    private String valueString = "";

    private AbbreviatingPlotLabelingAlgorithm plotLabelingAlgorithm = new AbbreviatingPlotLabelingAlgorithm();

    private String computedBaseDisplayName = "";

    private FeedProvider.RenderingInfo renderingInfo;

    private LegendEntryPopupMenuFactory popupManager = null;

    private int baseWidth = PlotConstants.PLOT_LEGEND_WIDTH;

    LegendEntry(Color theBackgroundColor, Color theForegroundColor, Font font, AbbreviatingPlotLabelingAlgorithm thisPlotLabelingAlgorithm) {
        setBorder(EMPTY_BORDER);
        plotLabelingAlgorithm = thisPlotLabelingAlgorithm;
        backgroundColor = theBackgroundColor;
        foregroundColor = theForegroundColor;
        setForeground(foregroundColor);
        lineSettings.setMarker(lineSettings.getColorIndex());
        focusBorder = BorderFactory.createLineBorder(theForegroundColor);
        originalFont = font;
        originalFont = originalFont.deriveFont((float) (originalFont.getSize() - 1));
        boldFont = originalFont.deriveFont(Font.BOLD);
        Map<TextAttribute, Object> attributes = new Hashtable<TextAttribute, Object>();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        strikeThruFont = originalFont.deriveFont(attributes);
        boldStrikeThruFont = boldFont.deriveFont(attributes);
        baseDisplayNameLabel.setBackground(backgroundColor);
        baseDisplayNameLabel.setForeground(foregroundColor);
        baseDisplayNameLabel.setFont(originalFont);
        baseDisplayNameLabel.setOpaque(true);
        toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.setEnabled(true);
        toolTipManager.setLightWeightPopupEnabled(true);
        toolTipManager.setDismissDelay(PlotConstants.MILLISECONDS_IN_SECOND * 3);
        toolTipManager.setInitialDelay(PlotConstants.MILLISECONDS_IN_SECOND / 2);
        toolTipManager.setReshowDelay(PlotConstants.MILLISECONDS_IN_SECOND / 2);
        layoutLabels();
        addMouseListener(this);
    }

    void setPlot(LinearXYPlotLine thePlot) {
        linePlot = thePlot;
        updateLinePlotFromSettings();
    }

    private List<String> getPanelOrWindowContextTitleList() {
        List<String> panelOrWindowContextTitleList = new ArrayList<String>();
        panelOrWindowContextTitleList.clear();
        if (plotLabelingAlgorithm != null) {
            if (plotLabelingAlgorithm.getPanelContextTitleList().size() > 0) {
                panelOrWindowContextTitleList.addAll(this.plotLabelingAlgorithm.getPanelContextTitleList());
            }
            if (plotLabelingAlgorithm.getCanvasContextTitleList().size() > 0) {
                panelOrWindowContextTitleList.addAll(this.plotLabelingAlgorithm.getCanvasContextTitleList());
            }
        } else {
            logger.error("Plot labeling algorithm object is NULL!");
        }
        return panelOrWindowContextTitleList;
    }

    public void setBaseDisplayName(String theBaseDisplayName) {
        thisBaseDisplayName = theBaseDisplayName;
        if (thisBaseDisplayName != null) {
            thisBaseDisplayName = thisBaseDisplayName.trim();
        }
        baseDisplayName = thisBaseDisplayName;
        String[] strings = baseDisplayName.split(PlotConstants.LEGEND_NEWLINE_CHARACTER);
        if (strings.length <= 1) {
            if (baseDisplayName.indexOf(PlotConstants.LEGEND_NEWLINE_CHARACTER) == -1) {
                baseDisplayNameLabel.setText(baseDisplayName);
            } else if (theBaseDisplayName.equals(PlotConstants.LEGEND_NEWLINE_CHARACTER)) {
                baseDisplayNameLabel.setText("");
            } else {
                baseDisplayNameLabel.setText(PlotConstants.LEGEND_ELLIPSES);
            }
        } else {
            String line1 = strings[0];
            if (line1 != null) {
                baseDisplayName = line1.trim();
                thisBaseDisplayName = baseDisplayName;
            }
            List<String> baseDisplayNameList = new ArrayList<String>();
            baseDisplayNameList.add(line1);
            assert plotLabelingAlgorithm != null : "Plot labeling algorithm should NOT be NULL at this point.";
            baseDisplayName = plotLabelingAlgorithm.computeLabel(baseDisplayNameList, getPanelOrWindowContextTitleList());
            if (baseDisplayName != null && baseDisplayName.isEmpty()) {
                baseDisplayName = thisBaseDisplayName;
            }
            computedBaseDisplayName = baseDisplayName;
            updateLabelText();
        }
        thisBaseDisplayName = theBaseDisplayName.replaceAll(PlotConstants.WORD_DELIMITERS, " ");
        currentToolTipTxt = "<HTML>" + thisBaseDisplayName.replaceAll(PlotConstants.LEGEND_NEWLINE_CHARACTER, "<BR>") + "<BR>" + valueString + "<HTML>";
        this.setToolTipText(currentToolTipTxt);
    }

    void setData(FeedProvider.RenderingInfo info) {
        this.renderingInfo = info;
        String valueText = info.getValueText();
        if (!"".equals(valueText)) {
            valueString = PlotConstants.DECIMAL_FORMAT.format(Double.parseDouble(valueText));
        }
        updateLabelFont();
        updateLabelText();
        thisBaseDisplayName = thisBaseDisplayName.replaceAll(PlotConstants.WORD_DELIMITERS, " ");
        currentToolTipTxt = "<HTML>" + thisBaseDisplayName.replaceAll(PlotConstants.LEGEND_NEWLINE_CHARACTER, "<BR>") + "<BR>" + valueString + "<HTML>";
        this.setToolTipText(currentToolTipTxt);
    }

    private void updateLabelFont() {
        if (selected) {
            if (renderingInfo == null || renderingInfo.isPlottable()) {
                baseDisplayNameLabel.setFont(boldFont);
            } else {
                baseDisplayNameLabel.setFont(boldStrikeThruFont);
            }
        } else {
            if (renderingInfo == null || renderingInfo.isPlottable()) {
                baseDisplayNameLabel.setFont(originalFont);
            } else {
                baseDisplayNameLabel.setFont(strikeThruFont);
            }
        }
    }

    private void updateLabelText() {
        String statusText = renderingInfo == null ? null : renderingInfo.getStatusText();
        if (statusText == null) {
            statusText = "";
        }
        statusText = statusText.trim();
        if (!"".equals(statusText)) {
            baseDisplayNameLabel.setText("(" + statusText + ") " + baseDisplayName);
        } else {
            baseDisplayNameLabel.setText(baseDisplayName);
        }
    }

    private void updateLabelWidth() {
        Font f = baseDisplayNameLabel.getFont();
        String s = baseDisplayNameLabel.getText();
        if (f == originalFont && s.equals(baseDisplayName) && baseDisplayNameLabel.isValid()) {
            baseWidth = baseDisplayNameLabel.getWidth();
        }
    }

    void layoutLabels() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setBorder(PANEL_PADDING);
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel displayNamePanel = new JPanel();
        displayNamePanel.setLayout(new BoxLayout(displayNamePanel, BoxLayout.LINE_AXIS));
        displayNamePanel.add(baseDisplayNameLabel);
        displayNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(displayNamePanel);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        toolTipManager.registerComponent(this);
        if (!selected) {
            originalPlotLineColor = linePlot.getForeground();
            originalPlotLineStroke = linePlot.getStroke();
        }
        selected = true;
        baseDisplayNameLabel.setForeground(foregroundColor.brighter());
        updateLabelFont();
        linePlot.setForeground(originalPlotLineColor.brighter());
        if (originalPlotLineStroke == null) {
            linePlot.setStroke(new BasicStroke(PlotConstants.SELECTED_LINE_THICKNESS));
        } else if (originalPlotLineStroke instanceof BasicStroke) {
            BasicStroke stroke = (BasicStroke) originalPlotLineStroke;
            linePlot.setStroke(new BasicStroke(stroke.getLineWidth() * PlotConstants.SELECTED_LINE_THICKNESS, stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), stroke.getDashArray(), stroke.getDashPhase()));
        }
        if (regressionLine != null) {
            originalRegressionLineStroke = regressionLine.getStroke();
            regressionLine.setForeground(originalPlotLineColor.brighter().brighter());
            stroke = (BasicStroke) regressionLine.getStroke();
            if (stroke == null) {
                regressionLine.setStroke(new BasicStroke(PlotConstants.SLOPE_LINE_WIDTH * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, PlotConstants.dash1, 0.0f));
            } else {
                regressionLine.setStroke(new BasicStroke(PlotConstants.SLOPE_LINE_WIDTH * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, PlotConstants.dash1, 0.0f));
            }
        }
        this.setToolTipText(currentToolTipTxt);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        toolTipManager.unregisterComponent(this);
        selected = false;
        baseDisplayNameLabel.setForeground(foregroundColor);
        updateLabelFont();
        linePlot.setForeground(originalPlotLineColor);
        linePlot.setStroke(originalPlotLineStroke);
        if (regressionLine != null) {
            regressionLine.setForeground(originalPlotLineColor);
            regressionLine.setStroke(originalRegressionLineStroke);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (popupManager != null && e.isPopupTrigger()) {
            setBorder(focusBorder);
            JPopupMenu popup = popupManager.getPopup(this);
            popup.show(this, e.getX(), e.getY());
            popup.addPopupMenuListener(new PopupMenuListener() {

                @Override
                public void popupMenuCanceled(PopupMenuEvent arg0) {
                    setBorder(EMPTY_BORDER);
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
                    setBorder(EMPTY_BORDER);
                }

                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
                }
            });
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (popupManager != null && e.isPopupTrigger()) {
            popupManager.getPopup(this).show(this, e.getX(), e.getY());
        }
    }

    public String getToolTipText() {
        return currentToolTipTxt;
    }

    public String getBaseDisplayNameLabel() {
        return baseDisplayNameLabel.getText();
    }

    public String getFullBaseDisplayName() {
        return thisBaseDisplayName;
    }

    public String getComputedBaseDisplayName() {
        return computedBaseDisplayName;
    }

    public String getTruncatedBaseDisplayName() {
        return baseDisplayName;
    }

    public void setPlotLabelingAlgorithm(AbbreviatingPlotLabelingAlgorithm plotLabelingAlgorithm) {
        this.plotLabelingAlgorithm = plotLabelingAlgorithm;
    }

    public AbbreviatingPlotLabelingAlgorithm getPlotLabelingAlgorithm() {
        return this.plotLabelingAlgorithm;
    }

    public int getLabelWidth() {
        updateLabelWidth();
        return baseWidth + LEFT_PADDING;
    }

    @Override
    public void setForeground(Color fg) {
        Color lineColor = fg;
        Color labelColor = fg;
        if (linePlot != null) {
            if (linePlot.getForeground() != foregroundColor)
                lineColor = fg;
            linePlot.setForeground(lineColor);
        }
        if (regressionLine != null) {
            if (regressionLine.getForeground() != foregroundColor)
                lineColor = fg.brighter().brighter();
            regressionLine.setForeground(lineColor);
        }
        if (baseDisplayNameLabel != null) {
            if (baseDisplayNameLabel.getForeground() != foregroundColor)
                labelColor = fg.brighter();
            baseDisplayNameLabel.setForeground(labelColor);
        }
        foregroundColor = fg;
        for (int i = 0; i < PlotConstants.MAX_NUMBER_OF_DATA_ITEMS_ON_A_PLOT; i++) {
            if (PlotLineColorPalette.getColor(i).getRGB() == fg.getRGB()) {
                lineSettings.setColorIndex(i);
            }
        }
        super.setForeground(fg);
    }

    public LegendEntryPopupMenuFactory getPopup() {
        return popupManager;
    }

    public void setPopup(LegendEntryPopupMenuFactory popup) {
        this.popupManager = popup;
    }

    public LinearXYPlotLine getRegressionLine() {
        return regressionLine;
    }

    public int getNumberRegressionPoints() {
        return numberRegressionPoints;
    }

    public boolean hasRegressionLine() {
        return hasRegressionLine;
    }

    private void updateLinePlotFromSettings() {
        int index = lineSettings.getColorIndex();
        Color c = PlotLineColorPalette.getColor(index);
        setForeground(c);
        Stroke s = linePlot.getStroke();
        if (s == null || s instanceof BasicStroke) {
            int t = lineSettings.getThickness();
            linePlot.setStroke(t == 1 ? null : new BasicStroke(t));
            originalPlotLineStroke = linePlot.getStroke();
        }
        if (linePlot.getPointIcon() != null) {
            Shape shape = null;
            if (lineSettings.getUseCharacter()) {
                Graphics g = (Graphics) getGraphics();
                if (g != null && g instanceof Graphics2D) {
                    FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();
                    shape = PlotLineShapePalette.getShape(lineSettings.getCharacter(), frc);
                }
            } else {
                int marker = lineSettings.getMarker();
                shape = PlotLineShapePalette.getShape(marker);
            }
            if (shape != null) {
                linePlot.setPointIcon(new PlotMarkerIcon(shape));
                baseDisplayNameLabel.setIcon(new PlotMarkerIcon(shape, false, 12, 12));
            }
        }
        linePlot.repaint();
        repaint();
    }

    private boolean hasRegressionLine = false;

    private LinearXYPlotLine regressionLine;

    private int numberRegressionPoints = PlotConstants.NUMBER_REGRESSION_POINTS;

    private LineSettings lineSettings = new LineSettings();

    private Stroke originalRegressionLineStroke;

    public void setRegressionLine(LinearXYPlotLine regressionLine) {
        this.regressionLine = regressionLine;
        if (regressionLine != null)
            regressionLine.setForeground(foregroundColor);
    }

    public LineSettings getLineSettings() {
        return lineSettings;
    }

    public void setNumberRegressionPoints(int numberRegressionPoints) {
        this.numberRegressionPoints = numberRegressionPoints;
    }

    public void setHasRegressionLine(boolean regressionLine) {
        this.hasRegressionLine = regressionLine;
    }

    private Border focusBorder = BorderFactory.createLineBorder(LafColor.TEXT_HIGHLIGHT);

    private final static Border EMPTY_BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);

    public void setLineSettings(LineSettings settings) {
        lineSettings = settings;
        updateLinePlotFromSettings();
    }

    private class ShapeIcon implements Icon {

        @Override
        public int getIconHeight() {
            return linePlot != null && linePlot.getPointIcon() != null ? 12 : 0;
        }

        @Override
        public int getIconWidth() {
            return linePlot != null && linePlot.getPointIcon() != null ? 12 : 0;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (linePlot != null && linePlot.getPointIcon() != null)
                linePlot.getPointIcon().paintIcon(c, g, x + 6, y + 6);
        }
    }
}
