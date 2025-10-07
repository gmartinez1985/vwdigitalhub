package com.vwdigitalhub.robots.application;

import com.vwdigitalhub.robots.application.adapter.in.cli.ConsoleAdapter;
import com.vwdigitalhub.robots.application.domain.port.in.ExecuteInstructionsUseCase;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ExecuteInstructionsService;

import java.io.IOException;

public final class Main {
    public static void main(String[] args) throws IOException {
        ExecuteInstructionsUseCase useCase = new ExecuteInstructionsService();
        new ConsoleAdapter(useCase).run(System.in, System.out);
    }
}
