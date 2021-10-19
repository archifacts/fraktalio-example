package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.EventPublisherDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.EventHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;
import org.archifacts.core.model.ArtifactRelationshipRole;

public final class FraktalioEvent extends FraktalioBuildingBlock {

	public FraktalioEvent(Artifact artifact) {
		super(artifact);
	}

	public Set<Artifact> getPublishers() {
		return getArtifact().getIncomingRelationshipsOfRole(EventPublisherDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	@Override
	protected ArtifactRelationshipRole getHandlerRole() {
		return EventHandlerDescriptor.role();
	}
}
