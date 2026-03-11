/*
 * Copyright 2015 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.codeland.fabrice.core.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroSliderModel {

    private static final String SLIDES_NODE = "slides";

    @ValueMapValue(name = "autoplaySpeed")
    @org.apache.sling.models.annotations.Default(intValues = 5000)
    private int autoplaySpeed;

    @ValueMapValue(name = "showNavigation")
    @org.apache.sling.models.annotations.Default(booleanValues = true)
    private boolean showNavigation;

    @SlingObject
    private Resource currentResource;

    private List<Slide> slides;

    @javax.annotation.PostConstruct
    protected void init() {
        slides = new ArrayList<>();
        Resource slidesResource = currentResource.getChild(SLIDES_NODE);
        if (slidesResource != null) {
            StreamSupport.stream(slidesResource.getChildren().spliterator(), false)
                    .sorted(Comparator.comparing(Resource::getName, this::compareItemNames))
                    .filter(r -> r.getName().startsWith("item"))
                    .map(r -> r.adaptTo(Slide.class))
                    .filter(s -> s != null && s.getTitle() != null)
                    .forEach(slides::add);
        }
    }

    private int compareItemNames(String a, String b) {
        try {
            int na = Integer.parseInt(a.replace("item", ""));
            int nb = Integer.parseInt(b.replace("item", ""));
            return Integer.compare(na, nb);
        } catch (NumberFormatException e) {
            return a.compareTo(b);
        }
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public boolean isHasSlides() {
        return slides != null && !slides.isEmpty();
    }

    public int getAutoplaySpeed() {
        return autoplaySpeed;
    }

    public boolean isShowNavigation() {
        return showNavigation;
    }
}
