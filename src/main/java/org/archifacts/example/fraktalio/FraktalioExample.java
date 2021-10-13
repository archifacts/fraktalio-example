package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.BuildingBlockDescriptors.CommandDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.BuildingBlockDescriptors.EventDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.BuildingBlockDescriptors.QueryDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.ContainerDescriptors.ModuleDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.*;
import static org.archifacts.integration.axon.AxonDescriptors.BuildingBlockDescriptors.*;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.AggregateIdentifiedByDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.AggregateMemberDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.CommandHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EntityIdentifiedByDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventSourcingHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.QueryHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.SagaEventHandlerDescriptor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.archifacts.core.model.Application;
import org.archifacts.integration.plaintext.ApplicationOverview;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public class FraktalioExample {

	private static final String ApplicationPackage = "com.fraktalio";

	public static void main(String[] args) throws IOException {
		new FraktalioExample().generateDocumentation();
	}

	private void generateDocumentation() throws IOException {
		final JavaClasses javaClasses = new ClassFileImporter().importPackages(ApplicationPackage);
		final Application application = initApplication(javaClasses);
		writeApplicationOverviewToFile(application, Paths.get("export", "overview.txt"));
	}

	private Application initApplication(final JavaClasses javaClasses) {
		return Application.builder().addContainerDescriptor(ModuleDescriptor)
				.addBuildingBlockDescriptor(AggregateRootDescriptor)
				.addBuildingBlockDescriptor(EntityDescriptor)
				.addBuildingBlockDescriptor(SagaDescriptor)
				.addBuildingBlockDescriptor(EventDescriptor)
				.addBuildingBlockDescriptor(CommandDescriptor)
				.addBuildingBlockDescriptor(QueryDescriptor)
				.addSourceBasedRelationshipDescriptor(CommandHandlerDescriptor)
				.addSourceBasedRelationshipDescriptor(EventHandlerDescriptor)
				.addSourceBasedRelationshipDescriptor(EventSourcingHandlerDescriptor)
				.addSourceBasedRelationshipDescriptor(AggregateIdentifiedByDescriptor)
				.addSourceBasedRelationshipDescriptor(EntityIdentifiedByDescriptor)
				.addSourceBasedRelationshipDescriptor(SagaEventHandlerDescriptor)
				.addSourceBasedRelationshipDescriptor(AggregateMemberDescriptor)
				.addSourceBasedRelationshipDescriptor(QueryHandlerDescriptor)
				.addTargetBasedRelationshipDescriptor(EventPublisherDescriptor)
				.addTargetBasedRelationshipDescriptor(CommandSenderDescriptor)
				.addTargetBasedRelationshipDescriptor(QueryQuerierDescriptor)
				.buildApplication(javaClasses);

	}

	private void writeApplicationOverviewToFile(final Application application, Path outputFile) throws IOException {
		Files.createDirectories(outputFile.getParent());
		try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
			new ApplicationOverview(application).writeToWriter(writer);
		}
		System.out.println("Application overview written to " + outputFile.toString());
	}

}
