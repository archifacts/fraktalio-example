package org.archifacts.example.fraktalio.asciidoc;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactContainer;
import org.archifacts.example.fraktalio.model.FraktalioApplication;
import org.archifacts.example.fraktalio.model.FraktalioBuildingBlock;
import org.archifacts.example.fraktalio.model.FraktalioModule;
import org.archifacts.integration.asciidoc.AsciiDocElement;

final class ModuleInteractionMatrix implements AsciiDocElement {

	private final FraktalioApplication application;

	ModuleInteractionMatrix(final FraktalioApplication application) {
		this.application = application;
	}

	@Override
	public String render() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n== Module interactions");
		stringBuilder.append("\n\nThe first column and the first row represent the system's modules. The second column lists all the events, commands and queries which are used across modules.");
		stringBuilder.append("\n\n[green]#icon:check-square[]# is read as: The building block (event, command or query) is published, sent or queried by the row module on the left and handled by the column module." );
		stringBuilder.append("\n\n[red]#icon:minus-square[]# is read as: The building block (event, command or query) is published, sent or queried by the row module on the left and *not* handled by the column module." );
		stringBuilder.append("\n\nThe combination of the very same module is left out as this view focusses on interactions between modules.\n" );
		final List<FraktalioModule> modules = application.getModules().stream().sorted(comparing(FraktalioModule::getName)).collect(Collectors.toList());
		final List<ArtifactContainer> containers = modules.stream().map(FraktalioModule::getArtifactContainer).collect(Collectors.toList());

		final String colDef = modules.stream().map(x -> "1").collect(Collectors.joining(","));
		stringBuilder.append("\n[%header,cols=\"1,1,").append(colDef).append("\"]");
		stringBuilder.append("\n|===");
		final String headerDef = modules.stream().map(FraktalioModule::getName).collect(Collectors.joining("|"));
		stringBuilder.append("\n|||").append(headerDef);
		for (final FraktalioModule module : modules) {
			final List<String> moduleEntries = new ArrayList<>();
			final ModuleEntryBuilder moduleEntryBuilder = new ModuleEntryBuilder(containers, module, moduleEntries::add);
			moduleEntryBuilder.build(FraktalioModule::getPublishedEvents);
			moduleEntryBuilder.build(FraktalioModule::getSentCommands);
			moduleEntryBuilder.build(FraktalioModule::getQueriedQueries);

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

	private static class ModuleEntryBuilder {
		private final List<ArtifactContainer> containers;
		private final FraktalioModule module;
		private final Consumer<String> moduleEntryAcceptor;

		private ModuleEntryBuilder(final List<ArtifactContainer> containers, final FraktalioModule module, final Consumer<String> moduleEntryAcceptor) {
			this.containers = containers;
			this.module = module;
			this.moduleEntryAcceptor = moduleEntryAcceptor;
		}

		void build(final Function<FraktalioModule, Set<? extends FraktalioBuildingBlock>> buildingBlockProvider) {
			for (final FraktalioBuildingBlock buildingBlock : buildingBlockProvider.apply(module)) {
				final Set<ArtifactContainer> handlers = buildingBlock.getHandlers().stream()
						.map(Artifact::getContainer)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(candidate -> candidate != module.getArtifactContainer())
						.collect(Collectors.toSet());
				if (!handlers.isEmpty()) {
					final StringBuilder sb = new StringBuilder();
					sb.append("\nh|").append(buildingBlock.getName());
					for (final ArtifactContainer artifactContainer : containers) {
						sb.append("\n^|");
						if (artifactContainer != module.getArtifactContainer()) {
							if (handlers.contains(artifactContainer)) {
								sb.append("[green]#icon:check-square[]#");
							} else {
								sb.append("[red]#icon:minus-square[]#");
							}
						}
					}
					moduleEntryAcceptor.accept(sb.toString());
				}
			}

		}

	}
}
