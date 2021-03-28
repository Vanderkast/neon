package com.vanderkast.neon.example.io;

import com.vanderkast.neon.example.use_case.CreateTextNote_UseCase;

public class CreateNoteCommandHandler {
    private final CreateTextNote_UseCase useCase;

    public CreateNoteCommandHandler(CreateTextNote_UseCase useCase) {
        this.useCase = useCase;
    }
}
