package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.ArtifactContainer;
import org.archifacts.core.model.ArtifactRelationship;
final class FraktalioModule {

	private final ArtifactContainer artifactContainer;

	public FraktalioModule(ArtifactContainer artifactContainer) {
		this.artifactContainer = artifactContainer;
	}
	
	ArtifactContainer getArtifactContainer() {
		return artifactContainer;
	}
	
	String getName() {
		return artifactContainer.getName();
	}
	
	Set<FraktalioEvent> getPublishedEvents() {
		return artifactContainer.getOutgoingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioEvent::new)
				.collect(Collectors.toSet());
	}
	
	Set<FraktalioCommand> getSentCommands() {
		return artifactContainer.getOutgoingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioCommand::new)
				.collect(Collectors.toSet());
	}
	
	Set<FraktalioQuery> getQueriedQueries() {
		return artifactContainer.getOutgoingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioQuery::new)
				.collect(Collectors.toSet());
	}
}
