package com.bridgelabz.watchservice;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class Java8WatchServiceExample
{
    private static final WatchEvent.Kind<?>[] ENTRY_CREATE = null;
    private static final WatchEvent.Modifier ENTRY_DELETE = null;
    private static final WatchEvent.Modifier ENTRY_MODIFY = null;
    private final WatchService watcher;
    private final Map<WatchKey, Path> dirWatchers;
    private String Kind;

    public Java8WatchServiceExample(Path dir) throws IOException
    {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.dirWatchers = new HashMap<WatchKey, Path>();
        scanAndRegisterDirectories(dir);
    }


    private void registerDirWatchers(Path dir) throws IOException
    {
        WatchKey key = dir.register(watcher,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
        dirWatchers.put(key,dir);
    }
    private void scanAndRegisterDirectories(final Path start) throws IOException
    {
        Files.walkFileTree(start,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
            {
                registerDirWatchers(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public void processEvents()
    {
        while (true)
        {
            WatchKey key; //wait for key to be signalled
            try
            {
                key = watcher.take();
            }
            catch (InterruptedException x)
            {
                return;
            }
            Path dir = dirWatchers.get(key);
            if (dir == null)
            {
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents())
            {
                WatchEvent.Kind kind = event.kind();
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                System.out.format("%s: %s\n", event.kind().name(), child); //print out event

                //if directory is created, then register it and its sub-directories
                if (Kind.equals(ENTRY_CREATE))
                {
                    try
                    {
                        if (Files.isDirectory(child)) scanAndRegisterDirectories(child);
                    }
                    catch (IOException x){ }
                }
                else if (Kind.equals(ENTRY_DELETE))
                {
                    if (Files.isDirectory(child)) dirWatchers.remove(key);
                }
            }

            //reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid)
            {
                dirWatchers.remove(key);
                if (dirWatchers.isEmpty())
                {
                    break; //all directories are inaccessible.
                }
            }
        }
    }
}


