package net.snatchlab.investmentfarm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PackTextures {

  public static void main(String[] args) throws IOException {

    String rawDir = "/home/snatch/my_projects/tinka_gdx/investmentFarm/assets/for_pack/";
    String packedDir = "/home/snatch/my_projects/tinka_gdx/investmentFarm/assets/packed/";
    String packFileName = "all.pack";

    deleteExistingPacks(packedDir);

    Path path = Paths.get(rawDir);

    if (Files.isDirectory(path)) {
      Files.walk(path).forEach(path1 -> {
        if (Files.isDirectory(path1)) {
          packTextures(path1.toString(), packedDir.concat(path1.getFileName().toString()), packFileName);
        }
      });
    } else {
      packTextures(rawDir, packedDir, packFileName);
    }
  }

  private static void deleteExistingPacks(String dir) throws IOException {
    Path path = Paths.get(dir);
    if (Files.isDirectory(path)) {
      Files.walk(path).forEach(path1 -> {
        if (Files.isRegularFile(path1)) {
          try {
            Files.delete(path1);
          } catch (IOException e) {
            throw new RuntimeException("Can't delete file: " + path1, e);
          }
        }
      });
    } else {
      Files.deleteIfExists(path);
    }
  }

  private static void packTextures(String inputDir, String outputDir, String packFileName) {
    //TexturePacker.processIfModified(inputDir, outputDir, packFileName);
    TexturePacker.Settings settings = new TexturePacker.Settings();
    settings.maxHeight = 4096;
    settings.maxWidth = 4096;
//    settings.stripWhitespaceX = true;
//    settings.stripWhitespaceY = true;
    settings.filterMin = Texture.TextureFilter.MipMapLinearLinear;
    settings.filterMag = Texture.TextureFilter.MipMapLinearLinear;
    TexturePacker.process(settings, inputDir, outputDir, packFileName);
  }

}
