import java.io.FileInputStream;
import java.io.IOException;

public class WavHeaderDump {
    public static void main(String[] args) {
		if (args.length != 1) {
            System.out.println("Usage: " + WavHeaderDump.class.getSimpleName() + " <input file>");
            System.exit(1);
        }
        String wavFilePath = args[0];

        try (FileInputStream fileInputStream = new FileInputStream(wavFilePath)) {
            byte[] header = new byte[44];  // WAV header is 44 bytes
            int bytesRead = fileInputStream.read(header);

            if (bytesRead == 44) {
                printHeaderFields(header);
            } else {
                System.out.println("Error: Incomplete or invalid WAV file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printHeaderFields(byte[] header) {
        System.out.println("WAV File Header Fields");
        System.out.println("======================");
        System.out.println("ChunkID: " + new String(header, 0, 4));
        System.out.println("ChunkSize: " + byteArrayToInt(header, 4));
        System.out.println("Format: " + new String(header, 8, 4));
        System.out.println("Subchunk1ID: " + new String(header, 12, 4));
        System.out.println("Subchunk1Size: " + byteArrayToInt(header, 16));
        short audioFormat = byteArrayToShort(header, 20);
        String audioFormatName = getAudioFormatName(audioFormat);
        System.out.println("AudioFormat: " + audioFormatName + " (" + audioFormat + ")");
        System.out.println("NumChannels: " + byteArrayToShort(header, 22));
        System.out.println("SampleRate: " + byteArrayToInt(header, 24));
        System.out.println("ByteRate: " + byteArrayToInt(header, 28));
        System.out.println("BlockAlign: " + byteArrayToShort(header, 32));
        System.out.println("BitsPerSample: " + byteArrayToShort(header, 34));
        System.out.println("Subchunk2ID: " + new String(header, 36, 4));
        System.out.println("Subchunk2Size: " + byteArrayToInt(header, 40));
    }

    private static String getAudioFormatName(short audioFormat) {
        switch (audioFormat) {
            case 0x0000:
                return "unknown";
            case 0x0001:
                return "Linear PCM/uncompressed";
            case 0x0002:
                return "Microsoftt ADPCM";
            case 0x0003:
                return "IEEE floating-point";
            case 0x0005:
                return "IBM CVSD";
            case 0x0006:
                return "Microsoft ALAW (8-bit ITU-T G.711BA ALAW)";
            case 0x0007:
                return "Microsoft M-LAW (8-bit ITU-T G.711 M-LAW";
            case 0x0011:
                return "Intel IMA/DVI ADPCM";
            case 0x0016:
                return "ITU G.723 ADPCM";
            case 0x0017:
                return "Dialogic OKI ADPCM";
            case 0x0030:
                return "Dolby AAC";
            case 0x0031:
                return "Microsoft GSM 6.10";
            case 0x0036:
                return "Rockwell ADPCM";
            case 0x0040:
                return "ITU G.721 ADPCM";
            case 0x0042:
                return "Microsoft MSG723";
            case 0x0045:
                return "ITU-T G.726";
            case 0x0064:
                return "APICOM G.726 ADPCM";
            case 0x00101:
                return "IBM M-LAW";
            case 0x00102:
                return "IBM A-LAW";
            case 0x00103:
                return "IBM ADPCM";
            default:
                return "?";
        }
    }

    private static int byteArrayToInt(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) | ((bytes[offset + 1] & 0xFF) << 8) |
               ((bytes[offset + 2] & 0xFF) << 16) | ((bytes[offset + 3] & 0xFF) << 24);
    }

    private static short byteArrayToShort(byte[] bytes, int offset) {
        return (short) ((bytes[offset] & 0xFF) | ((bytes[offset + 1] & 0xFF) << 8));
    }
}
