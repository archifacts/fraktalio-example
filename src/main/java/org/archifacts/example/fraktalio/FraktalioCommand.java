package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.CommandSenderDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.CommandHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

final class FraktalioCommand {

	private final Artifact artifact;

	public FraktalioCommand(Artifact artifact) {
		this.artifact = artifact;
	}

	Artifact getArtifact() {
		return artifact;
	}

	String getName() {
		return artifact.getName();
	}

	Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(CommandHandlerDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	Set<Artifact> getSenders() {
		return artifact.getIncomingRelationshipsOfRole(CommandSenderDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
