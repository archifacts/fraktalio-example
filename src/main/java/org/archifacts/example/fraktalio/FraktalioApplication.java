package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Application;
import org.archifacts.core.model.ArtifactRelationship;

class FraktalioApplication {
	private final Application application;

	FraktalioApplication(Application application) {
		this.application = application;
	}
	
	Set<FraktalioModule> getModules( ) {
		return application.getContainersOfType(FraktalioDescriptors.ContainerDescriptors.ModuleDescriptor.type())
					.stream()
					.map(FraktalioModule::new)
					.collect(Collectors.toSet());
	}
	
	Set<FraktalioEvent> getPublishedEvents() {
		return application.getRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioEvent::new)
				.collect(Collectors.toSet());
	}
	
	Set<FraktalioCommand> getSentCommands() {
		return application.getRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioCommand::new)
				.collect(Collectors.toSet());
	}
	
	Set<FraktalioQuery> getQueriedQueries() {
		return application.getRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioQuery::new)
				.collect(Collectors.toSet());
	}
}
