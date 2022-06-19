package neko.itskekoffcode.clientapi.utils;

import net.minecraft.client.Minecraft;

import java.io.File;

public class FileManager {

    private final String clientDirectory = Minecraft.getMinecraft().mcDataDir.getPath() + "/HClient";

    public FileManager() {
        final File clientDirectoryFolder = new File(clientDirectory);
        if (!clientDirectoryFolder.exists()) {
            if (clientDirectoryFolder.mkdirs())
                System.out.println("Created client directory...");
        }
    }

    @SuppressWarnings("ALL")
    public void addSubDirectory(String directoryName) {
        final File newDirectory = new File(String.format("%s/%s", clientDirectory, directoryName));
        if (!newDirectory.exists()) newDirectory.mkdirs();
    }

    public String getClientDirectory() {
        return clientDirectory;
    }
}
