package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.CommandHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

public final class FraktalioCommand {

	private final Artifact artifact;

	public FraktalioCommand(Artifact artifact) {
		this.artifact = artifact;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public String getName() {
		return artifact.getName();
	}

	public Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(CommandHandlerDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	public Set<Artifact> getSenders() {
		return artifact.getIncomingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
