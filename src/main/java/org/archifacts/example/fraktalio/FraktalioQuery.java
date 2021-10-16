package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.QueryHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

final class FraktalioQuery {

	private final Artifact artifact;

	public FraktalioQuery(Artifact artifact) {
		this.artifact = artifact;
	}

	Artifact getArtifact() {
		return artifact;
	}

	String getName() {
		return artifact.getName();
	}

	Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(QueryHandlerDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	Set<Artifact> getQueriers() {
		return artifact.getIncomingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
