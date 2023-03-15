import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FileTypeParser {
    public static void main(String[] args) {
        try(FileInputStream fis = new FileInputStream(args[0])){
            System.out.println("Filename: " + args[0]);
            byte buffer[] = fis.readNBytes(4);
            System.out.printf("File Header(Hex): [%02x, %02x, %02x, %02x]\n", buffer[0], buffer[1], buffer[2], buffer[3]);
            for(FileHead a: FileHead.values()) {
                if(a.matches(buffer)) {
                    System.out.println("File Type: " + a.name());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The input Pathname has no file");
        } catch (IOException e) {
            System.out.println("Failed or Interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }

    enum FileHead {
        PNG(new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47}),
        ZIPorJAR(new byte[]{(byte) 0x50, (byte) 0x4b, (byte) 0x03, (byte) 0x04}),
        CLASS(new byte[]{(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte)0xbe})
        ;
        private byte[] bytes;
        FileHead(byte[] bytes) {
            this.bytes = bytes;
        }

        public boolean matches(byte[] head) {
            if(head == null || head.length != bytes.length) {
                return false;
            }
            for(int i = 0; i < bytes.length; i++) {
                if(head[i] != bytes[i]) {
                    return false;
                }
            }
            return true;
        }

    }

}
