package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

final class FraktalioEvent {

	private final Artifact artifact;

	public FraktalioEvent( Artifact artifact) {
		this.artifact = artifact;
	}
	
	Artifact getArtifact() {
		return artifact;
	}
	
	String getName() {
		return artifact.getName();
	}
	
	Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(EventHandlerDescriptor.role())
			.stream()
			.map(ArtifactRelationship::getSource)
			.collect(Collectors.toSet());
	}
	
	Set<Artifact> getPublishers() {
		return artifact.getIncomingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
