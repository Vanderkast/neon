package com.vanderkast.neon.example;

import com.vanderkast.neon.core.model.Note;
import com.vanderkast.neon.example.controller.HashtagExtractor;
import com.vanderkast.neon.example.controller.StoreImpl;
import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.use_case.CreateTextNote_UseCase;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {
    private static final String EXIT = "exit";

    private final PrintStream out = System.out;
    private final Scanner in = new Scanner(System.in).useDelimiter("\n");


    private final Map<String, Runnable> commands = new HashMap<>(10);
    private final Map<String, String> commandsDescriptions = new HashMap<>(10);

    private final Store store;
    private final CreateTextNote_UseCase createUseCase;

    public Application(Store store, CreateTextNote_UseCase createUseCase) {
        this.store = store;
        this.createUseCase = createUseCase;
    }

    public static void main(String[] args) {
        var store = new StoreImpl();
        var createUseCase = new CreateTextNote_UseCase(store, new HashtagExtractor());

        var application = new Application(store, createUseCase);
        application.setUp();
        application.run();
    }

    public void setUp() {
        var help = "help";
        commandsDescriptions.put(help, "shows available commands");
        commands.put(help, this::help);

        var create = "new";
        commandsDescriptions.put(create, "create new note");
        commands.put(create, this::createNote);

        var showAll = "all";
        commandsDescriptions.put(showAll, "show current network");
        commands.put(showAll, this::showAll);

        // special command handled in run loop
        var exit = "exit";
        commandsDescriptions.put(exit, "exit from program");
    }

    private void help() {
        commandsDescriptions.forEach((com, desc) -> System.out.println(com + " - " + desc));
    }

    private void showAll() {
        var network = store.getAll();
        if (network.isEmpty()) {
            out.println("Network is empty. Fill it with notes!");
            return;
        }
        out.println("q - stop show;\n l - show links of last printed note;\n any other - continue");
        var noteIterator = network.iterator();

        var read = "n";
        var current = noteIterator.next();
        while (!read.equals("q")) {
            if (read.equals("n")) {
                out.println(current.toString());
                continue;
            }
            if(read.equals("l")) {

            }
            if (!noteIterator.hasNext())
                break;
            read = in.next();
        }
    }

    private void show(Note note) {
        out.println(note.toString());
        var links = store.getLinks(note).iterator();
        if (!links.hasNext()) {
            out.println("<<< Not linked >>>");
            return;
        }
        out.println("l - show links; any other - return");

        var read = in.next();
        while (read.equals("l")) {


            read = in.next();
        }
    }
    private void createNote() {
        out.println("Enter a text note. Use #tags");
        var note = in.next();
        createUseCase.create(note);
    }

    public void run() {
        greetings();
        var read = "help";
        do {
            commands.getOrDefault(read, this::help).run();
            out.print("enter next command: ");
            read = in.next();
        } while (!read.equals(EXIT));
    }

    private void greetings() {
        out.println("Welcome to the demo console example of NeoN!");
        out.println("NeoN is a Network of Notes.");
        out.println("\t--------------------\t");
        out.println("Let's start with a list of available commands:");
    }
}
