package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.QueryHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;

public final class FraktalioQuery {

	private final Artifact artifact;

	public FraktalioQuery(Artifact artifact) {
		this.artifact = artifact;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public String getName() {
		return artifact.getName();
	}

	public Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(QueryHandlerDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	public Set<Artifact> getQueriers() {
		return artifact.getIncomingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}
}
