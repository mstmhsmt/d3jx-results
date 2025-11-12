package net.sf.jabref;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import net.sf.jabref.gui.*;
import net.sf.jabref.importer.fetcher.EntryFetcher;
import net.sf.jabref.importer.fetcher.EntryFetchers;
import net.sf.jabref.logic.CustomEntryTypesManager;
import net.sf.jabref.logic.journals.Abbreviations;
import net.sf.jabref.logic.l10n.Localization;
import net.sf.jabref.logic.util.OS;
import net.sf.jabref.migrations.PreferencesMigrations;
import net.sf.jabref.model.database.BibtexDatabase;
import net.sf.jabref.model.entry.BibtexEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;
import net.sf.jabref.exporter.AutoSaveManager;
import net.sf.jabref.exporter.ExportFormats;
import net.sf.jabref.exporter.FileActions;
import net.sf.jabref.exporter.IExportFormat;
import net.sf.jabref.exporter.SaveException;
import net.sf.jabref.exporter.SaveSession;
import net.sf.jabref.importer.*;
import net.sf.jabref.logic.remote.RemotePreferences;
import net.sf.jabref.logic.remote.client.RemoteListenerClient;
import net.sf.jabref.gui.remote.JabRefMessageHandler;
import net.sf.jabref.gui.util.FocusRequester;
import net.sf.jabref.logic.util.io.FileBasedLock;
import net.sf.jabref.logic.util.strings.StringUtil;
import net.sf.jabref.logic.logging.CacheableHandler;
import net.sf.jabref.wizard.auximport.AuxCommandLine;

/**
 * JabRef Main Class - The application gets started here.
 */
public class JabRef {
  private static final Log LOGGER = LogFactory.getLog(JabRef.class);

  public static JabRefFrame jrf;

  private static final int MAX_DIALOG_WARNINGS = 10;

  private JabRefCLI cli;

  public void start(String[] args) {
    JabRefPreferences prefs = JabRefPreferences.getInstance();
    if (prefs.getBoolean(JabRefPreferences.USE_PROXY)) {
      System.setProperty("http.proxyHost", prefs.get(JabRefPreferences.PROXY_HOSTNAME));
      System.setProperty("http.proxyPort", prefs.get(JabRefPreferences.PROXY_PORT));
      if (prefs.get("proxyUsername") != null) {
        System.setProperty("http.proxyUser", prefs.get("proxyUsername"));
        System.setProperty("http.proxyPassword", prefs.get("proxyPassword"));
      }
    } else {
      System.setProperty("java.net.useSystemProxies", "true");
      System.setProperty("proxySet", "true");
    }
    Globals.startBackgroundTasks();
    setupLogHandlerForErrorConsole();
    Globals.prefs = prefs;
    Localization.setLanguage(prefs.get(JabRefPreferences.LANGUAGE));
    Globals.prefs.setLanguageDependentDefaultValues();
    BibtexFields.setNumericFieldsFromPrefs();
    Globals.importFormatReader.resetImportFormats();
    CustomEntryTypesManager.loadCustomEntryTypes(prefs);
    ExportFormats.initAllExports();
    Abbreviations.initializeJournalNames(Globals.prefs);
    RemotePreferences remotePreferences = new RemotePreferences(Globals.prefs);
    if (remotePreferences.useRemoteServer()) {
      Globals.remoteListener.open(new JabRefMessageHandler(this), remotePreferences.getPort());
      if (Globals.remoteListener.isOpen()) {
        Globals.remoteListener.start();
      } else {
        if (RemoteListenerClient.sendToActiveJabRefInstance(args, remotePreferences.getPort())) {
          System.out.println(Localization.lang("Arguments passed on to running JabRef instance. Shutting down."));
          JabRefExecutorService.INSTANCE.shutdownEverything();
          return;
        }
      }
    }
    Globals.NEWLINE = Globals.prefs.get(JabRefPreferences.NEWLINE);
    Vector<ParserResult> loaded = processArguments(args, true);
    if ((loaded == null) || cli.isDisableGui() || cli.isShowVersion()) {
      JabRefExecutorService.INSTANCE.shutdownEverything();
      return;
    }
    SwingUtilities.invokeLater(() -> openWindow(loaded));
  }

  private void setupLogHandlerForErrorConsole() {
    Globals.handler = new CacheableHandler();
    ((Jdk14Logger) LOGGER).getLogger().addHandler(Globals.handler);
  }

  public Vector<ParserResult> processArguments(String[] args, boolean initialStartup) {
    cli = new JabRefCLI(args);
    if (initialStartup && cli.isShowVersion()) {
      cli.displayVersion();
    }
    if (initialStartup && cli.isHelp()) {
      cli.printUsage();
      return null;
    }
    if (cli.isPreferencesReset()) {
      String value = cli.getPreferencesReset();
      if ("all".equals(value.trim())) {
        try {
          System.out.println(Localization.lang("Setting all preferences to default values."));
          Globals.prefs.clear();
        } catch (BackingStoreException e) {
          System.err.println(Localization.lang("Unable to clear preferences."));
          e.printStackTrace();
        }
      } else {
        String[] keys = value.split(",");
        for (String key : keys) {
          if (Globals.prefs.hasKey(key.trim())) {
            System.out.println(Localization.lang("Resetting preference key \'%0\'", key.trim()));
            Globals.prefs.clear(key.trim());
          } else {
            System.out.println(Localization.lang("Unknown preference key \'%0\'", key.trim()));
          }
        }
      }
    }
    if (cli.isPreferencesImport()) {
      try {
        Globals.prefs.importPreferences(cli.getPreferencesImport());
        CustomEntryTypesManager.loadCustomEntryTypes(Globals.prefs);
        ExportFormats.initAllExports();
      } catch (JabRefException ex) {
        LOGGER.error("Cannot import preferences", ex);
      }
    }
    Vector<ParserResult> loaded = new Vector<>();
    Vector<String> toImport = new Vector<>();
    if (!cli.isBlank() && (cli.getLeftOver().length > 0)) {
      for (String aLeftOver : cli.getLeftOver()) {
        boolean bibExtension = aLeftOver.toLowerCase().endsWith("bib");
        ParserResult pr = null;
        if (bibExtension) {
          pr = JabRef.openBibFile(aLeftOver, false);
        }
        if ((pr == null) || (pr == ParserResult.INVALID_FORMAT)) {
          if (initialStartup) {
            toImport.add(aLeftOver);
          } else {
            ParserResult res = JabRef.importToOpenBase(aLeftOver);
            if (res != null) {
              loaded.add(res);
            } else {
              loaded.add(ParserResult.INVALID_FORMAT);
            }
          }
        } else {
          if (pr != ParserResult.FILE_LOCKED) {
            loaded.add(pr);
          }
        }
      }
    }
    if (!cli.isBlank() && cli.isFileImport()) {
      toImport.add(cli.getFileImport());
    }
    for (String filenameString : toImport) {
      ParserResult pr = JabRef.importFile(filenameString);
      if (pr != null) {
        loaded.add(pr);
      }
    }
    if (!cli.isBlank() && cli.isImportToOpenBase()) {
      ParserResult res = JabRef.importToOpenBase(cli.getImportToOpenBase());
      if (res != null) {
        loaded.add(res);
      }
    }
    if (!cli.isBlank() && cli.isFetcherEngine()) {
      ParserResult res = fetch(cli.getFetcherEngine());
      if (res != null) {
        loaded.add(res);
      }
    }
    if (cli.isExportMatches()) {
      if (!loaded.isEmpty()) {
        String[] data = cli.getExportMatches().split(",");
        String searchTerm = data[0].replace("\\$", " ");
        ParserResult pr = loaded.elementAt(loaded.size() - 1);
        BibtexDatabase dataBase = pr.getDatabase();
        SearchManagerNoGUI smng = new SearchManagerNoGUI(searchTerm, dataBase);
        BibtexDatabase newBase = smng.getDBfromMatches();
        if ((newBase != null) && (newBase.getEntryCount() > 0)) {
          String formatName = null;
          IExportFormat format;
          switch (data.length) {
            case 3:
            formatName = data[2];
            break;
            case 2:
            formatName = "tablerefsabsbib";
            break;
            default:
            System.err.println(Localization.lang("Output file missing").concat(". \n \t ").concat("Usage").concat(": ") + JabRefCLI.getExportMatchesSyntax());
            return null;
          }
          format = ExportFormats.getExportFormat(formatName);
          if (format != null) {
            try {
              System.out.println(Localization.lang("Exporting") + ": " + data[1]);
              format.performExport(newBase, pr.getMetaData(), data[1], pr.getEncoding(), null);
            } catch (Exception ex) {
              System.err.println(Localization.lang("Could not export file") + " \'" + data[1] + "\': " + ex.getMessage());
            }
          } else {
            System.err.println(Localization.lang("Unknown export format") + ": " + formatName);
          }
        } else {
          System.err.println(Localization.lang("No search matches."));
        }
      } else {
        System.err.println(Localization.lang("The output option depends on a valid input option."));
      }
    }
    if (cli.isFileExport()) {
      if (!loaded.isEmpty()) {
        String[] data = cli.getFileExport().split(",");
        if (data.length == 1) {
          if (!loaded.isEmpty()) {
            ParserResult pr = loaded.elementAt(loaded.size() - 1);
            if (!pr.isInvalid()) {
              try {
                System.out.println(Localization.lang("Saving") + ": " + data[0]);
                SaveSession session = FileActions.saveDatabase(pr.getDatabase(), pr.getMetaData(), new File(data[0]), Globals.prefs, false, false, Globals.prefs.get(JabRefPreferences.DEFAULT_ENCODING), false);
                if (!session.getWriter().couldEncodeAll()) {
                  System.err.println(Localization.lang("Warning") + ": " + Localization.lang("The chosen encoding \'%0\' could not encode the following characters: ", session.getEncoding()) + session.getWriter().getProblemCharacters());
                }
                session.commit();
              } catch (SaveException ex) {
                System.err.println(Localization.lang("Could not save file.") + "\n" + ex.getLocalizedMessage());
              }
            }
          } else {
            System.err.println(Localization.lang("The output option depends on a valid import option."));
          }
        } else {
          if (data.length == 2) {
            ParserResult pr = loaded.elementAt(loaded.size() - 1);
            File theFile = pr.getFile();
            if (!theFile.isAbsolute()) {
              theFile = theFile.getAbsoluteFile();
            }
            MetaData metaData = pr.getMetaData();
            metaData.setFile(theFile);
            Globals.prefs.fileDirForDatabase = metaData.getFileDirectory(Globals.FILE_FIELD);
            Globals.prefs.databaseFile = metaData.getFile();
            System.out.println(Localization.lang("Exporting") + ": " + data[0]);
            IExportFormat format = ExportFormats.getExportFormat(data[1]);
            if (format != null) {
              try {
                format.performExport(pr.getDatabase(), pr.getMetaData(), data[0], pr.getEncoding(), null);
              } catch (Exception ex) {
                System.err.println(Localization.lang("Could not export file") + " \'" + data[0] + "\': " + ex.getMessage());
              }
            } else {
              System.err.println(Localization.lang("Unknown export format") + ": " + data[1]);
            }
          }
        }
      } else {
        System.err.println(Localization.lang("The output option depends on a valid import option."));
      }
    }
    LOGGER.debug("Finished export");
    if (cli.isPreferencesExport()) {
      try {
        Globals.prefs.exportPreferences(cli.getPreferencesExport());
      } catch (JabRefException ex) {
        LOGGER.error("Cannot export preferences", ex);
      }
    }
    if (!cli.isBlank() && cli.isAuxImport()) {
      boolean usageMsg = false;
      if (!loaded.isEmpty()) {
        String[] data = cli.getAuxImport().split(",");
        if (data.length == 2) {
          ParserResult pr = loaded.firstElement();
          AuxCommandLine acl = new AuxCommandLine(data[0], pr.getDatabase());
          BibtexDatabase newBase = acl.perform();
          boolean notSavedMsg = false;
          if (newBase != null) {
            if (newBase.getEntryCount() > 0) {
              String subName = StringUtil.getCorrectFileName(data[1], "bib");
              try {
                System.out.println(Localization.lang("Saving") + ": " + subName);
                SaveSession session = FileActions.saveDatabase(newBase, new MetaData(), new File(subName), Globals.prefs, false, false, Globals.prefs.get(JabRefPreferences.DEFAULT_ENCODING), false);
                if (!session.getWriter().couldEncodeAll()) {
                  System.err.println(Localization.lang("Warning") + ": " + Localization.lang("The chosen encoding \'%0\' could not encode the following characters: ", session.getEncoding()) + session.getWriter().getProblemCharacters());
                }
                session.commit();
              } catch (SaveException ex) {
                System.err.println(Localization.lang("Could not save file.") + "\n" + ex.getLocalizedMessage());
              }
              notSavedMsg = true;
            }
          }
          if (!notSavedMsg) {
            System.out.println(Localization.lang("no database generated"));
          }
        } else {
          usageMsg = true;
        }
      } else {
        usageMsg = true;
      }
      if (usageMsg) {
        System.out.println(Localization.lang("no base-BibTeX-file specified") + "!");
        System.out.println(Localization.lang("usage") + " :");
        System.out.println("jabref --aux infile[.aux],outfile[.bib] base-BibTeX-file");
      }
    }
    return loaded;
  }

  /**
     * Run an entry fetcher from the command line.
     *
     * Note that this only works headlessly if the EntryFetcher does not show any GUI.
     *
     * @param fetchCommand A string containing both the fetcher to use (id of EntryFetcherExtension minus Fetcher) and
     *            the search query, separated by a :
     * @return A parser result containing the entries fetched or null if an error occurred.
     */
  private ParserResult fetch(String fetchCommand) {
    if ((fetchCommand == null) || !fetchCommand.contains(":") || (fetchCommand.split(":").length != 2)) {
      System.out.println(Localization.lang("Expected syntax for --fetch=\'<name of fetcher>:<query>\'"));
      System.out.println(Localization.lang("The following fetchers are available:"));
      return null;
    }
    String[] split = fetchCommand.split(":");
    String engine = split[0];
    String query = split[1];
    EntryFetcher fetcher = null;
    for (EntryFetcher e : EntryFetchers.INSTANCE.getEntryFetchers()) {
      if (engine.toLowerCase().equals(e.getClass().getSimpleName().replaceAll("Fetcher", "").toLowerCase())) {
        fetcher = e;
      }
    }
    if (fetcher == null) {
      System.out.println(Localization.lang("Could not find fetcher \'%0\'", engine));
      System.out.println(Localization.lang("The following fetchers are available:"));
      for (EntryFetcher e : EntryFetchers.INSTANCE.getEntryFetchers()) {
        System.out.println("  " + e.getClass().getSimpleName().replaceAll("Fetcher", "").toLowerCase());
      }
      return null;
    }
    System.out.println(Localization.lang("Running Query \'%0\' with fetcher \'%1\'.", query, engine) + " " + Localization.lang("Please wait..."));
    Collection<BibtexEntry> result = new ImportInspectionCommandLine().query(query, fetcher);
    if ((result == null) || result.isEmpty()) {
      System.out.println(Localization.lang("Query \'%0\' with fetcher \'%1\' did not return any results.", query, engine));
      return null;
    }
    return new ParserResult(result);
  }

  private void setLookAndFeel() {
    try {
      String lookFeel;
      String systemLnF = UIManager.getSystemLookAndFeelClassName();
      if (Globals.prefs.getBoolean(JabRefPreferences.USE_DEFAULT_LOOK_AND_FEEL)) {
        lookFeel = systemLnF;
      } else {
        lookFeel = Globals.prefs.get(JabRefPreferences.WIN_LOOK_AND_FEEL);
      }
      if ("javax.swing.plaf.metal.MetalLookAndFeel".equals(lookFeel)) {
        Plastic3DLookAndFeel lnf = new Plastic3DLookAndFeel();
        Plastic3DLookAndFeel.setCurrentTheme(new SkyBluer());
        com.jgoodies.looks.Options.setPopupDropShadowEnabled(true);
        UIManager.setLookAndFeel(lnf);
      } else {
        try {
          UIManager.setLookAndFeel(lookFeel);
        } catch (Exception e) {
          UIManager.setLookAndFeel(systemLnF);
          Globals.prefs.put(JabRefPreferences.WIN_LOOK_AND_FEEL, systemLnF);
          JOptionPane.showMessageDialog(JabRef.jrf, Localization.lang("Unable to find the requested Look & Feel and thus the default one is used."), Localization.lang("Warning"), JOptionPane.WARNING_MESSAGE);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    boolean overrideDefaultFonts = Globals.prefs.getBoolean(JabRefPreferences.OVERRIDE_DEFAULT_FONTS);
    if (overrideDefaultFonts) {
      int fontSize = Globals.prefs.getInt(JabRefPreferences.MENU_FONT_SIZE);
      UIDefaults defaults = UIManager.getDefaults();
      Enumeration<Object> keys = defaults.keys();
      Double zoomLevel = null;
      while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        if ((key instanceof String) && ((String) key).endsWith(".font")) {
          FontUIResource font = (FontUIResource) UIManager.get(key);
          if (zoomLevel == null) {
            zoomLevel = (double) fontSize / (double) font.getSize();
          }
          font = new FontUIResource(font.getName(), font.getStyle(), fontSize);
          defaults.put(key, font);
        }
      }
      if (zoomLevel != null) {
        GUIGlobals.zoomLevel = zoomLevel;
      }
    }
  }

  private void openWindow(Vector<ParserResult> loaded) {
    PreferencesMigrations.replaceAbstractField();
    PreferencesMigrations.upgradeSortOrder();
    PreferencesMigrations.upgradeFaultyEncodingStrings();
    Globals.prefs.updateExternalFileTypes();
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("swing.aatext", "true");
    System.setProperty("awt.useSystemAAFontSettings", "lcd");
    try {
      setLookAndFeel();
    } catch (Throwable e) {
      e.printStackTrace();
    }
    if (!cli.isBlank() && Globals.prefs.getBoolean(JabRefPreferences.OPEN_LAST_EDITED) && (Globals.prefs.get(JabRefPreferences.LAST_EDITED) != null)) {
      String[] names = Globals.prefs.getStringArray(JabRefPreferences.LAST_EDITED);
      lastEdLoop:
      for (String name : names) {
        File fileToOpen = new File(name);
        for (int j = 0; j < loaded.size(); j++) {
          ParserResult pr = loaded.elementAt(j);
          if ((pr.getFile() != null) && pr.getFile().equals(fileToOpen)) {
            continue lastEdLoop;
          }
        }
        if (fileToOpen.exists()) {
          ParserResult pr = JabRef.openBibFile(name, false);
          if (pr != null) {
            if (pr == ParserResult.INVALID_FORMAT) {
              System.out.println(Localization.lang("Error opening file") + " \'" + fileToOpen.getPath() + "\'");
            } else {
              if (pr != ParserResult.FILE_LOCKED) {
                loaded.add(pr);
              }
            }
          }
        }
      }
    }
    GUIGlobals.init();
    GUIGlobals.CURRENTFONT = new Font(Globals.prefs.get(JabRefPreferences.FONT_FAMILY), Globals.prefs.getInt(JabRefPreferences.FONT_STYLE), Globals.prefs.getInt(JabRefPreferences.FONT_SIZE));
    LOGGER.debug("Initializing frame");
    JabRef.jrf = new JabRefFrame(this);
    boolean first = true;
    List<File> postponed = new ArrayList<>();
    List<ParserResult> failed = new ArrayList<>();
    List<ParserResult> toOpenTab = new ArrayList<>();
    if (!loaded.isEmpty()) {
      for (Iterator<ParserResult> i = loaded.iterator(); i.hasNext(); ) {
        ParserResult pr = i.next();
        if (pr.isInvalid()) {
          failed.add(pr);
          i.remove();
        } else {
          if (!pr.isPostponedAutosaveFound()) {
            if (pr.toOpenTab()) {
              toOpenTab.add(pr);
            } else {
              JabRef.jrf.addParserResult(pr, first);
              first = false;
            }
          } else {
            i.remove();
            postponed.add(pr.getFile());
          }
        }
      }
    }
    for (ParserResult pr : toOpenTab) {
      JabRef.jrf.addParserResult(pr, first);
      first = false;
    }
    if (cli.isLoadSession()) {
      JabRef.jrf.loadSessionAction.actionPerformed(new java.awt.event.ActionEvent(JabRef.jrf, 0, ""));
    }
    if (Globals.prefs.getBoolean(JabRefPreferences.AUTO_SAVE)) {
      Globals.startAutoSaveManager(JabRef.jrf);
    }
    if (Globals.prefs.getBoolean(JabRefPreferences.WINDOW_MAXIMISED)) {
      JabRef.jrf.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    JabRef.jrf.setVisible(true);
    if (Globals.prefs.getBoolean(JabRefPreferences.WINDOW_MAXIMISED)) {
      JabRef.jrf.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    for (ParserResult pr : failed) {
      String message = "<html>" + Localization.lang("Error opening file \'%0\'.", pr.getFile().getName()) + "<p>" + pr.getErrorMessage() + "</html>";
      JOptionPane.showMessageDialog(JabRef.jrf, message, Localization.lang("Error opening file"), JOptionPane.ERROR_MESSAGE);
    }
    for (int i = 0; i < loaded.size(); i++) {
      ParserResult pr = loaded.elementAt(i);
      if (Globals.prefs.getBoolean(JabRefPreferences.DISPLAY_KEY_WARNING_DIALOG_AT_STARTUP) && pr.hasWarnings()) {
        String[] wrns = pr.warnings();
        StringBuilder wrn = new StringBuilder();
        for (int j = 0; j < Math.min(JabRef.MAX_DIALOG_WARNINGS, wrns.length); j++) {
          wrn.append(j + 1).append(". ").append(wrns[j]).append("\n");
        }
        if (wrns.length > JabRef.MAX_DIALOG_WARNINGS) {
          wrn.append("... ");
          wrn.append(Localization.lang("%0 warnings", String.valueOf(wrns.length)));
        } else {
          if (wrn.length() > 0) {
            wrn.deleteCharAt(wrn.length() - 1);
          }
        }
        JabRef.jrf.showBasePanelAt(i);
        JOptionPane.showMessageDialog(JabRef.jrf, wrn.toString(), Localization.lang("Warnings") + " (" + pr.getFile().getName() + ")", JOptionPane.WARNING_MESSAGE);
      }
    }
    for (int i = 0; (i < loaded.size()) && (i < JabRef.jrf.getBasePanelCount()); i++) {
      ParserResult pr = loaded.elementAt(i);
      BasePanel panel = JabRef.jrf.getBasePanelAt(i);
      OpenDatabaseAction.performPostOpenActions(panel, pr, true);
    }
    LOGGER.debug("Finished adding panels");
    if (!postponed.isEmpty()) {
      AutosaveStartupPrompter asp = new AutosaveStartupPrompter(JabRef.jrf, postponed);
      SwingUtilities.invokeLater(asp);
    }
    if (!loaded.isEmpty()) {
      JabRef.jrf.tabbedPane.setSelectedIndex(0);
      new FocusRequester(((BasePanel) JabRef.jrf.tabbedPane.getComponentAt(0)).mainTable);
    }
  }

  public static ParserResult openBibFile(String name, boolean ignoreAutosave) {
    LOGGER.info("Opening: " + name);
    File file = new File(name);
    if (!file.exists()) {
      ParserResult pr = new ParserResult(null, null, null);
      pr.setFile(file);
      pr.setInvalid(true);
      System.err.println(Localization.lang("Error") + ": " + Localization.lang("File not found"));
      return pr;
    }
    try {
      if (!ignoreAutosave) {
        boolean autoSaveFound = AutoSaveManager.newerAutoSaveExists(file);
        if (autoSaveFound) {
          ParserResult postp = new ParserResult(null, null, null);
          postp.setPostponedAutosaveFound(true);
          postp.setFile(file);
          return postp;
        }
      }
      if (!FileBasedLock.waitForFileLock(file, 10)) {
        System.out.println(Localization.lang("Error opening file") + " \'" + name + "\'. " + "File is locked by another JabRef instance.");
        return ParserResult.FILE_LOCKED;
      }
      String encoding = Globals.prefs.get(JabRefPreferences.DEFAULT_ENCODING);
      ParserResult pr = OpenDatabaseAction.loadDatabase(file, encoding);
      if (pr == null) {
        pr = new ParserResult(null, null, null);
        pr.setFile(file);
        pr.setInvalid(true);
        return pr;
      }
      pr.setFile(file);
      if (pr.hasWarnings()) {
        String[] warn = pr.warnings();
        for (String aWarn : warn) {
          LOGGER.warn(aWarn);
        }
      }
      return pr;
    } catch (Throwable ex) {
      ParserResult pr = new ParserResult(null, null, null);
      pr.setFile(file);
      pr.setInvalid(true);
      pr.setErrorMessage(ex.getMessage());
      ex.printStackTrace();
      return pr;
    }
  }

  private static ParserResult importFile(String argument) {
    String[] data = argument.split(",");
    try {
      if ((data.length > 1) && !"*".equals(data[1])) {
        System.out.println(Localization.lang("Importing") + ": " + data[0]);
        try {
          List<BibtexEntry> entries;
          if (OS.WINDOWS) {
            entries = Globals.importFormatReader.importFromFile(data[1], data[0], JabRef.jrf);
          } else {
            entries = Globals.importFormatReader.importFromFile(data[1], data[0].replaceAll("~", System.getProperty("user.home")), JabRef.jrf);
          }
          return new ParserResult(entries);
        } catch (IllegalArgumentException ex) {
          System.err.println(Localization.lang("Unknown import format") + ": " + data[1]);
          return null;
        }
      } else {
        System.out.println(Localization.lang("Importing in unknown format") + ": " + data[0]);
        ImportFormatReader.UnknownFormatImport importResult;
        if (OS.WINDOWS) {
          importResult = Globals.importFormatReader.importUnknownFormat(data[0]);
        } else {
          importResult = Globals.importFormatReader.importUnknownFormat(data[0].replaceAll("~", System.getProperty("user.home")));
        }
        if (importResult != null) {
          System.out.println(Localization.lang("Format used") + ": " + importResult.format);
          return importResult.parserResult;
        } else {
          System.out.println(Localization.lang("Could not find a suitable import format."));
        }
      }
    } catch (IOException ex) {
      System.err.println(Localization.lang("Error opening file") + " \'" + data[0] + "\': " + ex.getLocalizedMessage());
    }
    return null;
  }

  /**
     * Will open a file (like importFile), but will also request JabRef to focus on this database
     *
     * @param argument See importFile.
     * @return ParserResult with setToOpenTab(true)
     */
  private static ParserResult importToOpenBase(String argument) {
    ParserResult result = JabRef.importFile(argument);
    if (result != null) {
      result.setToOpenTab(true);
    }
    return result;
  }
}