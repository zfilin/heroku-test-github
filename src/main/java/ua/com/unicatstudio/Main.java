package ua.com.unicatstudio;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, GitAPIException {

        if(args.length < 1) {
            System.out.println("args uri");
            System.exit(1);
        }

        String repoURI = args[0];

        String repoDir = Files.createTempDirectory("githubActivityDraw").toFile().getAbsolutePath();
        System.out.println(repoDir);

        Git.cloneRepository()
                .setURI(repoURI)
                .setDirectory(new File(repoDir))
                .call();

    }
}