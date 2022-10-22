package ua.com.unicatstudio;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, GitAPIException {

        BasicConfigurator.configure();

        if(args.length < 2) {
            System.out.println("args email uri");
            System.exit(1);
        }

        String repoURI = args[1];
        String userEmail = args[0];

        String repoDir = Files.createTempDirectory("githubActivityDraw").toFile().getAbsolutePath();
        System.out.println(repoDir);

        Git myGit = Git.cloneRepository()
                .setURI(repoURI)
                .setDirectory(new File(repoDir))
                .call();

        // Repository repo = myGit.getRepository();

        // Config repoCfg = repo.getConfig();
        // repoCfg.setString("user", null, "email", userEmail);

        // String email = repoCfg.getString("user", null, "email");
        // System.out.println(email);

        String imageFileName = repoDir + "/image.txt";

        Scanner imageScanner = new Scanner(new File(imageFileName));
        StringBuilder newImage = new StringBuilder();
        while( imageScanner.hasNextLine() ) {
            String newLine = imageScanner.nextLine();
             if( imageScanner.hasNextLine() ) {
                newImage.append(newLine).append(System.lineSeparator());
            }
        }
        imageScanner.close();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        newImage.append(formatter.format(new Date())).append(System.lineSeparator());

        FileWriter writer = new FileWriter(imageFileName);
        writer.append(newImage.toString());
        writer.flush();

        // git commit -a -m test2
        myGit.commit()
                .setAuthor("githubActivityDraw", userEmail)
                .setAll(true)
                .setMessage("test")
                .call();

        // git push
        myGit.push().call();

    }
}