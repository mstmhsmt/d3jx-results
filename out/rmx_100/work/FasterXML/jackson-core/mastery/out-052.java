package com.fasterxml.jackson.core.io;

import java.util.Arrays;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;

final public class JsonStringEncoder {

    private final static char[] HC = CharTypes.copyHexChars();

    private final static byte[] HB = CharTypes.copyHexBytes();

    private final static int SURR1_FIRST = 0xD800;

    private final static int SURR1_LAST = 0xDBFF;

    private final static int SURR2_FIRST = 0xDC00;

    private final static int SURR2_LAST = 0xDFFF;

    private final static int INITIAL_BYTE_BUFFER_SIZE = 200;

    public JsonStringEncoder() {
    }

    static public JsonStringEncoder getInstance() {
        return instance;
    }

    
<<<<<<< commits-rmx_100/FasterXML/jackson-core/7a6d71e2d83951df064805fd121857ddc7fddf7e/JsonStringEncoder-7a2f9f3.java

=======
public char[] quoteAsString(String input) {
        char[] outputBuffer = new char[INITIAL_CHAR_BUFFER_SIZE];
        final int[] escCodes = CharTypes.get7BitOutputEscapes();
        final int escCodeCount = escCodes.length;
        int inPtr = 0;
        final int inputLen = input.length();
        TextBuffer textBuffer = null;
        int outPtr = 0;
        char[] qbuf = null;
        outer: while (inPtr < inputLen) {
            tight_loop: while (true) {
                char c = input.charAt(inPtr);
                if (c < escCodeCount && escCodes[c] != 0) {
                    break tight_loop;
                }
                if (outPtr >= outputBuffer.length) {
                    if (textBuffer == null) {
                        textBuffer = TextBuffer.fromInitial(outputBuffer);
                    }
                    outputBuffer = textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outputBuffer[outPtr++] = c;
                if (++inPtr >= inputLen) {
                    break outer;
                }
            }
            if (qbuf == null) {
                qbuf = _qbuf();
            }
            char d = input.charAt(inPtr++);
            int escCode = escCodes[d];
            int length = (escCode < 0) ? _appendNumeric(d, qbuf) : _appendNamed(escCode, qbuf);
            ;
            if ((outPtr + length) > outputBuffer.length) {
                int first = outputBuffer.length - outPtr;
                if (first > 0) {
                    System.arraycopy(qbuf, 0, outputBuffer, outPtr, first);
                }
                if (textBuffer == null) {
                    textBuffer = TextBuffer.fromInitial(outputBuffer);
                }
                outputBuffer = textBuffer.finishCurrentSegment();
                int second = length - first;
                System.arraycopy(qbuf, first, outputBuffer, 0, second);
                outPtr = second;
            } else {
                System.arraycopy(qbuf, 0, outputBuffer, outPtr, length);
                outPtr += length;
            }
        }
        if (textBuffer == null) {
            return Arrays.copyOfRange(outputBuffer, 0, outPtr);
        }
        textBuffer.setCurrentLength(outPtr);
        return textBuffer.contentsAsArray();
    }
>>>>>>> commits-rmx_100/FasterXML/jackson-core/f21edfc730adbe64292b6d24fbbc133cf91d183d/JsonStringEncoder-87d5c65.java


    public char[] quoteAsCharArray(CharSequence input) {
        char[] outputBuffer = new char[100];
        final int[] escCodes = CharTypes.get7BitOutputEscapes();
        
<<<<<<< commits-rmx_100/FasterXML/jackson-core/7a6d71e2d83951df064805fd121857ddc7fddf7e/JsonStringEncoder-7a2f9f3.java
final int escCodeCount = escCodes.length;
=======
char[] outputBuffer = new char[INITIAL_CHAR_BUFFER_SIZE];
>>>>>>> commits-rmx_100/FasterXML/jackson-core/f21edfc730adbe64292b6d24fbbc133cf91d183d/JsonStringEncoder-87d5c65.java

        int inPtr = 0;
        final int inputLen = input.length();
        TextBuffer textBuffer = null;
        int outPtr = 0;
        char[] qbuf = null;
        outer: while (inPtr < inputLen) {
            tight_loop: while (true) {
                char c = input.charAt(inPtr);
                if (c < escCodeCount && escCodes[c] != 0) {
                    break tight_loop;
                }
                if (outPtr >= outputBuffer.length) {
                    if (textBuffer == null) {
                        textBuffer = TextBuffer.fromInitial(outputBuffer);
                    }
                    outputBuffer = textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outputBuffer[outPtr++] = c;
                if (++inPtr >= inputLen) {
                    break outer;
                }
            }
            if (qbuf == null) {
                qbuf = _qbuf();
            }
            char d = input.charAt(inPtr++);
            int escCode = escCodes[d];
            int length = (escCode < 0) ? _appendNumeric(d, qbuf) : _appendNamed(escCode, qbuf);
            if ((outPtr + length) > outputBuffer.length) {
                int first = outputBuffer.length - outPtr;
                if (first > 0) {
                    System.arraycopy(qbuf, 0, outputBuffer, outPtr, first);
                }
                if (textBuffer == null) {
                    textBuffer = TextBuffer.fromInitial(outputBuffer);
                }
                outputBuffer = textBuffer.finishCurrentSegment();
                int second = length - first;
                System.arraycopy(qbuf, first, outputBuffer, 0, second);
                outPtr = second;
            } else {
                System.arraycopy(qbuf, 0, outputBuffer, outPtr, length);
                outPtr += length;
            }
        }
        if (textBuffer == null) {
            return Arrays.copyOfRange(outputBuffer, 0, outPtr);
        }
        textBuffer.setCurrentLength(outPtr);
        return textBuffer.contentsAsArray();
    }

    public void quoteAsString(CharSequence input, StringBuilder output) {
        final int[] escCodes = CharTypes.get7BitOutputEscapes();
        final int escCodeCount = escCodes.length;
        int inPtr = 0;
        final int inputLen = input.length();
        char[] qbuf = null;
        outer: while (inPtr < inputLen) {
            tight_loop: while (true) {
                char c = input.charAt(inPtr);
                if (c < escCodeCount && escCodes[c] != 0) {
                    break tight_loop;
                }
                output.append(c);
                if (++inPtr >= inputLen) {
                    break outer;
                }
            }
            if (qbuf == null) {
                qbuf = _qbuf();
            }
            char d = input.charAt(inPtr++);
            int escCode = escCodes[d];
            int length = (escCode < 0) ? _appendNumeric(d, qbuf) : _appendNamed(escCode, qbuf);
            output.append(qbuf, 0, length);
        }
    }

    @SuppressWarnings("resource")
    public byte[] quoteAsUTF8(CharSequence text) {
        int inputPtr = 0;
        int inputEnd = text.length();
        int outputPtr = 0;
        byte[] outputBuffer = new byte[INITIAL_BYTE_BUFFER_SIZE];
        ByteArrayBuilder bb = null;
        main: while (inputPtr < inputEnd) {
            final int[] escCodes = CharTypes.get7BitOutputEscapes();
            inner_loop: while (true) {
                int ch = text.charAt(inputPtr);
                if (ch > 0x7F || escCodes[ch] != 0) {
                    break inner_loop;
                }
                if (outputPtr >= outputBuffer.length) {
                    if (bb == null) {
                        bb = ByteArrayBuilder.fromInitial(outputBuffer, outputPtr);
                    }
                    outputBuffer = bb.finishCurrentSegment();
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte) ch;
                if (++inputPtr >= inputEnd) {
                    break main;
                }
            }
            if (bb == null) {
                bb = ByteArrayBuilder.fromInitial(outputBuffer, outputPtr);
            }
            if (outputPtr >= outputBuffer.length) {
                outputBuffer = bb.finishCurrentSegment();
                outputPtr = 0;
            }
            int ch = (int) text.charAt(inputPtr++);
            if (ch <= 0x7F) {
                int escape = escCodes[ch];
                outputPtr = _appendByte(ch, escape, bb, outputPtr);
                outputBuffer = bb.getCurrentSegment();
                continue main;
            }
            if (ch <= 0x7FF) {
                outputBuffer[outputPtr++] = (byte) (0xc0 | (ch >> 6));
                ch = (0x80 | (ch & 0x3f));
            } else {
                if (ch < SURR1_FIRST || ch > SURR2_LAST) {
                    outputBuffer[outputPtr++] = (byte) (0xe0 | (ch >> 12));
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((ch >> 6) & 0x3f));
                    ch = (0x80 | (ch & 0x3f));
                } else {
                    if (ch > SURR1_LAST) {
                        _illegal(ch);
                    }
                    if (inputPtr >= inputEnd) {
                        _illegal(ch);
                    }
                    ch = _convert(ch, text.charAt(inputPtr++));
                    if (ch > 0x10FFFF) {
                        _illegal(ch);
                    }
                    outputBuffer[outputPtr++] = (byte) (0xf0 | (ch >> 18));
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((ch >> 12) & 0x3f));
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((ch >> 6) & 0x3f));
                    ch = (0x80 | (ch & 0x3f));
                }
            }
            if (outputPtr >= outputBuffer.length) {
                outputBuffer = bb.finishCurrentSegment();
                outputPtr = 0;
            }
            outputBuffer[outputPtr++] = (byte) ch;
        }
        if (bb == null) {
            return Arrays.copyOfRange(outputBuffer, 0, outputPtr);
        }
        return bb.completeAndCoalesce(outputPtr);
    }

    @SuppressWarnings("resource")
    public byte[] encodeAsUTF8(CharSequence text) {
        int inputPtr = 0;
        int inputEnd = text.length();
        int outputPtr = 0;
        byte[] outputBuffer = new byte[INITIAL_BYTE_BUFFER_SIZE];
        int outputEnd = outputBuffer.length;
        ByteArrayBuilder bb = null;
        main_loop: while (inputPtr < inputEnd) {
            int c = text.charAt(inputPtr++);
            while (c <= 0x7F) {
                if (outputPtr >= outputEnd) {
                    if (bb == null) {
                        bb = ByteArrayBuilder.fromInitial(outputBuffer, outputPtr);
                    }
                    outputBuffer = bb.finishCurrentSegment();
                    outputEnd = outputBuffer.length;
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte) c;
                if (inputPtr >= inputEnd) {
                    break main_loop;
                }
                c = text.charAt(inputPtr++);
            }
            if (bb == null) {
                bb = ByteArrayBuilder.fromInitial(outputBuffer, outputPtr);
            }
            if (outputPtr >= outputEnd) {
                outputBuffer = bb.finishCurrentSegment();
                outputEnd = outputBuffer.length;
                outputPtr = 0;
            }
            if (c < 0x800) {
                outputBuffer[outputPtr++] = (byte) (0xc0 | (c >> 6));
            } else {
                if (c < SURR1_FIRST || c > SURR2_LAST) {
                    outputBuffer[outputPtr++] = (byte) (0xe0 | (c >> 12));
                    if (outputPtr >= outputEnd) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputEnd = outputBuffer.length;
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((c >> 6) & 0x3f));
                } else {
                    if (c > SURR1_LAST) {
                        _illegal(c);
                    }
                    if (inputPtr >= inputEnd) {
                        _illegal(c);
                    }
                    c = _convert(c, text.charAt(inputPtr++));
                    if (c > 0x10FFFF) {
                        _illegal(c);
                    }
                    outputBuffer[outputPtr++] = (byte) (0xf0 | (c >> 18));
                    if (outputPtr >= outputEnd) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputEnd = outputBuffer.length;
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((c >> 12) & 0x3f));
                    if (outputPtr >= outputEnd) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputEnd = outputBuffer.length;
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte) (0x80 | ((c >> 6) & 0x3f));
                }
            }
            if (outputPtr >= outputEnd) {
                outputBuffer = bb.finishCurrentSegment();
                outputEnd = outputBuffer.length;
                outputPtr = 0;
            }
            outputBuffer[outputPtr++] = (byte) (0x80 | (c & 0x3f));
        }
        if (bb == null) {
            return Arrays.copyOfRange(outputBuffer, 0, outputPtr);
        }
        return bb.completeAndCoalesce(outputPtr);
    }

    private char[] _qbuf() {
        char[] qbuf = new char[6];
        qbuf[0] = '\\';
        qbuf[2] = '0';
        qbuf[3] = '0';
        return qbuf;
    }

    private int _appendNumeric(int value, char[] qbuf) {
        qbuf[1] = 'u';
        qbuf[4] = HC[value >> 4];
        qbuf[5] = HC[value & 0xF];
        return 6;
    }

    private int _appendNamed(int esc, char[] qbuf) {
        qbuf[1] = (char) esc;
        return 2;
    }

    private int _appendByte(int ch, int esc, ByteArrayBuilder bb, int ptr) {
        bb.setCurrentSegmentLength(ptr);
        bb.append('\\');
        if (esc < 0) {
            bb.append('u');
            if (ch > 0xFF) {
                int hi = (ch >> 8);
                bb.append(HB[hi >> 4]);
                bb.append(HB[hi & 0xF]);
                ch &= 0xFF;
            } else {
                bb.append('0');
                bb.append('0');
            }
            bb.append(HB[ch >> 4]);
            bb.append(HB[ch & 0xF]);
        } else {
            bb.append((byte) esc);
        }
        return bb.getCurrentSegmentLength();
    }

    private static int _convert(int p1, int p2) {
        if (p2 < SURR2_FIRST || p2 > SURR2_LAST) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(p1) + ", second 0x" + Integer.toHexString(p2) + "; illegal combination");
        }
        return 0x10000 + ((p1 - SURR1_FIRST) << 10) + (p2 - SURR2_FIRST);
    }

    private static void _illegal(int c) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(c));
    }

    private final static JsonStringEncoder instance = new JsonStringEncoder();

    private final static int INITIAL_CHAR_BUFFER_SIZE = 120;
}
