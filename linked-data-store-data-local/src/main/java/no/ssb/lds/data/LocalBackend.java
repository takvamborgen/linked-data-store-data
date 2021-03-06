package no.ssb.lds.data;

import no.ssb.lds.data.common.BinaryBackend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;

public class LocalBackend implements BinaryBackend {

    private final String prefix;

    public LocalBackend(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public SeekableByteChannel read(String path) throws FileNotFoundException {
        File file = new File(prefix + path);
        return new FileInputStream(file).getChannel();
    }

    @Override
    public SeekableByteChannel write(String path) throws IOException {
        File file = new File(prefix + path);
        File dir = file.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("could not create " + dir);
        }
        if (!file.createNewFile()) {
            throw new IOException("file " + file + " already exist");
        }
        return new FileOutputStream(file).getChannel();
    }
}
