package com.struct.demo.structurizr;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.SourceCodeComponentFinderStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class StructurizrGenerator {

    public static void generateWorkspaceJson(String outputPath) {
        try {
            // Create a Structurizr workspace
            Workspace workspace = new Workspace("Name", "Description");
            Model model = workspace.getModel();

            // Define basic elements
            Person user = model.addPerson("User", "A user of the system.");
            SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "The system being modeled.");
            Container webApp = softwareSystem.addContainer("Web Application", "Handles user requests.", "Java/Spring Boot");
            Container database = softwareSystem.addContainer("Database", "Stores system data.", "PostgreSQL");

            // Add relationships
            user.uses(webApp, "Uses");
            webApp.uses(database, "Reads from and writes to");

            // Configure component finder
            ComponentFinder componentFinder = new ComponentFinder(
                webApp,
                "com.struct.demo.structurizr", // Your base package
                new SourceCodeComponentFinderStrategy(
                    new File("src/main/java")
                ),
                new SpringComponentFinderStrategy()
            );
            // Discover components
            componentFinder.findComponents();

            // Create views
            ViewSet views = workspace.getViews();
            SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "System Context View.");
            contextView.addAllElements();

            ContainerView containerView = views.createContainerView(softwareSystem, "Containers", "Container View.");
            containerView.addAllElements();

            ComponentView componentView = views.createComponentView(webApp, "Components", "Component View.");
            componentView.addAllElements();

            // Define styles
            Styles styles = views.getConfiguration().getStyles();
            styles.addElementStyle(Tags.PERSON).background("#741eba").color("#ffffff").shape(Shape.Person);
            styles.addElementStyle(Tags.CONTAINER).background("#9a28f8").color("#ffffff");

            // Serialize and write the workspace to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File(outputPath), workspace);

            System.out.println("Workspace exported to JSON successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
