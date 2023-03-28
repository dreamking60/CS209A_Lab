import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Readsrc {
    public static void main(String[] args) throws IOException {
        try(FileInputStream fis = new FileInputStream("src.zip")) {
            InputStream is = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(is);

            ZipEntry entry = null;
            int count_io = 0;
            int count_nio = 0;
            ArrayList<String> ioList = new ArrayList<>();
            ArrayList<String> nioList = new ArrayList<>();
            System.out.println("# of .java file in java.io/java.nio: ");
            while((entry = zis.getNextEntry()) != null) {
                String filename = entry.getName();
                if (filename.startsWith("java/io")) {
                    count_io++;
                    ioList.add(filename);
                } else if (filename.startsWith("java/nio")) {
                    count_nio++;
                    nioList.add(filename);
                }
            }
            System.out.printf("There are %d .java file in java.io and java.nio.\n", count_io+count_nio);
            System.out.printf("There are %d .java file in java.io: \n", count_io);
            for(String s : ioList) {
                System.out.print(s+" ");
            }
            System.out.println();
            System.out.printf("There are %d .java file in java.nio: \n", count_nio);
            for(String s : nioList) {
                System.out.print(s+" ");
            }


        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        } catch (IOException e) {
            System.out.println("Fail or Interrupt when I/O");
            e.printStackTrace();
        }

        try{
            File f = new File("rt.jar");
            InputStream is = new BufferedInputStream(new FileInputStream(f));
            JarInputStream jis = new JarInputStream(is);
            JarEntry je = null;
            int count_io = 0;
            int count_nio = 0;
            ArrayList<String> ioList = new ArrayList<>();
            ArrayList<String> nioList = new ArrayList<>();
            while((je = jis.getNextJarEntry()) != null) {
                String filename = je.getName();
                if (filename.startsWith("java/io")) {
                    count_io++;
                    ioList.add(filename);
                } else if (filename.startsWith("java/nio")) {
                    count_nio++;
                    nioList.add(filename);
                }

            }
            System.out.printf("There are %d .class file in java.io and java.nio.\n", count_io+count_nio);
            System.out.printf("There are %d .class file in java.io: \n", count_io);
            for(String s : ioList) {
                System.out.print(s+" ");
            }
            System.out.println();
            System.out.printf("There are %d .java file in java.nio: \n", count_nio);
            for(String s : nioList) {
                System.out.print(s+" ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file isn't exist");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        List<String> zl = Files.lines(Paths.get("src.zip"), StandardCharsets.UTF_8).toList();
//        zl.forEach(System.out::println);

    }
}
