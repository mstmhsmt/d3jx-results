package gov.nasa.arc.mct.fastplot.view;

import gov.nasa.arc.mct.components.AbstractComponent;
import gov.nasa.arc.mct.components.FeedProvider;
import gov.nasa.arc.mct.fastplot.bridge.PlotConstants;
import gov.nasa.arc.mct.fastplot.bridge.PlotView;
import gov.nasa.arc.mct.fastplot.settings.PlotConfiguration;
import gov.nasa.arc.mct.fastplot.settings.PlotSettings;
import gov.nasa.arc.mct.fastplot.settings.PlotSettingsControlContainer;
import gov.nasa.arc.mct.fastplot.utils.AbbreviatingPlotLabelingAlgorithm;
import gov.nasa.arc.mct.fastplot.utils.ComponentTraverser;
import gov.nasa.arc.mct.gui.FeedView;
import gov.nasa.arc.mct.gui.FeedView.RenderingCallback;
import gov.nasa.arc.mct.gui.NamingContext;
import gov.nasa.arc.mct.roles.events.AddChildEvent;
import gov.nasa.arc.mct.roles.events.PropertyChangeEvent;
import gov.nasa.arc.mct.roles.events.RemoveChildEvent;
import gov.nasa.arc.mct.services.activity.TimeService;
import gov.nasa.arc.mct.services.component.ViewInfo;
import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gov.nasa.arc.mct.components.FeedInfoProvider.FeedInfo;
import gov.nasa.arc.mct.components.FeedInfoProvider;
import java.text.ParseException;
import gov.nasa.arc.mct.components.FeedFilterProvider.FeedFilter;
import gov.nasa.arc.mct.components.FeedFilterProvider;

@SuppressWarnings("serial")
public class PlotViewManifestation extends FeedView implements RenderingCallback {

    private final static Logger logger = LoggerFactory.getLogger(PlotViewManifestation.class);

    private AbbreviatingPlotLabelingAlgorithm plotLabelingAlgorithm = new AbbreviatingPlotLabelingAlgorithm();

    private JPanel theView;

    private List<String> canvasContextTitleList = new ArrayList<String>();

    private List<String> panelContextTitleList = new ArrayList<String>();

    private Color plotFrameBackground;

    private PlotView thePlot;

    private PlotDataAssigner plotDataAssigner = new PlotDataAssigner(this);

    private PlotDataFeedUpdateHandler plotDataFeedUpdateHandler = new PlotDataFeedUpdateHandler(this);

    private PlotPersistenceHandler plotPersistenceHandler = new PlotPersistenceHandler(this);

    private SwingWorker<Map<String, List<Map<String, String>>>, Map<String, List<Map<String, String>>>> currentDataRequest;

    private SwingWorker<Map<String, List<Map<String, String>>>, Map<String, List<Map<String, String>>>> currentPredictionRequest;

    private List<Runnable> feedCallbacks = new ArrayList<Runnable>();

    JComponent controlPanel;

    final static public String VIEW_ROLE_NAME = "Plot";

    public PlotViewManifestation(AbstractComponent component, ViewInfo vi) {
        super(component, vi);
        plotFrameBackground = getColor("plotFrame.background");
        if (plotFrameBackground == null)
            plotFrameBackground = PlotConstants.DEFAULT_PLOT_FRAME_BACKGROUND_COLOR;
        plotLabelingAlgorithm.setName("plotLabelingAlgorithm");
        setLabelingContext(plotLabelingAlgorithm, getNamingContext());
        generatePlot();
        setFocusable(true);
        assert thePlot != null : "Plot should not be null at this point";
    }

    @Override
    public FeedProvider getFeedProvider(AbstractComponent component) {
        List<FeedProvider> feedProviders = component.getCapabilities(FeedProvider.class);
        FeedInfoProvider feedInfoProvider = component.getCapability(FeedInfoProvider.class);
        PlotConfiguration settings = plotPersistenceHandler.loadPlotSettingsFromPersistance();
        String persistedTimeSystem = settings != null ? settings.getTimeSystemSetting() : null;
        String persistedFeedType = settings != null ? settings.getFeedTypeSetting() : null;
        String assignedTimeSystem = (plotDataAssigner != null) ? plotDataAssigner.getTimeSystemDefaultChoice() : null;
        String assignedFeedType = null;
        String filterTimeSystem = (persistedTimeSystem != null && !persistedTimeSystem.isEmpty()) ? persistedTimeSystem : assignedTimeSystem;
        String filterFeedType = (persistedFeedType != null && !persistedFeedType.isEmpty()) ? persistedFeedType : assignedFeedType;
        if (filterTimeSystem != null && feedProviders != null && feedProviders.size() > 0) {
            for (FeedProvider fp : feedProviders) {
                if (matchFeedProvider(fp, feedInfoProvider, filterTimeSystem, filterFeedType)) {
                    return fp;
                }
            }
        }
        return component.getCapability(FeedProvider.class);
    }

    @Override
    protected void handleNamingContextChange() {
        updateMonitoredGUI();
    }

    private Color getColor(String name) {
        return UIManager.getColor(name);
    }

    @Override
    protected JComponent initializeControlManifestation() {
        controlPanel = new PlotSettingsControlContainer(this);
        return controlPanel;
    }

    public void setupPlot(PlotSettings settings) {
        plotPersistenceHandler.persistPlotSettings(settings);
    }

    public void persistPlotLineSettings() {
        if (thePlot != null)
            plotPersistenceHandler.persistLineSettings(thePlot.getLineSettings());
    }

    @Override
    public void updateMonitoredGUI() {
        setLabelingContext(plotLabelingAlgorithm, getNamingContext());
        if (thePlot != null) {
            respondToSettingsChange();
        }
    }

    @Override
    public void updateMonitoredGUI(PropertyChangeEvent evt) {
        updateMonitoredGUI();
    }

    @Override
    public void updateMonitoredGUI(AddChildEvent event) {
        setLabelingContext(plotLabelingAlgorithm, getNamingContext());
        respondToChildChangeEvent();
    }

    @Override
    public void updateMonitoredGUI(RemoveChildEvent event) {
        setLabelingContext(plotLabelingAlgorithm, getNamingContext());
        respondToChildChangeEvent();
    }

    private void respondToChildChangeEvent() {
        generatePlot();
    }

    private void respondToSettingsChange() {
        generatePlot();
        if (controlPanel != null) {
        }
    }

    public String[] getTimeSystemChoices() {
        Set<String> s = plotDataAssigner.getTimeSystemChoices();
        return s.toArray(new String[s.size()]);
    }

    public String[] getTimeFormatChoices() {
        Set<String> s = plotDataAssigner.getTimeFormatChoices();
        return s.toArray(new String[s.size()]);
    }

    private void generatePlot() {
        plotDataAssigner.informFeedProvidersHaveChanged();
        createPlotAndAddItToPanel();
        thePlot.initialDataRequest();
        plotDataAssigner.assignFeedsToSubPlots();
        enforceBackgroundColor(plotFrameBackground);
        thePlot.addPopupMenus();
        thePlot.setLineSettings(plotPersistenceHandler.loadLineSettingsFromPersistence());
    }

    @Override
    public Collection<FeedProvider> getVisibleFeedProviders() {
        return plotDataAssigner.getVisibleFeedProviders();
    }

    private long getPointTime(Map<String, String> data) {
        return Long.parseLong(data.get(FeedProvider.NORMALIZED_TIME_KEY));
    }

    private void expandData(Map<String, List<Map<String, String>>> expandedData, final long startTime, final long endTime) {
        for (FeedProvider fp : getVisibleFeedProviders()) {
            List<Map<String, String>> points = expandedData.get(fp.getSubscriptionId());
            if (points != null && !points.isEmpty()) {
                if (fp.isNonCODDataBuffer()) {
                    continue;
                }
                List<Map<String, String>> expandedPoints = new ArrayList<Map<String, String>>();
                expandedData.put(fp.getSubscriptionId(), expandedPoints);
                long now = fp.getTimeService().getCurrentTime();
                for (int i = 0; i < points.size(); i++) {
                    Map<String, String> point = points.get(i);
                    expandedPoints.add(point);
                    long pointTime = getPointTime(point);
                    assert pointTime >= startTime : "point time is less than start time";
                    pointTime = Math.max(pointTime, startTime);
                    long nextPointTime = (points.size() > i + 1) ? getPointTime(points.get(i + 1)) : Math.min(now, endTime) + 1000;
                    for (long currentTime = pointTime + 1000; currentTime <= nextPointTime - 1000; currentTime += 1000) {
                        Map<String, String> newPoint = new HashMap<String, String>(point);
                        newPoint.put(FeedProvider.NORMALIZED_TIME_KEY, Long.toString(currentTime));
                        expandedPoints.add(newPoint);
                    }
                }
            }
        }
    }

    private DataTransformation getTransformation() {
        return new DataTransformation() {

            @Override
            public void transform(Map<String, List<Map<String, String>>> data, long startTime, long endTime) {
                expandData(data, startTime, endTime);
            }
        };
    }

    public void requestPredictiveData(GregorianCalendar startTime, GregorianCalendar endTime) {
        assert currentPredictionRequest == null : "prediction request should not be outstanding";
        if (plotDataAssigner.getPredictiveFeedProviders().isEmpty()) {
            return;
        }
        currentPredictionRequest = this.requestData(plotDataAssigner.getPredictiveFeedProviders(), startTime.getTimeInMillis(), endTime.getTimeInMillis(), getTransformation(), new RenderingCallback() {

            @Override
            public void render(Map<String, List<Map<String, String>>> data) {
                plotDataFeedUpdateHandler.updateFromFeed(data, true);
            }
        }, false);
        currentPredictionRequest.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (currentPredictionRequest == evt.getSource() && evt.getNewValue() == SwingWorker.StateValue.DONE) {
                    assert SwingUtilities.isEventDispatchThread();
                    currentPredictionRequest = null;
                }
            }
        });
    }

    public void requestDataRefresh(GregorianCalendar startTime, GregorianCalendar endTime) {
        if (plotDataAssigner.hasFeeds()) {
            cancelAnyOutstandingRequests();
            currentDataRequest = this.requestData(null, startTime.getTimeInMillis(), endTime.getTimeInMillis(), getTransformation(), this, true);
            currentDataRequest.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (currentDataRequest.getState() == SwingWorker.StateValue.STARTED && evt.getOldValue() == SwingWorker.StateValue.PENDING) {
                        plotDataFeedUpdateHandler.startDataRequest();
                    }
                    if (currentDataRequest == evt.getSource() && evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        assert SwingUtilities.isEventDispatchThread();
                        currentDataRequest = null;
                        plotDataFeedUpdateHandler.endDataRequest();
                    }
                }
            });
        }
    }

    private void cancelOutstandingPredictionRequests() {
        logger.debug("PlotViewRole.cancelOutstandingPredictionRequests()");
        if (currentPredictionRequest != null) {
            currentPredictionRequest.cancel(false);
            currentPredictionRequest = null;
        }
    }

    private void cancelAnyOutstandingRequests() {
        logger.debug("PlotViewRole.cancelAnyOutstandingRequests()");
        if (currentDataRequest != null) {
            currentDataRequest.cancel(false);
        }
        cancelOutstandingPredictionRequests();
    }

    @Override
    public void synchronizeTime(Map<String, List<Map<String, String>>> data, long syncTime) {
        plotDataFeedUpdateHandler.synchronizeTime(data, syncTime);
    }

    @Override
    protected void synchronizationDone() {
        thePlot.removeTimeSyncLine();
    }

    @Override
    public void clear(Collection<FeedProvider> feedProviders) {
        updateMonitoredGUI();
    }

    @Override
    public void updateFromFeed(Map<String, List<Map<String, String>>> data) {
        plotDataFeedUpdateHandler.updateFromFeed(data, false);
        for (Runnable r : feedCallbacks) {
            SwingUtilities.invokeLater(r);
        }
    }

    @Override
    public void render(Map<String, List<Map<String, String>>> data) {
        plotDataFeedUpdateHandler.processData(data);
    }

    public double getMaxFeedValue() {
        return thePlot.getNonTimeMaxCurrentlyDisplayed();
    }

    public double getMinFeedValue() {
        return thePlot.getNonTimeMinCurrentlyDisplayed();
    }

    public long getCurrentMCTTime() {
        long cachedTime = System.currentTimeMillis();
        AbstractComponent manifestedComponent = getManifestedComponent();
        if (manifestedComponent != null) {
            Collection<FeedProvider> feedproviders = getVisibleFeedProviders();
            if (!feedproviders.isEmpty()) {
                Iterator<FeedProvider> feedIterator = feedproviders.iterator();
                FeedProvider firstProvider = feedIterator.next();
                FeedProvider fp = firstProvider;
                while (fp.isPrediction() && feedIterator.hasNext()) {
                    fp = feedIterator.next();
                }
                if (fp.isPrediction())
                    fp = firstProvider;
                long currentTimeInMillis = fp.getTimeService().getCurrentTime();
                if (currentTimeInMillis >= 0) {
                    cachedTime = currentTimeInMillis;
                } else {
                    logger.error("FeedProvider currentTimeMillis() returned a time less than zero: {}", currentTimeInMillis);
                }
            } else {
                logger.debug("No feed providers. Returning cached time: {}", cachedTime);
            }
        }
        return cachedTime;
    }

    private void createPlotAndAddItToPanel() {
        createPlot();
        assert thePlot != null : "Plot must be created";
        addPlotToPanel();
    }

    private void createPlot() {
        thePlot = PlotViewFactory.createPlot(plotPersistenceHandler.loadPlotSettingsFromPersistance(), getCurrentMCTTime(), this, plotDataAssigner.returnNumberOfSubPlots(), null, plotLabelingAlgorithm, plotDataAssigner.getTimeSystemDefaultChoice());
    }

    private void addPlotToPanel() {
        if (theView != null) {
            remove(theView);
        }
        theView = thePlot.getPlotPanel();
        add(theView);
        refreshPlotPanel();
    }

    private void enforceBackgroundColor(final Color bg) {
        this.setBackground(bg);
        thePlot.getPlotPanel().setBackground(bg);
        ComponentTraverser.traverse(theView, new ComponentTraverser.ComponentProcedure() {

            @Override
            public void run(Component c) {
                if ((PlotConstants.DEFAULT_PLOT_FRAME_BACKGROUND_COLOR).equals(c.getBackground())) {
                    c.setBackground(bg);
                }
            }
        });
    }

    private void refreshPlotPanel() {
        thePlot.refreshDisplay();
        enforceBackgroundColor(plotFrameBackground);
        revalidate();
    }

    public PlotView getPlot() {
        return thePlot;
    }

    public void setPlot(PlotView plot) {
        thePlot = plot;
    }

    private void clearArrayList() {
        canvasContextTitleList.clear();
        panelContextTitleList.clear();
    }

    private void setLabelingContext(AbbreviatingPlotLabelingAlgorithm plotLabelingAlgorithm, NamingContext context) {
        clearArrayList();
        String surroundingName = "";
        if (context != null) {
            if (context.getContextualName() != null) {
                surroundingName = context.getContextualName();
                logger.debug("getPanelTitle surroundingName={}", surroundingName);
                if (surroundingName.isEmpty()) {
                    surroundingName = getManifestedComponent().getDisplayName();
                }
            }
            canvasContextTitleList.add(surroundingName);
        } else {
            surroundingName = getManifestedComponent().getDisplayName();
            panelContextTitleList.add(surroundingName);
        }
        plotLabelingAlgorithm.setPanelOrWindowTitle(surroundingName);
        plotLabelingAlgorithm.setCanvasContextTitleList(canvasContextTitleList);
        plotLabelingAlgorithm.setPanelContextTitleList(panelContextTitleList);
        if (logger.isDebugEnabled()) {
            printTitleArrayLists("*** DEBUG 2 *** panelContextTitleList", panelContextTitleList);
            printTitleArrayLists("*** DEBUG 2 *** canvasContextTitleList", canvasContextTitleList);
        }
    }

    private void printTitleArrayLists(String name, List<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            logger.debug(name + ".get(" + i + ")=" + arrayList.get(i));
        }
    }

    public void addFeedCallback(Runnable r) {
        feedCallbacks.add(r);
    }

    public void removeFeedCallback(Runnable r) {
        feedCallbacks.remove(r);
    }

    public FeedFilterProvider getFilterProvider() {
        return plotDataAssigner.getFilterProvider();
    }

    public FeedFilter getFilter() {
        FeedFilterProvider provider = getFilterProvider();
        Boolean enabled = getPlot().getExtension(PlotConstants.FILTER_ENABLED, Boolean.class);
        String definition = getPlot().getExtension(PlotConstants.FILTER_VALUE, String.class);
        if (provider != null && enabled != null && enabled.booleanValue() && definition != null) {
            try {
                return provider.createFilter(definition);
            } catch (ParseException pe) {
                return null;
            }
        }
        return null;
    }

    private boolean matchFeedProvider(FeedProvider fp, FeedInfoProvider feedInfoProvider, String timeSystem, String feedType) {
        String fpTimeSystem = fp.getTimeService().getTimeSystemId();
        if (!timeSystem.equals(fpTimeSystem) && !TimeService.WILDCARD_SERVICE_ID.equals(fpTimeSystem)) {
            return false;
        }
        if (feedType == null || feedType.isEmpty()) {
            return true;
        }
        FeedInfo feedInfo = feedInfoProvider != null ? feedInfoProvider.getFeedInfo(fp) : null;
        return feedInfo != null && feedType.equals(feedInfo.getTypeId());
    }

    public Map<String, String> getFeedInfoChoices() {
        Map<String, String> choices = new HashMap<String, String>();
        for (FeedInfo feedInfo : plotDataAssigner.getFeedInfoChoices()) {
            choices.put(feedInfo.getTypeId(), feedInfo.getTypeName());
        }
        return choices;
    }
}
