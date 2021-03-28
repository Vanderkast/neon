package com.vanderkast.neon.example.io;

import com.vanderkast.neon.example.use_case.CreateTextNote_UseCase;

import java.util.Scanner;

public class CreateNoteCommand implements Command {
    private final CreateTextNote_UseCase useCase;

    public CreateNoteCommand(CreateTextNote_UseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void execute() {
        System.out.print("Enter note text: ");
        var text = new Scanner(System.in).next();

    }
}
