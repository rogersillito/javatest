package org.rogersillito.medialib;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DirWalker {
    public static Optional<MediaDirectory> walk(String path) {
        var dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return Optional.empty();
        }
        var subdirectories = new ArrayList<MediaDirectory>();
        var mediaFiles = new ArrayList<MediaFile>();
        for (File directory :
                Stream.of(dir.listFiles()).filter(File::isDirectory).toList()) {
            walk(directory.getPath()).ifPresent(subdirectories::add);
        }
        for (File file :
                Stream.of(dir.listFiles())
                        .filter(File::isFile)
                        //TODO: filter using http://jthink.net/jaudiotagger/javadoc/org/jaudiotagger/audio/AudioFileFilter.html
                        .toList()) {
            System.out.println(file.getPath());
            mediaFiles.add(new MediaFile(file));
        }
        if (mediaFiles.size() > 0 || subdirectories.size() > 0) {
            var thisDirectory = new MediaDirectory();
            thisDirectory.getDirectories().addAll(subdirectories);
            thisDirectory.getFiles().addAll(mediaFiles);
            return Optional.of(thisDirectory);
        }
        return Optional.empty();
    }
}

@Data
class MediaDirectory {
    List<MediaDirectory> directories = new ArrayList<>();
    List<MediaFile> files = new ArrayList<>();
}


@RequiredArgsConstructor
class MediaFile {
    @NonNull File file;
}
