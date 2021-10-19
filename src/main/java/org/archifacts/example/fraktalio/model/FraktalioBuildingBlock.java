package org.archifacts.example.fraktalio.model;

import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationship;
import org.archifacts.core.model.ArtifactRelationshipRole;

public abstract class FraktalioBuildingBlock {

	private final Artifact artifact;

	protected FraktalioBuildingBlock(Artifact artifact) {
		this.artifact = artifact;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public String getName() {
		return artifact.getName();
	}

	public Set<Artifact> getHandlers() {
		return artifact.getIncomingRelationshipsOfRole(getHandlerRole())
				.stream()
				.map(ArtifactRelationship::getSource)
				.collect(Collectors.toSet());
	}

	protected abstract ArtifactRelationshipRole getHandlerRole();
}
