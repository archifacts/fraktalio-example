package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.ArtifactContainer;
import org.archifacts.core.model.ArtifactRelationship;
public final class FraktalioModule {

	private final ArtifactContainer artifactContainer;

	public FraktalioModule(ArtifactContainer artifactContainer) {
		this.artifactContainer = artifactContainer;
	}
	
	public ArtifactContainer getArtifactContainer() {
		return artifactContainer;
	}
	
	public String getName() {
		return artifactContainer.getName();
	}
	
	public Set<FraktalioEvent> getPublishedEvents() {
		return artifactContainer.getOutgoingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioEvent::new)
				.collect(Collectors.toSet());
	}
	
	public Set<FraktalioCommand> getSentCommands() {
		return artifactContainer.getOutgoingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioCommand::new)
				.collect(Collectors.toSet());
	}
	
	public Set<FraktalioQuery> getQueriedQueries() {
		return artifactContainer.getOutgoingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getTarget)
				.map(FraktalioQuery::new)
				.collect(Collectors.toSet());
	}
}
