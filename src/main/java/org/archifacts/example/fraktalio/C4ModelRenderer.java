package org.archifacts.example.fraktalio;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.archifacts.core.model.BuildingBlock;
import org.archifacts.core.model.MiscArtifact;
import org.archifacts.core.model.ExternalArtifact;
import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactContainer;
import org.archifacts.integration.asciidoc.AsciiDocElement;
import org.archifacts.integration.c4.asciidoc.plantuml.ComponentViewPlantUMLDocElement;
import org.archifacts.integration.c4.asciidoc.plantuml.ContainerViewPlantUMLDocElement;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.ViewSet;

public class C4ModelRenderer implements AsciiDocElement {

	private final FraktalioApplication application;

	private final Map<ArtifactContainer, Container> containerMap = new HashMap<>();
	private final Map<Artifact, Component> componentMap = new HashMap<>();

	private SoftwareSystem softwareSystem;

	C4ModelRenderer(FraktalioApplication application) {
		this.application = application;
	}

	@Override
	public String render() {
		final Workspace c4Workspace = new Workspace("Fraktalio", null);
		softwareSystem = initSoftwareSystem(c4Workspace);
		for (FraktalioEvent event : application.getPublishedEvents()) {
			for (Artifact publisher : event.getPublishers()) {
				final Component publisherComponent = component(publisher);
				for (Artifact handler : event.getHandlers()) {
					final Component handlerComponent = component(handler);
					publisherComponent.uses(handlerComponent, "Event");
				}
			}
		}
		for (FraktalioCommand command : application.getSentCommands()) {
			for (Artifact publisher : command.getSenders()) {
				final Component publisherComponent = component(publisher);
				for (Artifact handler : command.getHandlers()) {
					final Component handlerComponent = component(handler);
					publisherComponent.uses(handlerComponent, "Command");
				}
			}
		}
		for (FraktalioQuery query : application.getQueriedQueries()) {
			for (Artifact publisher : query.getQueriers()) {
				final Component publisherComponent = component(publisher);
				for (Artifact handler : query.getHandlers()) {
					final Component handlerComponent = component(handler);
					publisherComponent.uses(handlerComponent, "Query");
				}
			}
		}
		final ViewSet views = c4Workspace.getViews();
		final ContainerView containerView = initContainerView(softwareSystem, views);
		final StringBuilder asciiDoc = new StringBuilder(new ContainerViewPlantUMLDocElement(containerView).render());
		asciiDoc.append("\n");
		asciiDoc.append(containerMap.values()
				.stream()
				.map(container -> initComponentView(container, views))
				.map(ComponentViewPlantUMLDocElement::new)
				.map(ComponentViewPlantUMLDocElement::render)
				.collect(Collectors.joining("\n")));
		return asciiDoc.toString();
	}

	private SoftwareSystem initSoftwareSystem(final Workspace workspace) {
		final Model model = workspace.getModel();
		model.setImpliedRelationshipsStrategy(new CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy());
		final SoftwareSystem softwareSystem = model.addSoftwareSystem("Fraktalio");
		return softwareSystem;
	}

	private Component component(Artifact artifact) {
		final String technology;
		if (artifact instanceof BuildingBlock buildingBlock) {
			technology = buildingBlock.getType().getName();
		} else if (artifact instanceof MiscArtifact miscArtifact) {
			technology = "Misc";
		} else if (artifact instanceof ExternalArtifact externalArtifact) {
			technology = "External";
		} else {
			throw new IllegalArgumentException("Unexpected type: " + artifact.getClass().getName());
		}
		return componentMap.computeIfAbsent(artifact, key -> container(key.getContainer().orElseThrow(() -> new IllegalStateException(artifact + " does not have a container.")))
				.addComponent(key.getName(), key.getJavaClass().getName(), "", technology));
	}

	private Container container(ArtifactContainer artifactContainer) {
		return containerMap.computeIfAbsent(artifactContainer, key -> softwareSystem.addContainer(key.getName(), "", "Module"));
	}

	private ContainerView initContainerView(final SoftwareSystem softwareSystem, final ViewSet views) {

		final ContainerView containerView = views.createContainerView(softwareSystem, "container-view", "Module overview");
		containerView.addAllContainers();
		containerView.enableAutomaticLayout();
		return containerView;
	}

	private ComponentView initComponentView(final Container container, final ViewSet views) {

		final ComponentView componentView = views.createComponentView(container, container.getName(), null);
		componentView.addAllComponents();
		componentView.addExternalDependencies();
		return componentView;
	}

}
