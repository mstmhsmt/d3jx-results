package org.rstudio.studio.client.workbench.views.source.editors.text.themes;

import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.rstudio.core.client.ColorUtil.RGBColor;
import org.rstudio.core.client.CommandWithArg;
import org.rstudio.core.client.dom.DomUtils;
import org.rstudio.studio.client.application.Desktop;
import org.rstudio.studio.client.application.events.EventBus;
import org.rstudio.studio.client.workbench.prefs.model.UIPrefs;
import org.rstudio.studio.client.workbench.views.source.editors.text.events.EditorThemeChangedEvent;
import java.util.HashMap;
import com.google.gwt.core.client.JsArray;
import org.rstudio.studio.client.server.ServerError;
import org.rstudio.studio.client.server.ServerRequestCallback;
import org.rstudio.studio.client.workbench.views.source.editors.text.themes.model.ThemeServerOperations;
import java.util.function.Consumer;

@Singleton
public class AceThemes {

    @Inject
    public AceThemes(ThemeServerOperations themeServerOperations, final Provider<UIPrefs> prefs, EventBus events) {
        themeServerOperations_ = themeServerOperations;
        events_ = events;
        prefs_ = prefs;
        prefs.get().theme().bind(new CommandWithArg<AceTheme>() {

            public void execute(AceTheme theme) {
                applyTheme(theme);
            }
        });
    }

    private void applyTheme(Document document, final AceTheme theme) {
        Element oldStyleEl = document.getElementById(linkId_);
        if (oldStyleEl != null)
            oldStyleEl.removeFromParent();
        LinkElement currentStyleEl = document.createLinkElement();
        currentStyleEl.setType("text/css");
        currentStyleEl.setRel("stylesheet");
        currentStyleEl.setId(linkId_);
        currentStyleEl.setHref(theme.getUrl() + "?dark=" + (theme.isDark() ? "1" : "0"));
        document.getBody().appendChild(currentStyleEl);
        if (theme.isDark())
            document.getBody().addClassName("editor_dark");
        else
            document.getBody().removeClassName("editor_dark");
        new Timer() {

            @Override
            public void run() {
                events_.fireEvent(new EditorThemeChangedEvent(theme));
                if (Desktop.isDesktop()) {
                    Element el = Document.get().getElementById("rstudio_container");
                    Style style = DomUtils.getComputedStyles(el);
                    String color = style.getBackgroundColor();
                    RGBColor parsed = RGBColor.fromCss(color);
                    JsArrayInteger colors = JsArrayInteger.createArray(3).cast();
                    colors.set(0, parsed.red());
                    colors.set(1, parsed.green());
                    colors.set(2, parsed.blue());
                    Desktop.getFrame().setBackgroundColor(colors);
                }
            }
        }.schedule(100);
    }

    private void applyTheme(final AceTheme theme) {
        applyTheme(Document.get(), theme);
    }

    public void applyTheme(Document document) {
        applyTheme(document, prefs_.get().theme().getValue());
    }

    final private EventBus events_;

    final private Provider<UIPrefs> prefs_;

    final private String linkId_ = "rstudio-acethemes-linkelement";

    public void getThemes(Consumer<HashMap<String, AceTheme>> themeConsumer) {
        themeServerOperations_.getThemes(new ServerRequestCallback<JsArray<AceTheme>>() {

            @Override
            public void onResponseReceived(JsArray<AceTheme> jsonThemeArray) {
                HashMap<String, AceTheme> themes = new HashMap<>();
                int len = jsonThemeArray.length();
                for (int i = 0; i < len; ++i) {
                    AceTheme theme = jsonThemeArray.get(i);
                    themes.put(theme.getName(), theme);
                }
                themeConsumer.accept(themes);
            }

            @Override
            public void onError(ServerError error) {
                HashMap<String, AceTheme> themes = new HashMap<>();
                AceTheme defaultTheme = AceTheme.createDefault();
                themes.put(defaultTheme.getName(), defaultTheme);
                themeConsumer.accept(themes);
            }
        });
    }

    private ThemeServerOperations themeServerOperations_;
}
