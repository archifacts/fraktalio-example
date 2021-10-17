package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

public final class FraktalioEvent {

	private final Artifact artifact;

	public FraktalioEvent( Artifact artifact) {
		this.artifact = artifact;
	}
	
	public Artifact getArtifact() {
		return artifact;
	}
	
	public String getName() {
		return artifact.getName();
	}
	
	public Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(EventHandlerDescriptor.role())
			.stream()
			.map(ArtifactRelationship::getSource)
			.collect(Collectors.toSet());
	}
	
	public Set<Artifact> getPublishers() {
		return artifact.getIncomingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
