package coordinate_calling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class GZipExample {
	static Path source_path;
	static Path target_path;


    public static void main(String ref_gz_add, String ref_add) {

    	source_path = Paths.get(ref_gz_add);
       target_path = Paths.get(ref_add);

        if (Files.notExists(source_path)) {
            System.err.printf("The path %s doesn't exist!", ref_gz_add);
            return;
        }

        try {

            GZipExample.decompressGzip(source_path, target_path);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void decompressGzip(Path source_path, Path target_path) throws IOException {

        try (GZIPInputStream gis = new GZIPInputStream(
                                      new FileInputStream(source_path.toFile()));
             FileOutputStream fos = new FileOutputStream(target_path.toFile())) {

            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024*1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            
            }}catch (Exception e) {
				e.printStackTrace();

        }

    }

}
