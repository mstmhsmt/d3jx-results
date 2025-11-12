package juicebox.assembly;

import juicebox.HiCGlobals;
import juicebox.gui.SuperAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import juicebox.data.basics.Chromosome;

public class AssemblyFileImporter {

    private SuperAdapter superAdapter = null;

    private String assemblyFilePath;

    private boolean modified = false;

    private List<Scaffold> listOfScaffolds;

    private List<List<Integer>> listOfSuperscaffolds;

    private AssemblyScaffoldHandler assemblyScaffoldHandler;

    public AssemblyFileImporter(String assemblyFilePath, boolean modified) {
        this.assemblyFilePath = assemblyFilePath;
        this.modified = modified;
    }

    public AssemblyFileImporter(SuperAdapter superAdapter) {
        this.superAdapter = superAdapter;
    }

    private int updateAssemblyScale() {
        long totalLength = 0;
        for (Scaffold fragmentProperty : listOfScaffolds) {
            totalLength += fragmentProperty.getLength();
        }
        HiCGlobals.hicMapScale = (int) (1 + totalLength / 2100000000);
        return (int) (totalLength / HiCGlobals.hicMapScale);
    }

    private void setInitialState() {
        long shift = 0;
        for (List<Integer> row : listOfSuperscaffolds) {
            for (Integer entry : row) {
                int fragmentIterator = Math.abs(entry) - 1;
                listOfScaffolds.get(fragmentIterator).setOriginallyInverted(false);
                if (entry < 0) {
                    listOfScaffolds.get(fragmentIterator).setOriginallyInverted(true);
                } else if (entry == 0) {
                    System.err.println("Something is wrong with the input.");
                }
                listOfScaffolds.get(fragmentIterator).setOriginalStart(shift);
                shift += listOfScaffolds.get(fragmentIterator).getLength();
            }
        }
    }

    private void setModifiedInitialState() {
        List<Scaffold> originalScaffolds = AssemblyHeatmapHandler.getSuperAdapter().getAssemblyStateTracker().getInitialAssemblyScaffoldHandler().getListOfScaffolds();
        long modifiedShift = 0;
        int originalScaffoldIterator = 0;
        Scaffold originalScaffold = originalScaffolds.get(originalScaffoldIterator);
        long containingStart = originalScaffold.getOriginalStart();
        long containingEnd = originalScaffold.getOriginalEnd();
        for (Scaffold modifiedScaffold : listOfScaffolds) {
            modifiedScaffold.setOriginallyInverted(originalScaffold.getOriginallyInverted());
            if (!modifiedScaffold.getOriginallyInverted()) {
                modifiedScaffold.setOriginalStart(containingStart);
                containingStart += modifiedScaffold.getLength();
            } else {
                modifiedScaffold.setOriginalStart(containingEnd - modifiedScaffold.getLength());
                containingEnd -= modifiedScaffold.getLength();
            }
            modifiedShift += modifiedScaffold.getLength();
            if (modifiedShift == originalScaffold.getLength()) {
                if (originalScaffoldIterator == originalScaffolds.size() - 1) {
                    if (modifiedScaffold != listOfScaffolds.get(listOfScaffolds.size() - 1)) {
                        System.err.println("Modified assembly incompatible with the original one.");
                    }
                    break;
                }
                originalScaffoldIterator++;
                originalScaffold = originalScaffolds.get(originalScaffoldIterator);
                containingStart = originalScaffold.getOriginalStart();
                containingEnd = originalScaffold.getOriginalEnd();
                modifiedShift = 0;
            }
        }
    }

    public AssemblyScaffoldHandler getAssemblyScaffoldHandler() {
        return assemblyScaffoldHandler;
    }

    public List<String> listOfUnattempted;

    private boolean needsBundling = false;

    private int bundleSize = 0;

    private List<String> rawFileData;

    public AssemblyFileImporter() {
    }

    public void importAssembly() {
        listOfScaffolds = new ArrayList<>();
        listOfSuperscaffolds = new ArrayList<>();
        listOfUnattempted = new ArrayList<>();
        try {
            if (assemblyFilePath != null) {
                rawFileData = readFile(assemblyFilePath);
                parseAssemblyFile();
            }
            if (!modified)
                setInitialState();
            else
                setModifiedInitialState();
        } catch (IOException exception) {
            System.err.println("Error reading files!");
        }
        updateAssemblyScale();
        assemblyScaffoldHandler = new AssemblyScaffoldHandler(listOfScaffolds, listOfSuperscaffolds, listOfUnattempted);
    }

    private void parseAssemblyFileNoBundling() throws IOException {
        try {
            for (String row : rawFileData) {
                if (row.startsWith(">")) {
                    String[] splitRow = row.split(" ");
                    Scaffold scaffold = new Scaffold(splitRow[0].substring(1), Integer.parseInt(splitRow[1]), Integer.parseInt(splitRow[2]));
                    listOfScaffolds.add(scaffold);
                } else {
                    List<Integer> superscaffold = new ArrayList<>();
                    for (String index : row.split(" ")) {
                        superscaffold.add(Integer.parseInt(index));
                    }
                    listOfSuperscaffolds.add(superscaffold);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Errors in format");
        }
    }

    private void parseAssemblyFile() throws IOException {
        if (!needsBundling) {
            parseAssemblyFileNoBundling();
            return;
        }
        try {
            long len = 0;
            int start = 0;
            int prev = 0;
            int i = 0;
            int input_start = 0;
            List<String> name = new ArrayList<>();
            List<Integer> size = new ArrayList<>();
            List<String[]> asm = new ArrayList<>();
            for (String row : rawFileData) {
                String[] splitRow = row.split(" ");
                if (splitRow[0].startsWith(">")) {
                    int clen = Integer.parseInt(splitRow[2]);
                    int id = Integer.parseInt(splitRow[1]);
                    if (clen >= bundleSize && input_start != 0) {
                        input_start = 0;
                    }
                    if (clen < bundleSize && input_start == 0) {
                        input_start = id;
                    }
                    len += clen;
                    name.add(splitRow[0]);
                    size.add(clen);
                } else {
                    asm.add(splitRow);
                    if (splitRow.length != 1) {
                        start = 0;
                        continue;
                    }
                    i = Integer.parseInt(splitRow[0]);
                    if (i <= 0) {
                        start = 0;
                        continue;
                    }
                    if (name.get(i - 1).contains(":::fragment_")) {
                        start = 0;
                        continue;
                    }
                    if (start > 0 && i == prev + 1) {
                        prev = i;
                    } else {
                        start = i;
                        prev = i;
                    }
                }
            }
            if ((bundleSize != 0 && input_start == 0) || (start == i)) {
                System.err.println("Nothing to bundle!");
            }
            if (bundleSize != 0 && input_start > start) {
                start = input_start;
                System.err.println("Warning: cannot bundle everything under " + bundleSize + "bp");
            }
            if (modified && start > 1) {
                List<Scaffold> originalScaffolds = AssemblyHeatmapHandler.getSuperAdapter().getAssemblyStateTracker().getInitialAssemblyScaffoldHandler().getListOfScaffolds();
                while (true) {
                    long tentativeUnbundled = 0;
                    for (int j = 0; j < start - 1; j++) {
                        tentativeUnbundled += size.get(j);
                    }
                    if (tentativeUnbundled < len - originalScaffolds.get(originalScaffolds.size() - 1).getLength()) {
                        start++;
                        continue;
                    }
                    if (tentativeUnbundled > len - originalScaffolds.get(originalScaffolds.size() - 1).getLength()) {
                        System.err.println("Something's wrong!");
                    }
                    break;
                }
            }
            long bundlelen = 0;
            for (i = 1; i < start; i++) {
                Scaffold scaffold = new Scaffold(name.get(i - 1).substring(1), i, size.get(i - 1));
                listOfScaffolds.add(scaffold);
                bundlelen += size.get(i - 1);
            }
            for (i = start; i <= name.size(); i++) {
                listOfUnattempted.add(name.get(i - 1) + " " + i + " " + size.get(i - 1));
            }
            for (String[] asmLine : asm) {
                if (Integer.parseInt(asmLine[0]) == start) {
                    break;
                }
                List<Integer> superscaffold = new ArrayList<>();
                for (String index : asmLine) {
                    superscaffold.add(Integer.parseInt(index));
                }
                listOfSuperscaffolds.add(superscaffold);
            }
            listOfScaffolds.add(new Scaffold("unattempted:::debris", start, len - bundlelen));
            List<Integer> superscaffold = new ArrayList<>();
            superscaffold.add(start);
            listOfSuperscaffolds.add(superscaffold);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Errors in format");
        }
    }

    List<String> readFile(String filePath) throws IOException {
        List<String> fileData = new ArrayList<>();
        File file = new File(filePath);
        if (file.length() > 1000000 && !modified) {
            if (SuperAdapter.showConfirmDialog("The assembly file is large. Do you want to try and bundle small sequences?") == 0) {
                needsBundling = true;
            }
        }
        if (modified) {
            List<Scaffold> listOfScaffolds = AssemblyHeatmapHandler.getSuperAdapter().getAssemblyStateTracker().getInitialAssemblyScaffoldHandler().getListOfScaffolds();
            if (listOfScaffolds.get(listOfScaffolds.size() - 1).getName().contentEquals("unattempted:::debris")) {
                needsBundling = true;
            }
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            fileData.add(scanner.nextLine());
        }
        return fileData;
    }
}
