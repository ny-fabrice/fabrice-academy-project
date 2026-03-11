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

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Slide {

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String pretitle;

    @ValueMapValue
    private String subtitle;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private String ctaText;

    @SlingObject
    private ResourceResolver resourceResolver;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPretitle() {
        return pretitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    /**
     * Returns the CTA URL: mapped if internal path, unchanged if external (e.g. http/https).
     */
    public String getCtaUrl() {
        if (ctaLink == null || ctaLink.isEmpty()) {
            return "";
        }
        if (ctaLink.startsWith("http://") || ctaLink.startsWith("https://") || ctaLink.startsWith("#")) {
            return ctaLink;
        }
        if (resourceResolver != null) {
            return resourceResolver.map(ctaLink);
        }
        return ctaLink;
    }

    public String getCtaText() {
        return ctaText;
    }

    /**
     * Resolves the stored DAM/image path to a URL suitable for use in the page.
     */
    public String getImageUrl() {
        if (image == null || image.isEmpty()) {
            return "";
        }
        if (resourceResolver != null) {
            return resourceResolver.map(image);
        }
        return image;
    }

    public boolean isHasCta() {
        return ctaLink != null && !ctaLink.isEmpty() && ctaText != null && !ctaText.isEmpty();
    }
}
