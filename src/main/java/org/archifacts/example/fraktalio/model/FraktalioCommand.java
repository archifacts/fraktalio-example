package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.CommandHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;
import org.archifacts.core.model.ArtifactRelationshipRole;

public final class FraktalioCommand extends FraktalioBuildingBlock{


	public FraktalioCommand(final Artifact artifact) {
		super(artifact);
	}

	public Set<Artifact> getSenders() {
		return getArtifact().getIncomingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	@Override
	protected ArtifactRelationshipRole getHandlerRole() {
		return CommandHandlerDescriptor.role();
	}
}
