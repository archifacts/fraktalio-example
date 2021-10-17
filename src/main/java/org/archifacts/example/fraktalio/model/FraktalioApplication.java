package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Application;
import org.archifacts.core.model.ArtifactRelationship;
import org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors;

public class FraktalioApplication {
	private final Application application;

	public FraktalioApplication(Application application) {
		this.application = application;
	}
	
	public Set<FraktalioModule> getModules( ) {
		return application.getContainersOfType(FraktalioDescriptors.ContainerDescriptors.ModuleDescriptor.type())
					.stream()
					.map(FraktalioModule::new)
					.collect(Collectors.toSet());
	}
	
	public Set<FraktalioEvent> getPublishedEvents() {
		return application.getRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioEvent::new)
				.collect(Collectors.toSet());
	}
	
	public Set<FraktalioCommand> getSentCommands() {
		return application.getRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioCommand::new)
				.collect(Collectors.toSet());
	}
	
	public Set<FraktalioQuery> getQueriedQueries() {
		return application.getRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioQuery::new)
				.collect(Collectors.toSet());
	}
}
