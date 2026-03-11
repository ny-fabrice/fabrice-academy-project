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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = Resource.class,
    resourceType = "fabrice/components/random-quote",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class RandomQuoteModel {

    private static final String QUOTES_NODE = "quotes";

    @ValueMapValue(name = "title")
    private String title;

    @ValueMapValue(name = "autoRefresh")
    private Boolean autoRefresh;

    @ValueMapValue(name = "refreshInterval")
    @org.apache.sling.models.annotations.Default(longValues = 5)
    private long refreshInterval;

    @SlingObject
    private Resource currentResource;

    private List<QuoteItem> quotes;
    private QuoteItem randomQuote;

    @javax.annotation.PostConstruct
    protected void init() {
        quotes = new ArrayList<>();
        Resource quotesParent = currentResource.getChild(QUOTES_NODE);
        if (quotesParent != null) {
            for (Resource child : quotesParent.getChildren()) {
                QuoteItem item = child.adaptTo(QuoteItem.class);
                if (item != null && item.getQuote() != null && !item.getQuote().isEmpty()) {
                    quotes.add(item);
                }
            }
        }
        if (!quotes.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(quotes.size());
            randomQuote = quotes.get(index);
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean isAutoRefresh() {
        return Boolean.TRUE.equals(autoRefresh);
    }

    public long getRefreshInterval() {
        return refreshInterval > 0 ? refreshInterval : 5;
    }

    public List<QuoteItem> getQuotes() {
        return quotes;
    }

    public int getQuoteCount() {
        return quotes.size();
    }

    public QuoteItem getRandomQuote() {
        return randomQuote;
    }

    public boolean isEmpty() {
        return quotes.isEmpty();
    }
}
