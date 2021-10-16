package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Application;
import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactContainer;
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
	
	Set<Artifact> getPublishedEventsForModule(ArtifactContainer module) {
		return module.getOutgoingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.collect(Collectors.toSet());
	}
	
	Set<Artifact> getSentCommandsForModule(ArtifactContainer module) {
		return  module.getOutgoingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.collect(Collectors.toSet());
	}
	
	Set<Artifact> getQueriedQueriesForModule(ArtifactContainer module) {
		return module.getOutgoingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.collect(Collectors.toSet());
	}
}
