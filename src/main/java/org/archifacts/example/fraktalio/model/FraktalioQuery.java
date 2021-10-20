package org.archifacts.example.fraktalio.model;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.RelationshipDescriptors.QueryQuerierDescriptor;
import static org.archifacts.integration.axon.AxonDescriptors.RelationshipDescriptors.QueryHandlerDescriptor;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;
import org.archifacts.core.model.ArtifactRelationshipRole;

public final class FraktalioQuery extends FraktalioBuildingBlock {

	public FraktalioQuery(final Artifact artifact) {
		super(artifact);
	}

	public Set<Artifact> getQueriers() {
		return getArtifact().getIncomingRelationshipsOfRole(QueryQuerierDescriptor.role())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	@Override
	protected ArtifactRelationshipRole getHandlerRole() {
		return QueryHandlerDescriptor.role();
	}
}
