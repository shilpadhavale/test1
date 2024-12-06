package com.struct.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.struct.demo.structurizr.StructurizrGenerator;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		// Specify the output path for the workspace JSON
        String outputPath = "/Users/shilpadhavale/test1/demo/documentation/workspace.json";

        // Call the generator
        StructurizrGenerator.generateWorkspaceJson(outputPath);
	}

}
