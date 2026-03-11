package it.codeland.fabrice.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class AcademyLearningDemo {

    @Inject
    @Named("jcr:title")
    private String title;

    @Inject
    private String customDescription;

    @PostConstruct
    protected void init() {
        // TO DO:
        if (customDescription != null && !customDescription.isEmpty()) {
            customDescription = customDescription.toUpperCase();
        } else {
            customDescription = "Not Available";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }
}