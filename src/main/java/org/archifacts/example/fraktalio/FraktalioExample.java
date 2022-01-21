package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.BuildingBlockDescriptors.CommandDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.BuildingBlockDescriptors.EventDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.BuildingBlockDescriptors.QueryDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.ContainerDescriptors.ModuleDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.BuildingBlockDescriptors.AggregateRootDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.BuildingBlockDescriptors.EntityDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.BuildingBlockDescriptors.SagaDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.AggregateIdentifiedByDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.AggregateMemberDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.CommandHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EntityIdentifiedByDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventSourcingHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.QueryHandlerDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.SagaEventHandlerDescriptor;
import static org.archifacts.integration.spring.SpringDescriptors.BuildingBlockDescriptors.ConfigurationDescriptor;
import static org.archifacts.integration.spring.SpringDescriptors.BuildingBlockDescriptors.ControllerDescriptor;
import static org.archifacts.integration.spring.SpringDescriptors.BuildingBlockDescriptors.RepositoryDescriptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.archifacts.core.model.Application;
import org.archifacts.example.fraktalio.asciidoc.AsciiDocWriter;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(description = "Generates a AsciiDoc based documentation of the Fraktalio demo application.")
public class FraktalioExample implements Runnable {

	@Parameters(index = "0", description = "The output folder")
	private Path outputFolder;

	private static final String ApplicationPackage = "com.fraktalio";

	public static void main(final String[] args) {
		new CommandLine(new FraktalioExample()).execute(args);
	}

	@Override
	public void run() {
		final JavaClasses javaClasses = new ClassFileImporter().importPackages(ApplicationPackage);
		final Application application = initApplication(javaClasses);
		try {
			Files.createDirectories(outputFolder);
			new AsciiDocWriter().writeAsciidoc(application, outputFolder.resolve("index.adoc"));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Application initApplication(final JavaClasses javaClasses) {
		return Application.builder().descriptor(ModuleDescriptor)
				.descriptor(AggregateRootDescriptor)
				.descriptor(EntityDescriptor)
				.descriptor(SagaDescriptor)
				.descriptor(EventDescriptor)
				.descriptor(CommandDescriptor)
				.descriptor(QueryDescriptor)
				.descriptor(ControllerDescriptor)
				.descriptor(ConfigurationDescriptor)
				.descriptor(RepositoryDescriptor)
				.descriptor(CommandHandlerDescriptor)
				.descriptor(EventHandlerDescriptor)
				.descriptor(EventSourcingHandlerDescriptor)
				.descriptor(AggregateIdentifiedByDescriptor)
				.descriptor(EntityIdentifiedByDescriptor)
				.descriptor(SagaEventHandlerDescriptor)
				.descriptor(AggregateMemberDescriptor)
				.descriptor(QueryHandlerDescriptor)
				.descriptor(EventPublisherDescriptor)
				.descriptor(CommandSenderDescriptor)
				.descriptor(QueryQuerierDescriptor)
				.buildApplication(javaClasses);

	}



}
