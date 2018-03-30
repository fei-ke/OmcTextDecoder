import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * decompiled from csc.apk
 */
public class OmcTextDecoder {
    private final int SALT_LENGTH = 256;
    private final String XML_HEADER = "<?xml";
    private final byte[] salts = new byte[]{(byte) 65, (byte) -59, (byte) 33, (byte) -34, (byte) 107, (byte) 28, (byte) -107, (byte) 55, (byte) 78, (byte) 17, (byte) -81, (byte) 6, (byte) -80, (byte) -121, (byte) -35, (byte) -23, (byte) 72, (byte) 122, (byte) -63, (byte) -43, (byte) 68, (byte) 119, (byte) -78, (byte) -111, (byte) -60, (byte) 31, (byte) 60, (byte) 57, (byte) 92, (byte) -88, (byte) -100, (byte) -69, (byte) -106, (byte) 91, (byte) 69, (byte) 93, (byte) 110, (byte) 23, (byte) 93, (byte) 53, (byte) -44, (byte) -51, (byte) 64, (byte) -80, (byte) 46, (byte) 2, (byte) -4, (byte) 12, (byte) -45, (byte) 80, (byte) -44, (byte) -35, (byte) -111, (byte) -28, (byte) -66, (byte) -116, (byte) 39, (byte) 2, (byte) -27, (byte) -45, (byte) -52, (byte) 125, (byte) 39, (byte) 66, (byte) -90, (byte) 63, (byte) -105, (byte) -67, (byte) 84, (byte) -57, (byte) -4, (byte) -4, (byte) 101, (byte) -90, (byte) 81, (byte) 10, (byte) -33, (byte) 1, (byte) 67, (byte) -57, (byte) -71, (byte) 18, (byte) -74, (byte) 102, (byte) 96, (byte) -89, (byte) 64, (byte) -17, (byte) 54, (byte) -94, (byte) -84, (byte) -66, (byte) 14, (byte) 119, (byte) 121, (byte) 2, (byte) -78, (byte) -79, (byte) 89, (byte) 63, (byte) 93, (byte) 109, (byte) -78, (byte) -51, (byte) 66, (byte) -36, (byte) 32, (byte) 86, (byte) 3, (byte) -58, (byte) -15, (byte) 92, (byte) 58, (byte) 2, (byte) -89, (byte) -80, (byte) -13, (byte) -1, (byte) 122, (byte) -4, (byte) 48, (byte) 63, (byte) -44, (byte) 59, (byte) 100, (byte) -42, (byte) -45, (byte) 59, (byte) -7, (byte) -17, (byte) -54, (byte) 34, (byte) -54, (byte) 71, (byte) -64, (byte) -26, (byte) -87, (byte) -80, (byte) -17, (byte) -44, (byte) -38, (byte) -112, (byte) 70, (byte) 10, (byte) -106, (byte) 95, (byte) -24, (byte) -4, (byte) -118, (byte) 45, (byte) -85, (byte) -13, (byte) 85, (byte) 25, (byte) -102, (byte) -119, (byte) 13, (byte) -37, (byte) 116, (byte) 46, (byte) -69, (byte) 59, (byte) 42, (byte) -90, (byte) -38, (byte) -105, (byte) 101, (byte) -119, (byte) -36, (byte) 97, (byte) -3, (byte) -62, (byte) -91, (byte) -97, (byte) -125, (byte) 17, (byte) 14, (byte) 106, (byte) -72, (byte) -119, (byte) 99, (byte) 111, (byte) 20, (byte) 18, (byte) -27, (byte) 113, (byte) 64, (byte) -24, (byte) 74, (byte) -60, (byte) -100, (byte) 26, (byte) 56, (byte) -44, (byte) -70, (byte) 12, (byte) -51, (byte) -100, (byte) -32, (byte) -11, (byte) 26, (byte) 48, (byte) -117, (byte) 98, (byte) -93, (byte) 51, (byte) -25, (byte) -79, (byte) -31, (byte) 97, (byte) 87, (byte) -105, (byte) -64, (byte) 7, (byte) -13, (byte) -101, (byte) 33, (byte) -122, (byte) 5, (byte) -104, (byte) 89, (byte) -44, (byte) -117, (byte) 63, (byte) -80, (byte) -6, (byte) -71, (byte) -110, (byte) -29, (byte) -105, (byte) 116, (byte) 107, (byte) -93, (byte) 91, (byte) -41, (byte) -13, (byte) 20, (byte) -115, (byte) -78, (byte) 43, (byte) 79, (byte) -122, (byte) 6, (byte) 102, (byte) -32, (byte) 52, (byte) -118, (byte) -51, (byte) 72, (byte) -104, (byte) 41, (byte) -38, (byte) 124, (byte) 72, (byte) -126, (byte) -35};
    private final byte[] shifts;

    public OmcTextDecoder() {
        byte[] bArr = new byte[256];
        bArr[0] = (byte) 1;
        bArr[1] = (byte) 1;
        bArr[3] = (byte) 2;
        bArr[4] = (byte) 2;
        bArr[5] = (byte) 4;
        bArr[6] = (byte) 5;
        bArr[8] = (byte) 4;
        bArr[9] = (byte) 7;
        bArr[10] = (byte) 1;
        bArr[11] = (byte) 6;
        bArr[12] = (byte) 5;
        bArr[13] = (byte) 3;
        bArr[14] = (byte) 3;
        bArr[15] = (byte) 1;
        bArr[16] = (byte) 2;
        bArr[17] = (byte) 5;
        bArr[19] = (byte) 6;
        bArr[20] = (byte) 2;
        bArr[21] = (byte) 2;
        bArr[22] = (byte) 4;
        bArr[23] = (byte) 2;
        bArr[24] = (byte) 2;
        bArr[25] = (byte) 3;
        bArr[27] = (byte) 2;
        bArr[28] = (byte) 1;
        bArr[29] = (byte) 2;
        bArr[30] = (byte) 4;
        bArr[31] = (byte) 3;
        bArr[32] = (byte) 4;
        bArr[36] = (byte) 3;
        bArr[37] = (byte) 5;
        bArr[38] = (byte) 3;
        bArr[39] = (byte) 1;
        bArr[40] = (byte) 6;
        bArr[41] = (byte) 5;
        bArr[42] = (byte) 6;
        bArr[43] = (byte) 1;
        bArr[44] = (byte) 1;
        bArr[45] = (byte) 1;
        bArr[48] = (byte) 3;
        bArr[49] = (byte) 2;
        bArr[50] = (byte) 7;
        bArr[51] = (byte) 7;
        bArr[52] = (byte) 5;
        bArr[53] = (byte) 6;
        bArr[54] = (byte) 7;
        bArr[55] = (byte) 3;
        bArr[56] = (byte) 5;
        bArr[57] = (byte) 1;
        bArr[59] = (byte) 7;
        bArr[60] = (byte) 6;
        bArr[61] = (byte) 3;
        bArr[62] = (byte) 6;
        bArr[63] = (byte) 5;
        bArr[64] = (byte) 4;
        bArr[65] = (byte) 5;
        bArr[66] = (byte) 3;
        bArr[67] = (byte) 5;
        bArr[68] = (byte) 1;
        bArr[69] = (byte) 3;
        bArr[70] = (byte) 3;
        bArr[71] = (byte) 1;
        bArr[72] = (byte) 5;
        bArr[73] = (byte) 4;
        bArr[74] = (byte) 1;
        bArr[77] = (byte) 2;
        bArr[78] = (byte) 6;
        bArr[79] = (byte) 6;
        bArr[80] = (byte) 6;
        bArr[81] = (byte) 6;
        bArr[82] = (byte) 4;
        bArr[84] = (byte) 1;
        bArr[85] = (byte) 1;
        bArr[87] = (byte) 5;
        bArr[88] = (byte) 5;
        bArr[89] = (byte) 4;
        bArr[90] = (byte) 2;
        bArr[91] = (byte) 4;
        bArr[92] = (byte) 6;
        bArr[93] = (byte) 1;
        bArr[94] = (byte) 7;
        bArr[95] = (byte) 1;
        bArr[96] = (byte) 2;
        bArr[97] = (byte) 1;
        bArr[98] = (byte) 1;
        bArr[99] = (byte) 6;
        bArr[100] = (byte) 5;
        bArr[101] = (byte) 4;
        bArr[102] = (byte) 7;
        bArr[103] = (byte) 6;
        bArr[104] = (byte) 5;
        bArr[105] = (byte) 1;
        bArr[106] = (byte) 6;
        bArr[107] = (byte) 7;
        bArr[109] = (byte) 2;
        bArr[110] = (byte) 6;
        bArr[111] = (byte) 3;
        bArr[112] = (byte) 1;
        bArr[113] = (byte) 7;
        bArr[114] = (byte) 1;
        bArr[115] = (byte) 1;
        bArr[116] = (byte) 7;
        bArr[117] = (byte) 4;
        bArr[119] = (byte) 4;
        bArr[120] = (byte) 2;
        bArr[121] = (byte) 5;
        bArr[122] = (byte) 3;
        bArr[123] = (byte) 1;
        bArr[124] = (byte) 1;
        bArr[125] = (byte) 5;
        bArr[126] = (byte) 6;
        bArr[128] = (byte) 3;
        bArr[129] = (byte) 5;
        bArr[130] = (byte) 3;
        bArr[131] = (byte) 6;
        bArr[132] = (byte) 5;
        bArr[133] = (byte) 7;
        bArr[134] = (byte) 2;
        bArr[135] = (byte) 5;
        bArr[136] = (byte) 6;
        bArr[137] = (byte) 6;
        bArr[138] = (byte) 2;
        bArr[139] = (byte) 2;
        bArr[140] = (byte) 3;
        bArr[141] = (byte) 6;
        bArr[143] = (byte) 4;
        bArr[144] = (byte) 3;
        bArr[145] = (byte) 2;
        bArr[147] = (byte) 2;
        bArr[148] = (byte) 2;
        bArr[149] = (byte) 3;
        bArr[150] = (byte) 5;
        bArr[151] = (byte) 3;
        bArr[152] = (byte) 3;
        bArr[153] = (byte) 2;
        bArr[154] = (byte) 5;
        bArr[155] = (byte) 5;
        bArr[156] = (byte) 5;
        bArr[157] = (byte) 1;
        bArr[158] = (byte) 3;
        bArr[159] = (byte) 1;
        bArr[160] = (byte) 1;
        bArr[161] = (byte) 1;
        bArr[162] = (byte) 4;
        bArr[163] = (byte) 5;
        bArr[164] = (byte) 1;
        bArr[165] = (byte) 6;
        bArr[166] = (byte) 2;
        bArr[167] = (byte) 4;
        bArr[168] = (byte) 7;
        bArr[169] = (byte) 1;
        bArr[170] = (byte) 4;
        bArr[171] = (byte) 6;
        bArr[173] = (byte) 6;
        bArr[174] = (byte) 4;
        bArr[175] = (byte) 3;
        bArr[176] = (byte) 2;
        bArr[177] = (byte) 6;
        bArr[178] = (byte) 1;
        bArr[179] = (byte) 6;
        bArr[180] = (byte) 3;
        bArr[181] = (byte) 2;
        bArr[182] = (byte) 1;
        bArr[183] = (byte) 6;
        bArr[184] = (byte) 7;
        bArr[185] = (byte) 3;
        bArr[186] = (byte) 2;
        bArr[187] = (byte) 1;
        bArr[188] = (byte) 1;
        bArr[189] = (byte) 5;
        bArr[190] = (byte) 6;
        bArr[191] = (byte) 7;
        bArr[192] = (byte) 2;
        bArr[193] = (byte) 2;
        bArr[194] = (byte) 2;
        bArr[195] = (byte) 7;
        bArr[196] = (byte) 4;
        bArr[197] = (byte) 6;
        bArr[198] = (byte) 7;
        bArr[199] = (byte) 5;
        bArr[200] = (byte) 3;
        bArr[201] = (byte) 1;
        bArr[202] = (byte) 4;
        bArr[203] = (byte) 2;
        bArr[204] = (byte) 7;
        bArr[205] = (byte) 1;
        bArr[206] = (byte) 6;
        bArr[207] = (byte) 2;
        bArr[208] = (byte) 4;
        bArr[209] = (byte) 1;
        bArr[210] = (byte) 5;
        bArr[211] = (byte) 6;
        bArr[212] = (byte) 5;
        bArr[213] = (byte) 4;
        bArr[214] = (byte) 5;
        bArr[216] = (byte) 1;
        bArr[217] = (byte) 1;
        bArr[218] = (byte) 6;
        bArr[219] = (byte) 3;
        bArr[220] = (byte) 7;
        bArr[221] = (byte) 2;
        bArr[223] = (byte) 2;
        bArr[224] = (byte) 5;
        bArr[226] = (byte) 1;
        bArr[227] = (byte) 3;
        bArr[228] = (byte) 3;
        bArr[229] = (byte) 2;
        bArr[230] = (byte) 6;
        bArr[231] = (byte) 7;
        bArr[232] = (byte) 7;
        bArr[233] = (byte) 2;
        bArr[234] = (byte) 5;
        bArr[235] = (byte) 6;
        bArr[237] = (byte) 4;
        bArr[238] = (byte) 1;
        bArr[239] = (byte) 2;
        bArr[240] = (byte) 5;
        bArr[241] = (byte) 3;
        bArr[242] = (byte) 7;
        bArr[243] = (byte) 6;
        bArr[244] = (byte) 5;
        bArr[245] = (byte) 2;
        bArr[246] = (byte) 5;
        bArr[247] = (byte) 2;
        bArr[249] = (byte) 1;
        bArr[250] = (byte) 3;
        bArr[251] = (byte) 1;
        bArr[252] = (byte) 4;
        bArr[253] = (byte) 3;
        bArr[254] = (byte) 4;
        bArr[255] = (byte) 2;
        this.shifts = bArr;
    }

    private byte[] _decode(byte[] source) {
        byte[] results = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            results[i] = (byte) (((source[i] & 255) << this.shifts[i % 256]) | ((source[i] & 255) >>> (8 - this.shifts[i % 256])));
            results[i] = (byte) (results[i] ^ this.salts[i % 256]);
        }
        return results;
    }

    private byte[] _encode(byte[] source) {
        byte[] results = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            results[i] = (byte) (source[i] ^ this.salts[i % 256]);
            results[i] = (byte) (((results[i] & 255) >> this.shifts[i % 256]) | ((results[i] & 255) << (8 - this.shifts[i % 256])));
        }
        return results;
    }

    private byte[] _decompressGzip(byte[] sourceGz) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(sourceGz);
        GZIPInputStream gis = new GZIPInputStream(bis);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = gis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private byte[] _compressGzip(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos) {
            {
                this.def.setLevel(Deflater.BEST_COMPRESSION);
            }
        };
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    public boolean isXmlEncoded(File featureXmlFile) {
        try {
            BufferedReader ptrRead = new BufferedReader(new FileReader(featureXmlFile));
            String headerStr = ptrRead.readLine();
            if (ptrRead != null) {
                ptrRead.close();
            }
            if (headerStr.contains("<?xml")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public byte[] decode(File fileName) throws IOException {
        return _decompressGzip(_decode(fileToByteArray(fileName)));
    }

    public byte[] encode(File fileName) throws IOException {
        return _encode(_compressGzip(fileToByteArray(fileName)));
    }

    private byte[] fileToByteArray(File fileName) {
        try {
            FileInputStream fi = new FileInputStream(fileName);
            byte[] source = new byte[fi.available()];
            fi.read(source);
            fi.close();
            return source;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

}