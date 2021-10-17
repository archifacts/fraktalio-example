package org.archifacts.example.fraktalio.asciidoc;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactContainer;
import org.archifacts.example.fraktalio.model.FraktalioApplication;
import org.archifacts.example.fraktalio.model.FraktalioCommand;
import org.archifacts.example.fraktalio.model.FraktalioEvent;
import org.archifacts.example.fraktalio.model.FraktalioModule;
import org.archifacts.example.fraktalio.model.FraktalioQuery;
import org.archifacts.integration.asciidoc.AsciiDocElement;

final class ModuleInteractionMatrix implements AsciiDocElement {

	private final FraktalioApplication application;

	ModuleInteractionMatrix(FraktalioApplication application) {
		this.application = application;
	}

	public String render() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n== Module interactions");
		final List<FraktalioModule> modules = application.getModules().stream().sorted(comparing(FraktalioModule::getName)).collect(Collectors.toList());
		final List<ArtifactContainer> containers = modules.stream().map(FraktalioModule::getArtifactContainer).collect(Collectors.toList());

		String colDef = modules.stream().map(x -> "1").collect(Collectors.joining(","));
		stringBuilder.append("\n[%header,cols=\"1,1,").append(colDef).append("\"]");
		stringBuilder.append("\n|===");
		String headerDef = modules.stream().map(FraktalioModule::getName).collect(Collectors.joining("|"));
		stringBuilder.append("\n|||").append(headerDef);
		for (FraktalioModule module : modules) {
			List<String> moduleEntries = new ArrayList<>();
			// Events
			for (FraktalioEvent publishedEvent : module.getPublishedEvents()) {
				final Set<ArtifactContainer> handlers = publishedEvent.getHandlers().stream()
						.map(Artifact::getContainer)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(candidate -> candidate != module.getArtifactContainer())
						.collect(Collectors.toSet());
				if (!handlers.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append("\nh|").append(publishedEvent.getName());
					for (ArtifactContainer artifactContainer : containers) {
						sb.append("\n^|");
						if (artifactContainer != module.getArtifactContainer()) {
							if (handlers.contains(artifactContainer)) {
								sb.append("[green]#icon:check-square[]#");
							} else {
								sb.append("[red]#icon:minus-square[]#");
							}
						}
					}
					moduleEntries.add(sb.toString());
				}
			}

			// Commands
			for (FraktalioCommand sentCommands : module.getSentCommands()) {
				final Set<ArtifactContainer> handlers = sentCommands.getHandlers().stream()
						.map(Artifact::getContainer)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(candidate -> candidate != module.getArtifactContainer())
						.collect(Collectors.toSet());
				if (!handlers.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append("\nh|").append(sentCommands.getName());
					for (ArtifactContainer artifactContainer : containers) {
						sb.append("\n^|");
						if (artifactContainer != module.getArtifactContainer()) {
							if (handlers.contains(artifactContainer)) {
								sb.append("[green]#icon:check-square[]#");
							} else {
								sb.append("[red]#icon:minus-square[]#");
							}
						}
					}
					moduleEntries.add(sb.toString());
				}
			}

			// Queries
			for (FraktalioQuery queriesQueries : module.getQueriedQueries()) {
				final Set<ArtifactContainer> handlers = queriesQueries.getHandlers().stream()
						.map(Artifact::getContainer)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(candidate -> candidate != module.getArtifactContainer())
						.collect(Collectors.toSet());
				if (!handlers.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append("\nh|").append(queriesQueries.getName());
					for (ArtifactContainer artifactContainer : containers) {
						sb.append("\n^|");
						if (artifactContainer != module.getArtifactContainer()) {
							if (handlers.contains(artifactContainer)) {
								sb.append("[green]#icon:check-square[]#");
							} else {
								sb.append("[red]#icon:minus-square[]#");
							}
						}
					}
					moduleEntries.add(sb.toString());
				}
			}

			if (!moduleEntries.isEmpty()) {
				Collections.sort(moduleEntries);
				stringBuilder.append("\n.").append(String.valueOf(moduleEntries.size())).append("+h|").append(module.getName()).append(moduleEntries.get(0));
				for (int i = 1; i < moduleEntries.size(); i++) {
					stringBuilder.append("\n").append(moduleEntries.get(i));
				}
			}

		}
		stringBuilder.append("\n|===");
		return stringBuilder.toString();

	}

}
