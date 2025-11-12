package third_parties.daveKoeller;

import java.util.Comparator;
import com.owncloud.android.datamodel.OCFile;
import java.io.File;
import java.text.Collator;

public class AlphanumComparator implements Comparator<OCFile> {

    private final boolean isDigit(char ch) {
        return ch >= 48 && ch <= 57;
    }

    private final String getChunk(String s, int slength, int marker) {
        StringBuilder chunk = new StringBuilder();
        char c = s.charAt(marker);
        chunk.append(c);
        marker++;
        if (isDigit(c)) {
            while (marker < slength) {
                c = s.charAt(marker);
                if (!isDigit(c))
                    break;
                chunk.append(c);
                marker++;
            }
        } else {
            while (marker < slength) {
                c = s.charAt(marker);
                if (isDigit(c))
                    break;
                chunk.append(c);
                marker++;
            }
        }
        return chunk.toString();
    }

    
public int compare(OCFile o1, OCFile o2) {
        String s1 = (String) o1.getRemotePath().toLowerCase();
        String s2 = (String) o2.getRemotePath().toLowerCase();
        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();
        while (thisMarker < s1Length && thatMarker < s2Length) {
            String thisChunk = getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();
            String thatChunk = getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();
            int result = 0;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                if (result == 0) {
                    for (int i = 0; i < thisChunkLength; i++) {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                Collator collator = Collator.getInstance();
                collator.setStrength(Collator.PRIMARY);
                result = collator.compare(thisChunk, thatChunk);
            }
            if (result != 0)
                return result;
        }
        return s1Length - s2Length;
    }


    public int compare(OCFile o1, OCFile o2) {
        String s1 = o1.getRemotePath().toLowerCase();
        String s2 = o2.getRemotePath().toLowerCase();
        return compare(s1, s2);
    }

    public int compare(File f1, File f2) {
        String s1 = f1.getPath().toLowerCase();
        String s2 = f2.getPath().toLowerCase();
        return compare(s1, s2);
    }

    public int compare(String s1, String s2) {
        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();
        while (thisMarker < s1Length && thatMarker < s2Length) {
            String thisChunk = getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();
            String thatChunk = getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();
            int result = 0;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                if (result == 0) {
                    for (int i = 0; i < thisChunkLength; i++) {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk);
            }
            if (result != 0)
                return result;
        }
        return s1Length - s2Length;
    }
}
